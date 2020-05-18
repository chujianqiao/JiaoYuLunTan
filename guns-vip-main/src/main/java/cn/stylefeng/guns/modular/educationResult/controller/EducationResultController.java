package cn.stylefeng.guns.modular.educationResult.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.service.EducationResultService;
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
 * @Date 2020-05-18 11:00:07
 */
@Controller
@RequestMapping("/educationResult")
public class EducationResultController extends BaseController {

    private String PREFIX = "/educationResult";

    @Autowired
    private EducationResultService educationResultService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/educationResult.html";
    }

    /**
     * 新增页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/educationResult_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/educationResult_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(EducationResultParam educationResultParam) {
        this.educationResultService.add(educationResultParam);
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
    public ResponseData editItem(EducationResultParam educationResultParam) {
        this.educationResultService.update(educationResultParam);
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
    public ResponseData delete(EducationResultParam educationResultParam) {
        this.educationResultService.delete(educationResultParam);
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
    public ResponseData detail(EducationResultParam educationResultParam) {
        EducationResult detail = this.educationResultService.getById(educationResultParam.getResultId());
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
    public LayuiPageInfo list(EducationResultParam educationResultParam) {
        return this.educationResultService.findPageBySpec(educationResultParam);
    }

}


