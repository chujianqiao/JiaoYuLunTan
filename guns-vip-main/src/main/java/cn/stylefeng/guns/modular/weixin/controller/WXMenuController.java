package cn.stylefeng.guns.modular.weixin.controller;


import cn.stylefeng.guns.modular.weixin.menu.Button;
import cn.stylefeng.guns.modular.weixin.menu.CommonButton;
import cn.stylefeng.guns.modular.weixin.menu.ComplexButton;
import cn.stylefeng.guns.modular.weixin.menu.Menu;
import cn.stylefeng.guns.modular.weixin.pojo.Token;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.modular.weixin.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/weiXinMenu")
@Slf4j
public class WXMenuController {

    @RequestMapping(value = "/createMenu")
    public void createMenu(){
        Map<String, Object> token=TokenUtil.getToken();
        System.out.println(token.get("access_token"));
        // 调用接口获取access_token
        String at = token.get("access_token").toString();

        if (null != at) {
            // 调用接口创建菜单
            int result = CommonUtil.createMenu(getMenu(), at);

            // 判断菜单创建结果
            if (0 == result)
                log.info("菜单创建成功！");
            else
                log.info("菜单创建失败，错误码：" + result);
        }
    }


    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("论坛简介");
        btn11.setType("view");
        //btn11.setKey("11");
//        btn11.setUrl("http://cesf.nies.net.cn:8080/pub/lt_new_6/gylt/ltjj/");
        btn11.setUrl("https://cesf.nies.edu.cn/lt_new_6/gylt/ltjj/");


        CommonButton btn12 = new CommonButton();
        btn12.setName("论坛申报");
        btn12.setType("view");
        //btn12.setKey("12");
//        btn12.setUrl("http://cesf.nies.net.cn/holdForum/add");
        btn12.setUrl("https://cesf.nies.edu.cn/holdForum/add");


        CommonButton btn13 = new CommonButton();
        btn13.setName("新闻速递");
        btn13.setType("media_id");
        btn13.setMedia_id("iCqOxs5LX8aGVLxc317f9n0NRf0KV98LHneHhUNjkxM");

        CommonButton btn14 = new CommonButton();
        btn14.setName("会议日程");
        btn14.setType("view");
        /*btn14.setKey("14");*/
//        btn14.setUrl("http://cesf.nies.net.cn/meet/meetFile");
        btn14.setUrl("https://cesf.nies.edu.cn/meet/meetFile");

        CommonButton btn15 = new CommonButton();
        btn15.setName("问题答疑");
        btn15.setType("click");
        btn15.setKey("15");
        //btn15.setUrl("http://fagexz.natappfree.cc/answer/getAnswerData");

        CommonButton btn21 = new CommonButton();
        btn21.setName("优秀论著展示");
        btn21.setType("view");
        //btn21.setKey("21");
//        btn21.setUrl("http://cesf.nies.net.cn:8080/pub/lt_new_6/ltcg/yxlwj/");
        btn21.setUrl("https://cesf.nies.edu.cn/lt_new_6/ltcg/yxlwj/");

        CommonButton btn22 = new CommonButton();
        btn22.setName("教改实验成果展示");
        btn22.setType("view");
        //btn22.setKey("22");
//        btn22.setUrl("http://cesf.nies.net.cn:8080/pub/lt_new_6/ltcg/jgsy/");
        btn22.setUrl("https://cesf.nies.edu.cn/lt_new_6/ltcg/jgsy/");

        CommonButton btn31 = new CommonButton();
        btn31.setName("我的信息");
        btn31.setType("view");
        //btn31.setKey("31");
//        btn31.setUrl("http://cesf.nies.net.cn/system/person_info");
        btn31.setUrl("https://cesf.nies.edu.cn/system/person_info");

        CommonButton btn32 = new CommonButton();
        btn32.setName("会议注册");
        btn32.setType("view");
        //btn32.setKey("32");
//        btn32.setUrl("http://cesf.nies.net.cn/meetMember/add");
        btn32.setUrl("https://cesf.nies.edu.cn/meetMember/add");

        CommonButton btn33 = new CommonButton();
        btn33.setName("专家评审");
        btn33.setType("view");
        //btn33.setKey("33");
//        btn33.setUrl("http://cesf.nies.net.cn/holdForum/add");
        btn33.setUrl("https://cesf.nies.edu.cn/thesis");

        CommonButton btn34 = new CommonButton();
        btn34.setName("合作意向");
        btn34.setType("view");
        //btn34.setKey("34");
//        btn34.setUrl("http://cesf.nies.net.cn/socialForum/add");
        btn34.setUrl("https://cesf.nies.edu.cn/socialForum/add");

        CommonButton btn35 = new CommonButton();
        btn35.setName("主题征集");
        btn35.setType("view");
        //btn35.setKey("35");
//        btn35.setUrl("http://cesf.nies.net.cn/collectTopic/add");
        btn35.setUrl("https://cesf.nies.edu.cn/collectTopic/add");


        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("论坛活动");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14, btn15 });


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("论坛成果");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22 });


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("个人中心");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32, btn33, btn34, btn35 });


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
}
