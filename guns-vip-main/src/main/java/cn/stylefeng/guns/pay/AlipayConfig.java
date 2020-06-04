package cn.stylefeng.guns.pay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102300743088";

	//商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFeydgxdbcWWbRRt08+LSnguhFx2q63bJwJAG4EzXxiA8TZHb46QU3gOmApQ37Zyp/6QgC/F2fDWpk6mQwjHtQH46jp1NvORZOfGWXPyL9Gi/X5/tWtdGR4OYS3b2hbRno4Ul4zLGPNteo6eeAb+KT5KCr8tj8746+O/HJZLAzEEYRmU18zqHGwWJv5HjjzM2a4BeNl3Oq1TmqzNAqqdVzkwfNn34t/KQiQ/4WbAq1EOuwqjWSfoNqiDC2fcxs9K89J2UCSglU37SGcPvW9K4Hw2bIvnv3pfdecbuRnyeYQRMCQRGJED2avauON44kKXlFaCH0fiJuEDwlnKE3IXLXAgMBAAECggEAbZR+7Vk9XMQgPmo4shdS57n2SWK+4g2isaE9sApFXLXf+oGdkhZqa/huuWfHJL67CysZoqnfifWPhK1P9vM9QLfHd96kBkkNYP2KwTHZ3YMkB9Gwaqz1ERdaid50ERPqE17v3DXfBr11KnxryusPTW6B4OeK74sS2mWHx1fbtu7Oqx3npGAnRSbQ3Zpme+7mE8TwSXJKVpwL7o7oY6dQITGjlpaCshN35RUkjcsWcVed3LXiI2UxJsrovhEZK9n8emXyprxnomcNSU7Aebx6H+OHyNDbb69NOACp/bXxWsUMgfiqkaUoF6i+q/3zi5iPHiuEFHcS9LpF6j9l+cTbwQKBgQDduV30IeepIDGJ0Uzz/BEezKQaRL8z8VcJpIO8/HT6cum6Xtg7tehfD5C9aOIDiO/iW2SrUASioU5H8NnnU7Y/D29j/OIzU5cWtXJIDVm9rePorTJ5S1DgJXt+nx0C3a0J4ZDsvatz9BaOlv/RjS2BVBjZHUWs6Bavz6A85vn8EQKBgQCaHZu1T3vKQIlTzpYhHWr+Uj2bXmWFQabA2fRFHqh6/71GDARW0mEYfHhDKq5jkweB/8mJpQCW2ng3Fk+wKfy53x75xqFtYz3wybb4CWEjFzPztZXeQs1CWqGgxbSh3aJ5tghPdHaCKPwtrrWhiXEj56DnwTaT3Mvd1OZIxGSIZwKBgBhBC78F6nJ8vA6IX7Oc0j6dQiJBfOzwrIW4glStBRrUcS7a6yp09qtXZ4zgqUCbvSn0t2lqAmfL32VNOnjsllYFmTjF0/SrYy6vwIWTOErgd7rfH1NXYsxyLTUnsKO8ng4jp5fOE7BGdiU47Vj96YlpKqKtag7FAJ5QxwWCerwRAoGALIDyTug1+hCq112nssZY+DntemlG7bUonzYROMiCpIonMQwVULENwtFmtRalJzH1EBA6NZpFUkqiPl4eAuQNC+uneT9nrDvlThwhzvx+ZXC02uKL9ZPazBd55wBQihGDcpwjSDZ8ZlfJgrOZ1Zzn5BNmPqZnyUkQQe3R1h+IXG8CgYEAi5EDNzmm7m5Nsgelelrj27wIpSqYPNN4GT/2zBOyjptl+vEc8ml5l+nUi2RbQ3zmbY3YdD+WOBPkVHCgplpl2kKvn9I2PW4Lydm4RXGfwBUdEzHvcusPsCA8TOFu72wfCW0HLnhUp5WyPx10+Ab8dgluNdonpkU3Xis/CPZdrrY=";

	//支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoIaH4qtsD2bIgNUc+VxYUiXPAc3/vhA7hS2oAqqzFYsZcXpKv6QZU2SLwEZeS0dJPCPndnqRMDoKr+6GgQ7g6cQTSbndYCm11RqDe0tLNYdW8bmJWRujaLyGiElBbmT/8v4HGsaUE6R1/OL8uLDAJZ/yOA8YfIQwL4dVSoeATYfA9BSVWGgZwVLsngRo8rVQGdCC34lWe5B4XjNXw1xtS52LRceJJhgMcWmwi3DPOaslVkq0YJObHxzj4DXCDqe14KoiV6KVvS+p2zXiccj5AiPNo6Q60EuOZKiK3h8qAoWQv7jJoTxk4w7MgBVZVkgN9MaqtcxamEOFeE5xZ/vm0wIDAQAB";

	//服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://a612a88e9b65.ngrok.io/alipay/notify";

	//页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://a612a88e9b65.ngrok.io/alipay/return";

	//签名方式
	public static String sign_type = "RSA2";

	//字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
//	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	//沙箱网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


	// 日志地址
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

