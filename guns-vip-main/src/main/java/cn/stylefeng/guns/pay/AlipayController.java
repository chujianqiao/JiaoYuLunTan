package cn.stylefeng.guns.pay;

import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付控制器
 * @author
 */
@RestController
@RequestMapping(value = "/alipay")
public class AlipayController {

	@RequestMapping(value = "/pay", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public static String pay(Map<String,Object> hap) throws Exception {
		//获取要向支付宝支付的参数,由页面传过来
		//商户订单号，商户网站订单系统中唯一订单号，必填
		Long ms = System.currentTimeMillis();
		String out_trade_no = ms.toString();
		//付款金额，必填
		String total_amount = "18.0";
		//订单名称，必填
		String subject = "XX订单";
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
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求
		String result = null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
//		return ResponseData.success(result);
	}

//	@RequestMapping("/notify")
	@GetMapping("/notify")
	public String pay_notify(HttpServletRequest request) {
		System.out.println("");
		return "异步通知";
	}

	@GetMapping("/return")
	@ResponseBody
	public String pay_return(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
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
		}

		boolean signVerified = false; //调用SDK验证签名
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
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

			System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
			return "支付成功";
		}else {
			System.out.println("验签失败");
			return "支付失败";
		}
	}

}
