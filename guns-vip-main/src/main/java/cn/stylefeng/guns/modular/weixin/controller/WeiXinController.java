package cn.stylefeng.guns.modular.weixin.controller;

import cn.hutool.http.HttpUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.checkIn.entity.CheckIn;
import cn.stylefeng.guns.modular.checkIn.model.params.CheckInParam;
import cn.stylefeng.guns.modular.checkIn.service.CheckInService;
import cn.stylefeng.guns.modular.weixin.cache.PoolCache;
import cn.stylefeng.guns.modular.weixin.cache.ScanPool;
import cn.stylefeng.guns.modular.weixin.pay.WXPayUtil;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/weiXin")
@Slf4j
public class WeiXinController {
    private String PREFIX = "/weiXin";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private VipPayService vipPayService;

    @Value("${weiXin.url}")
    private String systemUrl;

    @Value("${file.imagesFolder}")
    private String imagesFolder;

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Value("${weiXin.mchId}")
    private String mchId;

    @Value("${weiXin.key}")
    private String key;

    @Value("${weiXinOpen.appid}")
    private String appidOpen;

    @Value("${weiXinOpen.secret}")
    private String secretOpen;

    private static final String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @RequestMapping(value = "/toWeiXinPay")
    public String toWeiXinPay(@RequestParam("memberId") String memberId){
        return PREFIX + "/weiXinPay.html";
    }

    /**
     * @Description: 微信支付
     * @auther: Sakura
     * @date: 2019/3/8 9:46
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/weiXinPay")
    @ResponseBody
    public Map<String, Object> weiXinPay(String memberId, String out_trade_no, String total_fee, String good_id){
        HashMap<String, String> map = new HashMap<>();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        LoginUser user = LoginContextHolder.getContext().getUser();
        //日期
        String date = String.valueOf(System.currentTimeMillis()/ 1000);
        String randomA = String.valueOf((int)(Math.random()*100000000));
        String randomB = String.valueOf((int)(Math.random()*100000000));
        String randomC = String.valueOf((int)(Math.random()*100000000));
        String str = (randomA+randomB+randomC).substring(0,15);
        //订单号
        out_trade_no = date+str;
        // APPID
        map.put("appid", appid);
        // 商户号
        map.put("mch_id", mchId);
        // 随机字符串
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        // 商品描述
        map.put("body", "测试支付");
        // 商户订单号
        map.put("out_trade_no", out_trade_no);
        // 标价金额
        map.put("total_fee","1");
        // 终端IP
        map.put("spbill_create_ip","127.0.0.1");
        // 通知地址(回调地址)
        map.put("notify_url",systemUrl + "/weiXin/payCallBack?uuid=" + uuid + "&userId=" + user.getId() + "&memberId=" + memberId);
        // 交易类型
        map.put("trade_type","NATIVE");
        // 商品ID
        map.put("product_id","1");

        try {
            // 将参数转成xml  // PARTNERKEY: 密钥
            String signedXml = WXPayUtil.generateSignedXml(map, key);
            // 用的hutool工具类， 相当于https请求 // URL: 统一下单接口
            String post = HttpUtil.post(URL, signedXml);

            // 将返回值转成map
            Map<String, String> toMap = WXPayUtil.xmlToMap(post);
            // 生成二维码的url
            String code_url = toMap.get("code_url");


            //  要生成二维码的链接

            //将UUID放入缓存
            ScanPool pool = new ScanPool();
            PoolCache.cacheMap.put(uuid, pool);
            String url = code_url;
            //	指定路径：D:\User\Desktop\testQrcode
            //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
            String path = imagesFolder;
            System.out.println(path);
            //	指定二维码图片名字

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
            Map mapReturn = new HashMap();
            mapReturn.put("photo", Base64.encodeBase64String(data));
            mapReturn.put("uuid", uuid);
            return mapReturn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 微信支付回调
     * @auther: Sakura
     * @date: 2019/3/8 9:46
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/payCallBack")
    public void payCallBack(String uuid, String userId, String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            InputStream inStream = request.getInputStream();
            BufferedReader in = null;
            StringBuffer result = new StringBuffer();
            in = new BufferedReader(
                    new InputStreamReader(inStream));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result);

            //===============下面根据自己的需求写逻辑===========================
//判定是支付成功的返回结果
            Map<String, String> map = new HashMap<String, String>();
            if(result.toString().indexOf("xml")!=-1) {
                //將xml转化为map
                Document doc;
                doc = DocumentHelper.parseText(result.toString());
                Element root = doc.getRootElement();
                List children = root.elements();
                if (children != null && children.size() > 0) {
                    for (int i = 0; i < children.size(); i++) {
                        Element child = (Element) children.get(i);
                        map.put(child.getName(), child.getTextTrim());
                    }
                }
            }
            //微信返回的签名
            String sign = map.get("sign");
            uuid = map.get("uuid");
            memberId = map.get("memberId");
            userId = map.get("userId");
            map.remove("sign");
            //自动生成的sign
            String nsign = WXPayUtil.generateSignedXml(map,key);
            //验证签名通过
            //if(nsign.equals(sign)){
                String resXml = "";
                if(map.get("return_code").equals("SUCCESS")) {
                    log.info("返回结果:: "+ map.get("openid") +"用户支付成功");
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    String create_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    //获取系统当前时间
                    map.put("create_time",create_time);
                    //商户号
                    String mch_id = map.get("mch_id");
                    //商户订单号
                    String out_trade_no = map.get("out_trade_no");

                    //无订单信息 ,订单状态为已支付,支付金额不正确，返回微信支付成功！
                    if(map.get("total_fee")==null||map.get("total_fee").equals("0")){

                    }else{
                        //实现自己的业务
                        double wxtotal_fee = Double.parseDouble(map.get("total_fee"));
                        //支付详情
                        VipPayParam vipPayParam = new VipPayParam();
                        long payId = ToolUtil.getNum19();
                        vipPayParam.setPayId(payId);
                        vipPayParam.setOrderNum(out_trade_no);
                        vipPayParam.setPayMoney(new BigDecimal(wxtotal_fee));
                        vipPayParam.setPayType("wechat");
                        vipPayParam.setPayUser(Long.parseLong(userId));
                        vipPayParam.setPayTime(new Date());
                        vipPayParam.setMemberId(Long.parseLong(memberId));

                        //更新会议注册信息
                        MeetMemberParam meetMemberParam = new MeetMemberParam();
                        meetMemberParam.setMemberId(Long.parseLong(memberId));
                        meetMemberParam.setPayId(payId);
                        meetMemberParam.setMeetStatus(4);

                        //更新表
                        vipPayService.add(vipPayParam);
                        meetMemberService.update(meetMemberParam);

                        ScanPool pool = PoolCache.cacheMap.get(uuid);

                        if(pool == null){
                        }else {
                            pool.scanSuccess();
                        }
                    }
                }else {
                    log.info("支付失败,错误信息：" + map.get("err_code"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
                log.info("返回结果 map::" + map);
                //json = new JsonResult(map);
                //关闭连接
                in.close();
                inStream.close();
            //}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @Description: 微信公众号登录授权
     * @auther: Sakura
     * @date: 2019/3/8 9:46
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/login")
    public void login(String uuid, String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 回调地址，该域名需要公众号验证
        String backUrl = systemUrl + "/weiXin/callBack?uuid=" + uuid + "&userId=" + userId;
        // 向用户申请授权
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
                + "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8")
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                 + "&state=STATE#wechat_redirect";

        response.sendRedirect(url);
    }

    @RequestMapping(value = "/testBand")
    public String testBand(String uuid, String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return PREFIX + "/weiXinLogin.html";
    }

    /**
     * @Description: 授权的回调函数
     * @auther: Sakura
     * @date: 2019/3/8 9:47
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/callBack")
    public String callBack(String uuid, String userId, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        User userExist = userService.getUserByUnionId(userInfo.getString("unionid"));

        if (userExist == null){
            User loginUser = userService.getById(userId);
            UserDto user = new UserDto();
            if (loginUser != null){
                user.setUserId(loginUser.getUserId());
                user.setWechatName(userInfo.getString("nickname"));
                user.setWechatId(userInfo.getString("openid"));
                user.setUnionId(userInfo.getString("unionid"));
                this.userService.editUser(user);
            }


            ScanPool pool = PoolCache.cacheMap.get(uuid);

            if(pool == null){
            }else {
                pool.scanSuccess();
            }
            model.addAttribute("bandStatus","success");
        }else {
            model.addAttribute("bandStatus","exist");
        }


        //return "success";
        try {
            return PREFIX + "/weiXinLogin.html";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信扫描异常");
        }
    }

    //登录二维码
    @RequestMapping("/qrcode")
    @ResponseBody
    public Map<String, Object> qrcode(Model model) throws UnsupportedEncodingException {
        Map map = new HashMap();
        String oauthUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        String callBack = systemUrl + "/weiXin/openCallBack";
        String redirect_uri = URLEncoder.encode(callBack, "utf-8");
        oauthUrl = oauthUrl.replace("APPID",appidOpen).replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_login");
        model.addAttribute("name","liuzp");
        model.addAttribute("oauthUrl",oauthUrl);
        map.put("oauthUrl",oauthUrl);
        return map;
    }


    /**
     * @Description: 授权的回调函数
     * @auther: Sakura
     * @date: 2019/3/8 9:47
     * @param: [request, response]
     * @return: java.lang.String
     */
    @RequestMapping(value = "/openCallBack")
    public String openCallBack(String code,String state,Model model,HttpServletRequest request) throws Exception{
        //1.通过code获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url = url.replace("APPID",appidOpen).replace("SECRET",secretOpen).replace("CODE",code);
        JSONObject tokenInfoObject =  CommonUtil.httpsRequest(url,"GET",null);

        log.info("tokenInfoObject:{}",tokenInfoObject);

        //2.通过access_token和openid获取用户信息
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        userInfoUrl = userInfoUrl.replace("ACCESS_TOKEN",tokenInfoObject.getString("access_token")).replace("OPENID",tokenInfoObject.getString("openid"));
        JSONObject userInfoStr =  CommonUtil.httpsRequest(userInfoUrl,"GET",null);
        log.info("userInfoObject:{}",userInfoStr);

        model.addAttribute("tokenInfoObject",tokenInfoObject);
        model.addAttribute("userInfoObject",userInfoStr);

        User user = userService.getUserByUnionId(userInfoStr.getString("unionid"));
        if (user != null){
            //登录并创建token
            String token = authService.login(user.getAccount(),request);
            new SuccessResponseData(token);
            return "redirect:" + systemUrl;
        }else {
            return "";
        }

    }


    @RequestMapping("/getLinkImage")
    @ResponseBody
    public Map<String, Object> getLinkImage(){
        //  要生成二维码的链接
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        LoginUser user = LoginContextHolder.getContext().getUser();
        //将UUID放入缓存
        ScanPool pool = new ScanPool();
        PoolCache.cacheMap.put(uuid, pool);
        String url = systemUrl + "/weiXin/login?uuid=" + uuid + "&userId=" + user.getId();
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        //	指定二维码图片名字

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

    @RequestMapping("/getCheckImage")
    @ResponseBody
    public Map<String, Object> getCheckImage(String meetId){
        String url = systemUrl + "/weiXin/toCheckSign?type=check&meetId=" + meetId;
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        //	指定二维码图片名字
        LoginUser user = LoginContextHolder.getContext().getUser();
        //String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        String fileName = meetId + "_check.jpg";
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
        map.put("checkName",Base64.encodeBase64String(data));
        return map;
    }
    @RequestMapping("/getSignImage")
    @ResponseBody
    public Map<String, Object> getSignImage(String meetId){
        String url = systemUrl + "/weiXin/toCheckSign?type=sign&meetId=" + meetId;
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        //	指定二维码图片名字
        LoginUser user = LoginContextHolder.getContext().getUser();
        //String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        String fileName = meetId + "_sign.jpg";
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
        map.put("signName",Base64.encodeBase64String(data));
        return map;
    }

    @RequestMapping(value = "/toCheckSign")
    public void toCheckSign(String meetId, String type, HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 回调地址，该域名需要公众号验证
        String backUrl = systemUrl + "/weiXin/checkSign?type="+ type + "&meetId=" + meetId;
        // 向用户申请授权
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
                + "&redirect_uri=" + URLEncoder.encode(backUrl, "UTF-8")
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";

        response.sendRedirect(url);
    }

    @RequestMapping("/checkSign")
    public String checkSign(Model model, String type, String meetId, HttpServletRequest request){
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

        User userExist = userService.getUserByUnionId(userInfo.getString("unionid"));

        if (userExist != null){
            CheckInParam checkInParam = new CheckInParam();
            checkInParam.setUserId(userExist.getUserId());
            if (meetId != null && meetId != ""){
                checkInParam.setMeetOrForum(0);
            }
            CheckIn checkIn = checkInService.getByUser(userExist.getUserId());
            if (type.equals("check")){
                checkInParam.setRegisterStatus(1);
                checkInParam.setRegisterTime(new Date());
            } else if (type.equals("sign")){
                checkInParam.setSignStatus(1);
                checkInParam.setSignTime(new Date());
                checkInParam.setSignPlace("");
            }
            if (checkIn != null){
                if (type.equals("check")){
                    if (checkIn.getRegisterStatus() != null && checkIn.getRegisterStatus() == 1){
                        model.addAttribute("ifCheck","yes");
                    }else {
                        if (checkIn.getSignTime() != null){
                            checkInParam.setSignTime(checkIn.getSignTime());
                        }
                        checkInParam.setCheckId(checkIn.getCheckId());
                        checkInService.update(checkInParam);
                    }
                } else if (type.equals("sign")){
                    if (checkIn.getSignStatus() != null && checkIn.getSignStatus() == 1){
                        model.addAttribute("ifSign","yes");
                    }else {
                        if (checkIn.getRegisterTime() != null){
                            checkInParam.setRegisterTime(checkIn.getRegisterTime());
                        }
                        checkInParam.setCheckId(checkIn.getCheckId());
                        checkInService.update(checkInParam);
                    }
                }
            }else {
                checkInService.add(checkInParam);
            }
            if (type.equals("check")){
                model.addAttribute("checkIn","checkSuccess");
            }else if (type.equals("sign")){
                model.addAttribute("checkIn","signSuccess");
            }

        }else {

        }

        //return "success";
        try {
            return PREFIX + "/weiXinLogin.html";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信扫描异常");
        }
    }






    @RequestMapping("/weChatBand")
    public String weChatBand(Model model){

        return PREFIX + "/weChatBand.html";
    }

    @RequestMapping("/weChatCancelBand")
    @ResponseBody
    public ResponseData weChatCancelBand(Model model){
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        if (loginUser != null){
            this.userService.cancelBand(loginUser.getId());
        }
        return ResponseData.success();
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
                .toUser("oe9p8wrDJD5_Mm9l9Mv9BM9CuKqI")//要推送的用户openid oe9p8wmSOnbvURekVJPo5Xg2ZEDI  oe9p8wrDJD5_Mm9l9Mv9BM9CuKqI
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
