package cn.stylefeng.guns.modular.ownForum.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 自设论坛表控制器
 *
 * @author CHUJIANQIAO
 * @Date 2020-05-18 14:48:09
 */
@Controller
@RequestMapping("/ownForum")
public class OwnForumController extends BaseController {

    private String PREFIX = "/ownForum";

    @Autowired
    private OwnForumService ownForumService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/ownForum.html";
    }

    /**
     * 新增页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/ownForum_add.html";
    }

    /**
     * 审批页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approve")
    public String approve() {
        return PREFIX + "/ownForum_approve.html";
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin() {
        return PREFIX + "/ownForum_detail.html";
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/ownForum_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(OwnForumParam ownForumParam) {
        this.ownForumService.add(ownForumParam);
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
    public ResponseData editItem(OwnForumParam ownForumParam) {
        this.ownForumService.update(ownForumParam);
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
    public ResponseData delete(OwnForumParam ownForumParam) {
        this.ownForumService.delete(ownForumParam);
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
    public ResponseData detail(OwnForumParam ownForumParam) {
        OwnForum detail = this.ownForumService.getById(ownForumParam.getForumId());
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
    public LayuiPageInfo list(OwnForumParam ownForumParam) {
        return this.ownForumService.findPageBySpec(ownForumParam);
    }

}


