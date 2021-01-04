package cn.stylefeng.guns.modular.forum.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.model.params.ForumParam;
import cn.stylefeng.guns.modular.forum.service.ForumService;
import cn.stylefeng.guns.modular.forum.wrapper.ForumWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 分论坛表控制器
 *
 * @author CHU
 * @Date 2020-08-10 15:32:46
 */
@Controller
@RequestMapping("/forum")
public class ForumController extends BaseController {

    private String PREFIX = "/forum";

    @Autowired
    private ForumService forumService;
    @Autowired
    private MeetService meetService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/forum.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/forum_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/forum_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ForumParam forumParam) {
        forumParam.setExistNum(0);
        forumParam.setStatus(0);
        Meet meet = meetService.getByStatus(1);
        if (meet != null){
            forumParam.setMeetId(meet.getMeetId());
        }

        this.forumService.add(forumParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ForumParam forumParam) {
        this.forumService.update(forumParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ForumParam forumParam) {
        this.forumService.delete(forumParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ForumParam forumParam) {
        Forum detail = this.forumService.getById(forumParam.getForumId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ForumParam forumParam) {
        return this.forumService.findPageBySpec(forumParam);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(ForumParam forumParam) {
        if (forumParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                forumParam.setMeetId(meet.getMeetId());
            }
        } else if (forumParam.getMeetId() == 0) {
            forumParam.setMeetId(null);
        }
        Page<Map<String, Object>> forum = this.forumService.findPageWrap(forumParam);
        Page wrapped = new ForumWrapper(forum).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-10
     */
    @RequestMapping("/publish")
    @ResponseBody
    public ResponseData publish(@RequestParam("forumIds") String forumIds) {
        String[] forumIdStrs = forumIds.split(";");
        for (int i = 0;i < forumIdStrs.length;i++){
            String forumIdStr = forumIdStrs[i];
            Long forumId = Long.parseLong(forumIdStr);
            int status = 1;
            this.forumService.setStatus(forumId, status);
        }
        return ResponseData.success();
    }
}


