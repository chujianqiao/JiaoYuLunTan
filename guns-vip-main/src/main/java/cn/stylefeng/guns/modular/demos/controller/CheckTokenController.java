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
        /*try {
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
            //替换成你的AK
            final String accessKeyId = "LTAI4G8fGDZuKV1T88CedR14";//你的accessKeyId,参考本文档步骤2
            final String accessKeySecret = "u1jvMhv6iaG53gpDu5pt6moNq3rflv";//你的accessKeySecret，参考本文档步骤2
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();


            //使用post提交
            request.setMethod(MethodType.POST);
            //1，必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers("18716002632");
            //2，必填:短信签名-可在短信控制台中找到
            request.setSignName("智慧工程");
            //3，必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode("SMS_96665060");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"code\":\"123\"}");
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            // 判断是否发送成功
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("返回的状态码：" + sendSmsResponse.getCode());
                System.out.println("返回的信息：" + sendSmsResponse.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }
}
