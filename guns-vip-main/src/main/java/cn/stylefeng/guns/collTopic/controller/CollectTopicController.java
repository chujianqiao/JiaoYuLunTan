package cn.stylefeng.guns.collTopic.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.collTopic.entity.CollectTopic;
import cn.stylefeng.guns.collTopic.model.params.CollectTopicParam;
import cn.stylefeng.guns.collTopic.service.CollectTopicService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 论坛主题征集表控制器
 *
 * @author wucy
 * @Date 2020-05-18 14:11:10
 */
@Controller
@RequestMapping("/collectTopic")
public class CollectTopicController extends BaseController {

    private String PREFIX = "/collectTopic";

    @Autowired
    private CollectTopicService collectTopicService;

    /**
     * 跳转到主页面
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/collectTopic.html";
    }

    /**
     * 新增页面
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/collectTopic_add.html";
    }

    /**
     * 编辑页面
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/collectTopic_edit.html";
    }

    /**
     * 新增接口
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CollectTopicParam collectTopicParam) {
        this.collectTopicService.add(collectTopicParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(CollectTopicParam collectTopicParam) {
        this.collectTopicService.update(collectTopicParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(CollectTopicParam collectTopicParam) {
        this.collectTopicService.delete(collectTopicParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(CollectTopicParam collectTopicParam) {
        CollectTopic detail = this.collectTopicService.getById(collectTopicParam.getTopicId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author wucy
     * @Date 2020-05-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CollectTopicParam collectTopicParam) {
        return this.collectTopicService.findPageBySpec(collectTopicParam);
    }

}


