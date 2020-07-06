package cn.stylefeng.guns.modular.email.service;

/**
 * <p>
 * 邮箱
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
public interface EmailService {

    /**
     * @Description 发送简单纯文本邮件
     * @param to 收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return boolean 成功返回true，失败返回false
     */
    boolean sendAttachmentMail(String to, String subject, String content);

}
