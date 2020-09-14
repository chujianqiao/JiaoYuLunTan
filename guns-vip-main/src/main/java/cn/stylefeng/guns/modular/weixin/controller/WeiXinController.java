package cn.stylefeng.guns.modular.weixin.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.modular.weixin.cache.PoolCache;
import cn.stylefeng.guns.modular.weixin.cache.ScanPool;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/weiXin")
@Slf4j
public class WeiXinController {
    private String PREFIX = "/weiXin";

    @Value("${weiXin.url}")
    private String systemUrl;

    @Value("${file.imagesFolder}")
    private String imagesFolder;

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    /**
     * @Description: 微信公众号登录授权
     * @auther: Sakura
     * @date: 2019/3/8 9:46
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/login")
    public void login(String uuid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 回调地址，该域名需要公众号验证
        String backUrl = systemUrl + "/weiXin/callBack?uuid="+uuid;
        // 向用户申请授权
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
                + "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8")
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";

        response.sendRedirect(url);
    }

    /**
     * @Description: 授权的回调函数
     * @auther: Sakura
     * @date: 2019/3/8 9:47
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/callBack")
    public String callBack(String uuid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取到授权标志code
        String code = request.getParameter("code");
        // 通过code换取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = CommonUtil.httpsRequest(url,"GET",null);
        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");
        // 校验access_token是否失效
        String checkoutUrl = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;
        JSONObject checkoutInfo = CommonUtil.httpsRequest(checkoutUrl,"GET",null);
        System.out.println("校验信息-----" + checkoutInfo.toString());
        if (!"0".equals(checkoutInfo.getString("errcode"))) {
            // 刷新access_token
            String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + openid + "&grant_type=refresh_token&refresh_token=" + refresh_token;

            JSONObject refreshInfo = CommonUtil.httpsRequest(checkoutUrl,"GET",null);
            System.out.println(refreshInfo.toString());
            access_token = refreshInfo.getString("access_token");
        }
        // 使用access_token拉取用户信息
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = CommonUtil.httpsRequest(infoUrl,"GET",null);
        System.out.println("用户数据-----" + userInfo.toString() + "\n"
                + "名字-----" + userInfo.getString("nickname") + "\n"
                + "头像-----" + userInfo.getString("headimgurl") + "\n"
                + "openID-----" + userInfo.getString("openid") + "\n"
                + "性别-----" + userInfo.getString("sex"));
        ScanPool pool = PoolCache.cacheMap.get(uuid);

        if(pool == null){
        }else {
            pool.scanSuccess();
        }
        //return "success";
        try {
            return PREFIX + "/weiXinLogin.html";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信扫描登陆异常");
        }
    }

    @RequestMapping("/qrcode")
    public String qrcode(Model model){
        model.addAttribute("url",CommonUtil.getTempQrcode());
        return PREFIX + "/weiXinLogin.html";
    }

    @RequestMapping("/getLinkImage")
    @ResponseBody
    public Map<String, Object> getLinkImage(){
        //  要生成二维码的链接
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //将UUID放入缓存
        ScanPool pool = new ScanPool();
        PoolCache.cacheMap.put(uuid, pool);
        String url = systemUrl + "/weiXin/login?uuid=" + uuid;
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        //	指定二维码图片名字
        LoginUser user = LoginContextHolder.getContext().getUser();
        //String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        String fileName = user.getId() + "_" + uuid + ".jpg";
        CommonUtil.createQrCode(url, path, fileName);

        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(path + "\\" + fileName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("name",Base64.encodeBase64String(data));
        return map;
    }

    @RequestMapping("/weChatBand")
    public String weChatBand(Model model){

        return PREFIX + "/weChatBand.html";
    }

    @RequestMapping("/pool")
    @ResponseBody
    public String pool(String uuid, HttpServletRequest request){
        int count = 1;
        int result = 0;
        while (true) {
            try {
                Thread.sleep(1000);//睡眠1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("页面传递的uuid : " + uuid);
            // 检测登录，检测手机是否已扫码
            ScanPool pool = PoolCache.cacheMap.get(uuid);
            boolean scanFlag = pool.getScanStatus();
            if ( scanFlag ) {
                result = 1;//认证成功
                break;
            } else {
                if (count == 10) {
                    result = 2;//认证失败，未扫描二维码
                    break;
                }
            }//end if..else

            count++;//计数+1

        }//end while
        if (result == 1){
            return "success";
        }else if (result == 2){
            return "fail";
        }else {
            return "timeout";
        }
    }



    @RequestMapping("/push")
    public void push(){
        String templateId = "2iJpJMGCXs4OQ5vy-H_5vFz_lELnAZNPe7bSfFLSvp4";
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appid);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oe9p8wmSOnbvURekVJPo5Xg2ZEDI")//要推送的用户openid oe9p8wmSOnbvURekVJPo5Xg2ZEDI  oe9p8wrDJD5_Mm9l9Mv9BM9CuKqI
                .templateId(templateId)//模版id
                .url("https://www.baidu.com/")//点击模版消息要访问的网址
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        templateMessage.addData(new WxMpTemplateData("keyword1", "大会", "#FF00FF"));
        templateMessage.addData(new WxMpTemplateData("keyword2", "2020-09-11", "FF00FF"));
        templateMessage.addData(new WxMpTemplateData("keyword3", "中国教育科学研究院", "FF00FF"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }


    @RequestMapping("/MP_verify_JZg3veGUNvbnoZz2.txt")
    @ResponseBody
    public String verify(){
        return "JZg3veGUNvbnoZz2";
    }

}
