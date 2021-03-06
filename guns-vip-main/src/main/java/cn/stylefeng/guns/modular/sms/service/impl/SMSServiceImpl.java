package cn.stylefeng.guns.modular.sms.service.impl;

import cn.stylefeng.guns.modular.sms.service.SMSService;
import cn.stylefeng.guns.sys.modular.consts.mapper.SysConfigMapper;
import cn.stylefeng.guns.sys.modular.consts.model.params.SysConfigParam;
import cn.stylefeng.guns.sys.modular.consts.model.result.SysConfigResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 优秀成果表 服务实现类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
@Service
public class SMSServiceImpl implements SMSService {

    //从application.properties配置文件中注入配置信息
    /*@Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;*/

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    public boolean sendSMS(String phone, String signName, String code, String param) {
        try {
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            //初始化ascClient需要的几个参数
            final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
            final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

            String accessKeyId = "";
            String accessKeySecret = "";
            SysConfigParam sysConfigParam = new SysConfigParam();
            List<SysConfigResult> sysConfigList = sysConfigMapper.customList(sysConfigParam);
            for (int i = 0;i < sysConfigList.size();i++){
                if (sysConfigList.get(i).getCode().equals("GUNS_SMS_ACCESSKEY_ID")){
                    accessKeyId = sysConfigList.get(i).getValue();
                }
                if (sysConfigList.get(i).getCode().equals("GUNS_SMS_ACCESSKEY_SECRET")){
                    accessKeySecret = sysConfigList.get(i).getValue();
                }
            }

            //替换成你的AK
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
            request.setPhoneNumbers(phone);
            //2，必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //3，必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(code);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(param);
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            // 判断是否发送成功
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("返回的状态码：" + sendSmsResponse.getCode());
                System.out.println("返回的信息：" + sendSmsResponse.getMessage());
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
