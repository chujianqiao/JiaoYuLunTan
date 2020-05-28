package cn.stylefeng.guns.collTopic.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.collTopic.entity.CollectTopic;
import cn.stylefeng.guns.collTopic.model.params.CollectTopicParam;
import cn.stylefeng.guns.collTopic.service.CollectTopicService;
import cn.stylefeng.guns.collTopic.wrapper.CollectTopicWrapper;
import cn.stylefeng.guns.expert.wrapper.ReviewMajorWrapper;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;


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
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/collectTopic.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return "/collect.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/collectTopic_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CollectTopicParam collectTopicParam) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        collectTopicParam.setCreateUser(user.getId());
        collectTopicParam.setCreateTime(new Date());
        this.collectTopicService.add(collectTopicParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
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
     * @author wucy
     * @Date 2020-05-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CollectTopicParam collectTopicParam) {
        return this.collectTopicService.findPageBySpec(collectTopicParam);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-18
     */
    @ResponseBody
    @RequestMapping("/wraplist")
    public Object wrapList(CollectTopicParam collectTopicParam) {
        //普通用户查询自己创建的主题，
        LoginUser user = LoginContextHolder.getContext().getUser();
        long userId = user.getId();
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            //为0时忽略该条件
            userId = 0;
        }
        collectTopicParam.setCreateUser(userId);

        Page<Map<String, Object>> topics = this.collectTopicService.findPageWrap(collectTopicParam);
        Page wrapped = new CollectTopicWrapper(topics).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}


