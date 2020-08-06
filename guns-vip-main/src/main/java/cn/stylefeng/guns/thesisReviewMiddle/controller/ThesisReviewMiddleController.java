package cn.stylefeng.guns.thesisReviewMiddle.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesisReviewMiddle.entity.ThesisReviewMiddle;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 论文专家中间表控制器
 *
 * @author CHU
 * @Date 2020-08-05 16:20:10
 */
@Controller
@RequestMapping("/thesisReviewMiddle")
public class ThesisReviewMiddleController extends BaseController {

    private String PREFIX = "/thesisReviewMiddle";

    @Autowired
    private ThesisReviewMiddleService thesisReviewMiddleService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/thesisReviewMiddle.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/thesisReviewMiddle_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/thesisReviewMiddle_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.add(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.update(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.delete(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        ThesisReviewMiddle detail = this.thesisReviewMiddleService.getById(thesisReviewMiddleParam.getMiddleId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        return this.thesisReviewMiddleService.findPageBySpec(thesisReviewMiddleParam);
    }

}


