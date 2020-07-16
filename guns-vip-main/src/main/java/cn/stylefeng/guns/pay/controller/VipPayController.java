package cn.stylefeng.guns.pay.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.pay.entity.VipPay;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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

    /**
     * 跳转到主页面
     *
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/vipPay.html";
    }

    /**
     * 新增页面
     *
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
     *
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
     *
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
     *
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
     *
     * @author wucy
     * @Date 2020-07-15
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(VipPayParam vipPayParam) {
        VipPay detail = this.vipPayService.getById(vipPayParam.getPayId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author wucy
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(VipPayParam vipPayParam) {
        return this.vipPayService.findPageBySpec(vipPayParam);
    }

}


