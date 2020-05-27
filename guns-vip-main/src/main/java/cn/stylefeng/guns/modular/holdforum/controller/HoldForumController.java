package cn.stylefeng.guns.modular.holdforum.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.HoldForumDict;
import cn.stylefeng.guns.modular.holdforum.entity.HoldForum;
import cn.stylefeng.guns.modular.holdforum.model.params.HoldForumParam;
import cn.stylefeng.guns.modular.holdforum.service.HoldForumService;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.entity.FileInfo;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

import static cn.stylefeng.guns.excel.consts.ExcelConstants.EXCEL_FILE_TEMPLATE_PATH;


/**
 * 承办方论坛表控制器
 *
 * @author 
 * @Date 2020-05-13 11:13:04
 */
@Controller
@RequestMapping("/holdForum")
public class HoldForumController extends BaseController {

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    private String PREFIX = "/holdForum";

    @Autowired
    private HoldForumService holdForumService;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("")
    public String index() {
        //Long userId = LoginContextHolder.getContext().getUserId();
        if (ToolUtil.isAdminRole()){
            return PREFIX + "/holdForum.html";
        }else {
            return PREFIX + "/holdForum_person.html";
        }
    }

    /**
     * 新增页面
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/add")
    public String add() {
        LoginUser user = LoginContextHolder.getContext().getUser();
        List roles = user.getRoleList();
        long unit = 3;
        if (roles.contains(unit)){
            return "/unitForum.html";
        } else {
            return "/forum.html";
        }
    }

    /**
     * 审批页面
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/approve")
    public String approve() {
        return PREFIX + "/holdForum_approve.html";
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin() {
        return PREFIX + "/holdForum_detail.html";
    }

    /**
     * 编辑页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam Long forumId) {
        HoldForum holdForum = holdForumService.getById(forumId);
        LogObjectHolder.me().set(holdForum);
        return PREFIX + "/holdForum_edit.html";
    }

    /**
     * 新增接口
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "新增承办单位申报信息", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData addItem(HoldForumParam holdForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        holdForumParam.setApplyStatus(1);
        holdForumParam.setApplyUser(userId);
        this.holdForumService.add(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/editItem")
    @BussinessLog(value = "修改承办单位申报信息", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData editItem(HoldForumParam holdForumParam) {
        this.holdForumService.update(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 审批通过接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approveForum")
    @BussinessLog(value = "承办单位申报审批通过", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData approveForum(HoldForumParam holdForumParam) {
        holdForumParam.setApplyStatus(2);
        this.holdForumService.update(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 审批驳回接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/rejectForum")
    @BussinessLog(value = "承办单位申报审批驳回", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData rejectForum(HoldForumParam holdForumParam) {
        holdForumParam.setApplyStatus(3);
        this.holdForumService.update(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除承办单位申报信息", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData delete(HoldForumParam holdForumParam) {
        this.holdForumService.delete(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 取消接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/cancel")
    @BussinessLog(value = "承办单位申报取消", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData cancel(HoldForumParam holdForumParam) {
        holdForumParam.setApplyStatus(0);
        this.holdForumService.update(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 申请接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/editNew")
    @BussinessLog(value = "承办单位申报申请", key = "forumId", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData editNew(HoldForumParam holdForumParam) {
        holdForumParam.setApplyStatus(1);
        this.holdForumService.update(holdForumParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author 
     * @Date 2020-05-13
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(HoldForumParam holdForumParam) {
        HoldForum detail = this.holdForumService.getById(holdForumParam.getForumId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author 
     * @Date 2020-05-13
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(HoldForumParam holdForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        holdForumParam.setApplyUser(userId);
        return this.holdForumService.findPageBySpec(holdForumParam);
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


