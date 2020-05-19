package cn.stylefeng.guns.modular.socialForum.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.socialForum.entity.SocialForum;
import cn.stylefeng.guns.modular.socialForum.model.params.SocialForumParam;
import cn.stylefeng.guns.modular.socialForum.service.SocialForumService;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


/**
 * 社会资助论坛表控制器
 *
 * @author CHUJIANQIAO
 * @Date 2020-05-15 10:37:04
 */
@Controller
@RequestMapping("/socialForum")
public class SocialForumController extends BaseController {

    private String PREFIX = "/socialForum";

    @Autowired
    private SocialForumService socialForumService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("")
    public String index() {
        if (ToolUtil.isAdminRole()){
            return PREFIX + "/socialForum.html";
        }else {
            return PREFIX + "/socialForum_person.html";
        }
    }

    /**
     * 新增页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/socialForum_add.html";
    }

    /**
     * 审批页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approve")
    public String approve() {
        return PREFIX + "/socialForum_approve.html";
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin() {
        return PREFIX + "/socialForum_detail.html";
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/socialForum_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SocialForumParam socialForumParam) {
        this.socialForumService.add(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SocialForumParam socialForumParam) {
        this.socialForumService.update(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 审批通过接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approveForum")
    @ResponseBody
    public ResponseData approveForum(SocialForumParam socialForumParam) {
        socialForumParam.setApplyStatus(2);
        this.socialForumService.update(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 审批驳回接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/rejectForum")
    @ResponseBody
    public ResponseData rejectForum(SocialForumParam socialForumParam) {
        socialForumParam.setApplyStatus(3);
        this.socialForumService.update(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SocialForumParam socialForumParam) {
        this.socialForumService.delete(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 取消接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/cancel")
    @ResponseBody
    public ResponseData cancel(SocialForumParam socialForumParam) {
        socialForumParam.setApplyStatus(0);
        this.socialForumService.update(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 申请接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/editNew")
    @ResponseBody
    public ResponseData editNew(SocialForumParam socialForumParam) {
        socialForumParam.setApplyStatus(1);
        this.socialForumService.update(socialForumParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SocialForumParam socialForumParam) {
        SocialForum detail = this.socialForumService.getById(socialForumParam.getForumId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SocialForumParam socialForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        socialForumParam.setApplyId(userId);
        return this.socialForumService.findPageBySpec(socialForumParam);
    }

    /**
     * 上传文件
     *
     * @author fengshuonan
     * @Date 2019-2-23 10:48:29
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData upload(@RequestPart("file") MultipartFile file) {

        String path = uploadFolder;

        UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
        String fileId = uploadResult.getFileId();

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        map.put("path",uploadResult.getFileSavePath());

        return ResponseData.success(0, "上传成功", map);
    }

}

