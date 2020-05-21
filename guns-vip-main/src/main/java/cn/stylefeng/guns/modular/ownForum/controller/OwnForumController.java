package cn.stylefeng.guns.modular.ownForum.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.OwnForumDict;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


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
        if (ToolUtil.isAdminRole()){
            return PREFIX + "/ownForum.html";
        }else {
            return PREFIX + "/ownForum_person.html";
        }
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
    public String approve(Integer applyType) {
        if (applyType == 1){
            return PREFIX + "/ownForum_approve.html";
        }else {
            return PREFIX + "/ownForum_approveUnit.html";
        }
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin(Integer applyType) {
        if (applyType == 1){
            return PREFIX + "/ownForum_detail.html";
        }else {
            return PREFIX + "/ownForum_detailUnit.html";
        }
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/edit")
    public String edit(Integer applyType,@RequestParam Long forumId) {
        OwnForum ownForum = ownForumService.getById(forumId);
        LogObjectHolder.me().set(ownForum);
        if (applyType == 1){
            return PREFIX + "/ownForum_edit.html";
        }else {
            return PREFIX + "/ownForum_editUnit.html";
        }
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
    @BussinessLog(value = "修改自设论坛申报信息", key = "forumId", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData editItem(OwnForumParam ownForumParam) {
        this.ownForumService.update(ownForumParam);
        return ResponseData.success();
    }

    /**
     * 审批通过接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approveForum")
    @BussinessLog(value = "自设论坛申报审批通过", key = "forumId", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData approveForum(OwnForumParam ownForumParam) {
        ownForumParam.setApplyStatus(2);
        this.ownForumService.update(ownForumParam);
        return ResponseData.success();
    }

    /**
     * 审批驳回接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/rejectForum")
    @BussinessLog(value = "自设论坛申报审批驳回", key = "forumId", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData rejectForum(OwnForumParam ownForumParam) {
        ownForumParam.setApplyStatus(3);
        this.ownForumService.update(ownForumParam);
        return ResponseData.success();
    }

    /**
     * 取消接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/cancel")
    @BussinessLog(value = "自设论坛申报取消", key = "forumId", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData cancel(OwnForumParam ownForumParam) {
        ownForumParam.setApplyStatus(0);
        this.ownForumService.update(ownForumParam);
        return ResponseData.success();
    }

    /**
     * 申请接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/editNew")
    @BussinessLog(value = "自设论坛申报申请", key = "forumId", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData editNew(OwnForumParam ownForumParam) {
        ownForumParam.setApplyStatus(1);
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
    @BussinessLog(value = "删除自设论坛申报信息", key = "forumId", dict = OwnForumDict.class)
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
        Long userId = LoginContextHolder.getContext().getUserId();
        ownForumParam.setApplyId(userId);
        return this.ownForumService.findPageBySpec(ownForumParam);
    }

    /**
     * 查询所有
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @ResponseBody
    @RequestMapping("/listAll")
    public LayuiPageInfo listAll(OwnForumParam ownForumParam) {
        return this.ownForumService.findPageBySpec(ownForumParam);
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


