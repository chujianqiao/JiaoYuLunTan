package cn.stylefeng.guns.modular.educationReviewMiddle.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.service.EducationReviewMiddleService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 教改成果专家中间表控制器
 *
 * @author CHU
 * @Date 2020-08-18 15:57:03
 */
@Controller
@RequestMapping("/educationReviewMiddle")
public class EducationReviewMiddleController extends BaseController {

    private String PREFIX = "/educationReviewMiddle";

    @Autowired
    private EducationReviewMiddleService educationReviewMiddleService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/educationReviewMiddle.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/educationReviewMiddle_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/educationReviewMiddle_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(EducationReviewMiddleParam educationReviewMiddleParam) {
        this.educationReviewMiddleService.add(educationReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(EducationReviewMiddleParam educationReviewMiddleParam) {
        this.educationReviewMiddleService.update(educationReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(EducationReviewMiddleParam educationReviewMiddleParam) {
        this.educationReviewMiddleService.delete(educationReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(EducationReviewMiddleParam educationReviewMiddleParam) {
        EducationReviewMiddle detail = this.educationReviewMiddleService.getById(educationReviewMiddleParam.getMiddleId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(EducationReviewMiddleParam educationReviewMiddleParam) {
        return this.educationReviewMiddleService.findPageBySpec(educationReviewMiddleParam);
    }

}


