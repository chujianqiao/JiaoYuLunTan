package cn.stylefeng.guns.modular.demos.controller;

import cn.stylefeng.guns.modular.demos.util.SignUtil;
import cn.stylefeng.guns.modular.email.service.EmailService;
import cn.stylefeng.guns.modular.sms.service.SMSService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/check")
@Slf4j
public class CheckTokenController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    /**
     * @description 微信公众号服务器配置校验token
     * @author: CHU
     * @date 2019-05-09 9:38
     * @return
     */
    @ApiOperation("微信公众号服务器配置校验token")
    @RequestMapping("/checkToken")
    public void checkToken(HttpServletRequest request, HttpServletResponse response) {
        //token验证代码段
        try {
            log.info("请求已到达，开始校验token");
            if (StringUtils.isNotBlank(request.getParameter("signature"))) {
                String signature = request.getParameter("signature");
                String timestamp = request.getParameter("timestamp");
                String nonce = request.getParameter("nonce");
                String echostr = request.getParameter("echostr");
                log.info("signature[{}], timestamp[{}], nonce[{}], echostr[{}]", signature, timestamp, nonce, echostr);
                if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                    log.info("数据源为微信后台，将echostr[{}]返回！", echostr);
                    response.getOutputStream().println(echostr);
                }
            }
        } catch (IOException e) {
            log.error("校验出错");
            e.printStackTrace();
        }
    }

    /**
     * @description 邮箱发送验证
     * @author: CHU
     * @date 2020-06-23
     * @return
     */
    @ApiOperation("邮箱发送验证")
    @RequestMapping("/checkEmail")
    public void checkEmail(HttpServletRequest request, HttpServletResponse response) {
        String to = "1172544385@qq.com";
        String subject = "测试";
        String content = "测试文本";
        boolean sentEmail = emailService.sendAttachmentMail(to,subject,content);
        if (sentEmail){
            log.info("发送成功");
        }else {
            log.info("发送失败");
        }

    }

    /**
     * @description 短信验证
     * @author: CHU
     * @date 2020-06-23
     * @return
     */
    @ApiOperation("短信验证")
    @RequestMapping("/checkSMS")
    @ResponseBody
    public String checkSMS(String phone, HttpServletRequest request, HttpServletResponse response) {
        //String phone = "18716002632";
        JSONObject json = new JSONObject();
        String signName = "智慧工程";
        String code = "SMS_96665060";
        String smsCode = String.valueOf(new Random().nextInt(899900) + 100000);
        log.info(smsCode);
        String param = "{\"code\":\"" + smsCode + "\"}";
        boolean sentSMS = smsService.sendSMS(phone,signName,code,param);
        if (sentSMS){
            log.info("发送成功");
            json.put("phone", phone);
            json.put("smsCode", smsCode);
            json.put("createTime", System.currentTimeMillis());
            request.getSession().setAttribute("smsCode",json);
            return smsCode;
        }else {
            log.info("发送失败");
            return "error";
        }

    }
}
