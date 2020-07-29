package cn.stylefeng.guns.modular.reviewdict.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.reviewdict.entity.ReviewDictionary;
import cn.stylefeng.guns.modular.reviewdict.model.params.ReviewDictionaryParam;
import cn.stylefeng.guns.modular.reviewdict.service.ReviewDictionaryService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 评审字典表控制器
 *
 * @author wucy
 * @Date 2020-07-28 16:29:07
 */
@Controller
@RequestMapping("/reviewDictionary")
public class ReviewDictionaryController extends BaseController {

    private String PREFIX = "/reviewDictionary";

    private final String DISAGREE_NAME = "不同意参会";

    @Autowired
    private ReviewDictionaryService reviewDictionaryService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/reviewDictionary.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/reviewDictionary_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/reviewDictionary_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ReviewDictionaryParam reviewDictionaryParam) {
        //默认激活
        reviewDictionaryParam.setDicStatus(1);
        this.reviewDictionaryService.add(reviewDictionaryParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ReviewDictionaryParam reviewDictionaryParam) {
        ReviewDictionary reviewDictionary = this.reviewDictionaryService.getById(reviewDictionaryParam.getDicId());
        String dicName = reviewDictionary.getDicName();
        if(dicName.equals(DISAGREE_NAME)){
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_DISAGREE);
        }
        this.reviewDictionaryService.update(reviewDictionaryParam);
        return ResponseData.success();
    }

    /**
     * 解冻
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/freeItem")
    @ResponseBody
    public ResponseData freeItem(ReviewDictionaryParam reviewDictionaryParam) {
        reviewDictionaryParam.setDicStatus(1);
        this.reviewDictionaryService.update(reviewDictionaryParam);
        return ResponseData.success();
    }

    /**
     * 冻结
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/unFreeItem")
    @ResponseBody
    public ResponseData unFreeItem(ReviewDictionaryParam reviewDictionaryParam) {
        ReviewDictionary reviewDictionary = this.reviewDictionaryService.getById(reviewDictionaryParam.getDicId());
        String dicName = reviewDictionary.getDicName();
        if(dicName.equals(DISAGREE_NAME)){
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_DISAGREE);
        }
        reviewDictionaryParam.setDicStatus(0);
        this.reviewDictionaryService.update(reviewDictionaryParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ReviewDictionaryParam reviewDictionaryParam) {
        ReviewDictionary reviewDictionary = this.reviewDictionaryService.getById(reviewDictionaryParam.getDicId());
        String dicName = reviewDictionary.getDicName();
        if(dicName.equals(DISAGREE_NAME)){
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_DISAGREE);
        }
        this.reviewDictionaryService.delete(reviewDictionaryParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-07-28
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ReviewDictionaryParam reviewDictionaryParam) {
        ReviewDictionary detail = this.reviewDictionaryService.getById(reviewDictionaryParam.getDicId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-07-28
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ReviewDictionaryParam reviewDictionaryParam) {
        return this.reviewDictionaryService.findPageBySpec(reviewDictionaryParam);
    }

}


