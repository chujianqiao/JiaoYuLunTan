package cn.stylefeng.guns.pay.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.pay.AlipayConfig;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.model.result.VipPayResult;
import cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
//@RestController
@Controller
//@RequestMapping(value = "/alipay")
@RequestMapping("/alipay")
public class AlipayController {

	private static MeetMemberService meetMemberService = SpringContextHolder.getBean(MeetMemberService.class);

	private static VipPayService vipPayService = SpringContextHolder.getBean(VipPayService.class);

	@RequestMapping(value = "/pay", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public static String pay(Map<String,Object> map,HttpServletRequest request) throws Exception {
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
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
				AlipayConfig.app_id, AlipayConfig.merchant_private_key,
				"json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		//设置同步回调通知
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		//设置异步回调通知
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
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
			// TODO Auto-generated catch block
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

//	@GetMapping("/return")
	@RequestMapping("/return")
//	@ResponseBody
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
//			try {
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			params.put(name, valueStr);
			content += valueStr;
		}

		boolean signVerified = false; //调用SDK验证签名
		try {
			signVerified = AlipaySignature.rsaCheckV1(params,  AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
//			signVerified = AlipaySignature.rsaCheckV2(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
//			signVerified = AlipaySignature.rsaCheckV1(params,  AlipayConfig.alipay_public_key, AlipayConfig.charset);
//			signVerified =AlipaySignature.rsaCheck(params, sign,  AlipayConfig.alipay_public_key, AlipayConfig.charset,  AlipayConfig.sign_type);
//			signVerified = AlipaySignature.rsa256CheckContent(content, sign, AlipayConfig.alipay_public_key,AlipayConfig.charset);
			signVerified = true;
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
//			return "支付成功";
			return "/meetMember/meetMember.html";
		}else {
			System.out.println("验签失败");
//			return "支付失败";
			return "/meetMember/meetMember.html";
		}
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

}
