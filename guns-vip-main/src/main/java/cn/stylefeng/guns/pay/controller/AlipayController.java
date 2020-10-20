package cn.stylefeng.guns.pay.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.pay.config.AlipayConfigProperties;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.model.result.VipPayResult;
import cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.util.Base64Util;
import cn.stylefeng.guns.util.QrCodeUtil;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付宝支付控制器
 * @author
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(LogManager.class);

	private String PREFIX = "/pay";

	@Autowired
	private MeetMemberService meetMemberService;

	@Autowired
	private VipPayService vipPayService;

	@Autowired
	private AlipayConfigProperties aliPayProperties;

	@Value("${file.uploadFolder}")
	private String uploadFolder;

	/**
	 *二维码页面
	 * @return
	 */
	@RequestMapping("/qrcode")
	public String alipayQrcode(@RequestParam("memberId") String memberId) {
		return PREFIX + "/alipayQrcode.html";
	}

	/**
	 * 创建订单实现扫码支付
	 * @return
	 */
	@RequestMapping("/createOrder")
	@ResponseBody
	public ResponseData createOrder(@RequestParam("memberId") String memberId) {
		Map<String,String> map = new HashMap();
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.gatewayUrl,aliPayProperties.app_id,aliPayProperties.merchant_private_key,"json",aliPayProperties.charset,aliPayProperties.alipay_public_key,aliPayProperties.sign_type);
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		String orderNum = getOrderNum();
		//订单金额
		String amout = "188.88";
		request.setBizContent("{" +
				"\"out_trade_no\":\""+ orderNum +"\"," +
				"\"seller_id\":\"\"," +
				"\"total_amount\":" + amout + "," +
				"\"discountable_amount\":0.00," +
				"\"subject\":\"论坛费用\"," +
				"\"body\":\"论坛费用\"," +
				"\"product_code\":\"FACE_TO_FACE_PAYMENT\"," +
				"\"timeout_express\":\"90m\"," +
				"\"qr_code_timeout_express\":\"90m\"" +
				"  }");
		AlipayTradePrecreateResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		if(response.isSuccess()){
			logger.info("调用支付宝当面付接口成功");
			//二维码链接转图片，再转base64码显示到前台
			//二维码链接
			String qrCodeUrl = response.getQrCode();
			//本地路径
			String path = uploadFolder + "alipay";
			//文件名称
			String fileName = orderNum + ".jpg";
			String imgPatah = QrCodeUtil.createQrCode(qrCodeUrl,path,fileName);
			String imgBase64Str = Base64Util.convertFileToBase64(imgPatah);
			map.put("imgPatah",imgPatah);
			map.put("imgBase64Str",imgBase64Str);
			//存支付信息到缴费表
			VipPayParam vipPayParam = new VipPayParam();
			Long userId = LoginContextHolder.getContext().getUser().getId();
			vipPayParam.setPayUser(userId);
			vipPayParam.setOrderNum(orderNum);
			vipPayParam.setMemberId(Long.parseLong(memberId));
			vipPayParam.setPayType("alipay");
			vipPayParam.setPayMoney(new BigDecimal(amout));
			vipPayParam.setPayTime(null);
			VipPayParam tempPayParam = new VipPayParam();
			tempPayParam.setMemberId(Long.parseLong(memberId));
			List<VipPayResult> list = this.vipPayService.customList(tempPayParam);
			if(list.size() == 0){
				this.vipPayService.add(vipPayParam);
			}else {
				vipPayParam.setPayId(list.get(0).getPayId());
				this.vipPayService.update(vipPayParam);
			}
		} else {
			logger.error("调用支付宝当面付接口失败");
			String errMsg = response.getSubMsg();
			map.put("errMsg",errMsg);
		}
		map.put("orderNum",orderNum);
		return ResponseData.success(map);
	}

	/**
	 * 查询订单状态
	 * @return
	 */
	@RequestMapping("/searchOrder")
	@ResponseBody
	public ResponseData searchOrder(@RequestParam(required = false) String orderNum) {
		logger.info("轮询支付宝订单");
		Map<String,String> map = new HashMap();
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.gatewayUrl,aliPayProperties.app_id,aliPayProperties.merchant_private_key,"json",aliPayProperties.charset,aliPayProperties.alipay_public_key,aliPayProperties.sign_type);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{" +
				"\"out_trade_no\":\"" + orderNum + "\"," +
				"\"trade_no\":\"\"" +
				"  }");
		AlipayTradeQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(response.isSuccess()){
			logger.info("调用支付宝查询订单接口成功");
			String tradeStatus = response.getTradeStatus();
			String successStr = "TRADE_SUCCESS";
			if(successStr.equals(tradeStatus)){
				map.put("tradeStatus","success");
				//更新缴费表、会议成员表
				VipPayParam vipPayParam = new VipPayParam();
				vipPayParam.setOrderNum(orderNum);
				List<VipPayResult> list = this.vipPayService.customList(vipPayParam);
				if(list.size() != 0){
					vipPayParam.setPayId(list.get(0).getPayId());
					vipPayParam.setPayTime(new Date());
					MeetMemberParam meetMemberParam = new MeetMemberParam();
					meetMemberParam.setMemberId(list.get(0).getMemberId());
					meetMemberParam.setMeetStatus(4);
					this.vipPayService.update(vipPayParam);
					this.meetMemberService.update(meetMemberParam);
				}
			}else{
				map.put("tradeStatus","no");
			}
		} else {
			logger.info("调用支付宝查询订单接口失败");
			String errMsg = response.getSubMsg();
			map.put("errMsg",errMsg);
		}
		return ResponseData.success(map);
	}

	/**
	 * 生成订单号
	 * @return
	 */
	private static String getOrderNum(){
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(date);
		//6位随机数
		String randomStr = String.valueOf((int)((Math.random()*9+1)*100000));
		String orderNum = dateStr + randomStr;
		return orderNum;
	}

	@RequestMapping(value = "/pay", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String pay(Map<String,Object> map,HttpServletRequest request) throws Exception {
		//会议ID
		String memberIdStr = request.getParameter("memberId");
//		request.setAttribute("memberId",memberIdStr);

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = getOrderNum();
		//付款金额，必填
		String total_amount = "188.00";
		//订单名称，必填
		String subject = "支付会议费用";
		//商品描述，可空
		String body = "描述";
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayProperties.gatewayUrl,
				aliPayProperties.app_id, aliPayProperties.merchant_private_key,
				"json", aliPayProperties.charset, aliPayProperties.alipay_public_key, aliPayProperties.sign_type);
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		//设置同步回调通知
		alipayRequest.setReturnUrl(aliPayProperties.return_url);
		//设置异步回调通知
		alipayRequest.setNotifyUrl(aliPayProperties.notify_url);
		//设置支付参数
		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
				+ "\"total_amount\":\"" + total_amount + "\","
				+ "\"subject\":\"" + subject + "\","
				+ "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}"

		);
		//请求
		String result = null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		//支付详情
		VipPayParam vipPayParam = new VipPayParam();
		long payId = ToolUtil.getNum19();
		vipPayParam.setPayId(payId);
		vipPayParam.setOrderNum(out_trade_no);
		vipPayParam.setPayMoney(new BigDecimal(total_amount));
		vipPayParam.setPayType("alipay");
		vipPayParam.setPayUser(LoginContextHolder.getContext().getUser().getId());
		vipPayParam.setPayTime(new Date());

		//更新会议注册信息
		MeetMemberParam meetMemberParam = new MeetMemberParam();
		meetMemberParam.setMemberId(Long.parseLong(memberIdStr));
		meetMemberParam.setPayId(payId);

		//更新表
		vipPayService.add(vipPayParam);
		meetMemberService.update(meetMemberParam);

		return result;
//		return ResponseData.success(result);
	}

//	@RequestMapping("/notify")
	@GetMapping("/notify")
	public String pay_notify(HttpServletRequest request) {
		System.out.println("异步通知");
		return "异步通知";
	}

	@RequestMapping("/return")
	public String pay_return(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		String content = "";
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(name, valueStr);
			content += valueStr;
		}

		boolean signVerified = false; //调用SDK验证签名
		try {
			signVerified = AlipaySignature.rsaCheckV1(params,  aliPayProperties.alipay_public_key, aliPayProperties.charset, aliPayProperties.sign_type);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			VipPayParam vipPayParam = new VipPayParam();
			vipPayParam.setOrderNum(out_trade_no);
			List<VipPayResult> payResult = vipPayService.customList(vipPayParam);
			VipPayResult vipPayResult = payResult.get(0);
			vipPayParam.setPayId(vipPayResult.getPayId());
			vipPayParam.setTranNum(trade_no);

			MeetMemberParam meetMemberParam = new MeetMemberParam();
			meetMemberParam.setPayId(vipPayResult.getPayId());
			List<MeetMemberResult> members = meetMemberService.customList(meetMemberParam);
			MeetMemberResult meetMemberResult = members.get(0);
			meetMemberParam.setMemberId(meetMemberResult.getMemberId());
			meetMemberParam.setMeetStatus(4);

			//更新缴费表、会议注册成员表
			vipPayService.update(vipPayParam);
			meetMemberService.update(meetMemberParam);

			System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
			return "/meetMember/meetMember.html";
		}else {
			System.out.println("验签失败");
			return "/meetMember/meetMember.html";
		}
	}

}
