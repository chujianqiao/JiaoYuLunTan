package cn.stylefeng.guns.thesis.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 论文表控制器
 *
 * @author wucy
 * @Date 2020-05-21 15:15:05
 */
@Controller
@RequestMapping("/thesis")
public class ThesisController extends BaseController {

    private String PREFIX = "/thesis";

    @Autowired
    private ThesisService thesisService;

    /**
     * 跳转到主页面
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/thesis.html";
    }

    /**
     * 新增页面
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/thesis_add.html";
    }

    /**
     * 编辑页面
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/thesis_edit.html";
    }

    /**
     * 新增接口
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ThesisParam thesisParam) {
        this.thesisService.add(thesisParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ThesisParam thesisParam) {
        this.thesisService.update(thesisParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ThesisParam thesisParam) {
        this.thesisService.delete(thesisParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ThesisParam thesisParam) {
        Thesis detail = this.thesisService.getById(thesisParam.getThesisId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ThesisParam thesisParam) {

        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return this.thesisService.findPageBySpec(thesisParam);
        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            Long userId = user.getId();
            thesisParam.setThesisUser(userId.toString());
            return this.thesisService.findPageBySpec(thesisParam);
        }

    }

}


