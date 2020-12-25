package cn.stylefeng.guns.pay.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.pay.entity.VipPay;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.guns.pay.wrapper.VipPayWrapper;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 会员缴费表控制器
 *
 * @author wucy
 * @Date 2020-07-15 14:26:55
 */
@Controller
@RequestMapping("/vipPay")
public class VipPayController extends BaseController {

    private String PREFIX = "/vipPay";

    @Autowired
    private VipPayService vipPayService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetService meetService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/vipPay.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/vipPay_add.html";
    }

    /**
     * 编辑页面
     *
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/vipPay_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(VipPayParam vipPayParam) {
        this.vipPayService.add(vipPayParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(VipPayParam vipPayParam) {
        this.vipPayService.update(vipPayParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(VipPayParam vipPayParam) {
        this.vipPayService.delete(vipPayParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(VipPayParam vipPayParam) {
        VipPay detail = this.vipPayService.getById(vipPayParam.getPayId());
        String userName = userService.getById(detail.getPayUser()).getName();
        String payTypeStr = TransTypeUtil.getPayType().get(detail.getPayType()).toString();
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        map.put("userName",userName);
        map.put("payTypeStr",payTypeStr);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time",sdf.format(detail.getPayTime()));
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(VipPayParam vipPayParam) {
        return this.vipPayService.findPageBySpec(vipPayParam);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(VipPayParam vipPayParam,String userName){
        List<User> userList = new ArrayList<>();
        userList = userService.listByRole("", userName);
        List<Long> userIds = new ArrayList<>();
        if (userList.size() > 0){
            for (int i = 0;i < userList.size();i++){
                userIds.add(userList.get(i).getUserId());
            }
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
        }

        if (vipPayParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            vipPayParam.setMeetId(meet.getMeetId());
        } else if (vipPayParam.getMeetId() == 0) {
            vipPayParam.setMeetId(null);
        }

        Page<Map<String, Object>> pays = this.vipPayService.findPageWrap(vipPayParam,userIds);
        Page wrapped = new VipPayWrapper(pays).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}


