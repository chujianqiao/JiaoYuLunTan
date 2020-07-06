package cn.stylefeng.guns.modular.sms.service;

/**
 * <p>
 * 邮箱
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
public interface SMSService {

    /**
     * @Description 发送短信验证码
     * @param phone 待发送手机号
     * @param signName 短信签名
     * @param code 短信模板代码
     * @param param 短信模板内容为"您的验证码为${code}"时，此值为"{\"code\":\"123\"}"
     * @return boolean 成功返回true，失败返回false
     */
    boolean sendSMS(String phone, String signName, String code, String param);

}
