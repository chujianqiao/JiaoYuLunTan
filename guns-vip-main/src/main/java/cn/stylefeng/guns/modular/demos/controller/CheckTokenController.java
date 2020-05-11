package cn.stylefeng.guns.modular.demos.controller;

import cn.stylefeng.guns.modular.demos.util.SignUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/check")
@Slf4j
public class CheckTokenController {

    /**
     * @description 微信公众号服务器配置校验token
     * @author: liyinlong
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
}
