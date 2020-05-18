package cn.stylefeng.guns.modular.greatResult.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.greatResult.entity.GreatResult;
import cn.stylefeng.guns.modular.greatResult.model.params.GreatResultParam;
import cn.stylefeng.guns.modular.greatResult.service.GreatResultService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 优秀成果表控制器
 *
 * @author CHUJIANQIAO
 * @Date 2020-05-18 10:57:15
 */
@Controller
@RequestMapping("/greatResult")
public class GreatResultController extends BaseController {

    private String PREFIX = "/greatResult";

    @Autowired
    private GreatResultService greatResultService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/greatResult.html";
    }

    /**
     * 新增页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/greatResult_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/greatResult_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(GreatResultParam greatResultParam) {
        this.greatResultService.add(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(GreatResultParam greatResultParam) {
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(GreatResultParam greatResultParam) {
        this.greatResultService.delete(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(GreatResultParam greatResultParam) {
        GreatResult detail = this.greatResultService.getById(greatResultParam.getResultId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(GreatResultParam greatResultParam) {
        return this.greatResultService.findPageBySpec(greatResultParam);
    }

}


