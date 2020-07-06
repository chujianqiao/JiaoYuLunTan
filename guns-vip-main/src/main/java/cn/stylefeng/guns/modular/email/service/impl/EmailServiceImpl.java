package cn.stylefeng.guns.modular.email.service.impl;

import cn.stylefeng.guns.modular.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 优秀成果表 服务实现类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
@Service
public class EmailServiceImpl implements EmailService {

    //从application.properties配置文件中注入发送者的邮件地址
    @Value("${spring.mail.username}")
    private String fromEmail;

    //注入spring发送邮件的对象
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendAttachmentMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            javaMailSender.send(simpleMailMessage);  		//执行发送
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
