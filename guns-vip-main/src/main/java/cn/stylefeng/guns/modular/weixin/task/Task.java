package cn.stylefeng.guns.modular.weixin.task;


import cn.stylefeng.guns.modular.weixin.pojo.Token;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.modular.weixin.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class Task {

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    //定时生成AccessToken并存储数据库
    @Scheduled(fixedDelay = 7000000)
    public void taskToken(){
        //Token token= CommonUtil.getToken(appid, secret);
        //System.out.println(token.getAccessToken());
        //System.out.println(new Date());
        //TokenUtil.saveToken(token);
    }

}
