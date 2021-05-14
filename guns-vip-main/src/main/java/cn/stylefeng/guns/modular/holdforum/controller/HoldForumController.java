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
import cn.stylefeng.guns.sys.modular.consts.model.params.SysConfigParam;
import cn.stylefeng.guns.sys.modular.consts.model.result.SysConfigResult;
import cn.stylefeng.guns.sys.modular.consts.service.SysConfigService;
import cn.stylefeng.guns.sys.modular.system.entity.FileInfo;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @Autowired
    private SysConfigService sysConfigService;

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
    public String add(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","holdForum");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        List roles = user.getRoleList();
        long unit = 3;
        String meetTimeStatusStr = ToolUtil.getMeetTimeStatus();
        model.addAttribute("meetTimeStatusStr",meetTimeStatusStr);

        if (roles.contains(unit)){
            if (meetTimeStatusStr == "报名中" || meetTimeStatusStr == "报名结束"){
                return "/unitForum.html";
            }else {
                return  "/meet_status.html";
            }
        } else {
            if (meetTimeStatusStr == "报名中" || meetTimeStatusStr == "报名结束"){
                return "/forum.html";
            }else {
                return  "/meet_status.html";
            }
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
    @BussinessLog(value = "新增承办单位申报信息", key = "forumName", dict = HoldForumDict.class)
    @ResponseBody
    public ResponseData addItem(HoldForumParam holdForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        holdForumParam.setApplyStatus(1);
        holdForumParam.setApplyUser(userId);
        holdForumParam.setApplyTime(new Date());
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
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        //map.put("path",uploadResult.getFileSavePath());
        map.put("path",uploadResult.getFinalName());
        map.put("type",fileType);

        return ResponseData.success(0, "上传成功", map);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/uploadThesisPdf")
    @ResponseBody
    public ResponseData uploadThesisPdf(@RequestPart("file") MultipartFile file) {

        String path = uploadFolder;

        String fileId = "";
        String pathReturn = "";
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        long len  = file.getSize();
        SysConfigParam param = new SysConfigParam();
        param.setCode("FILE_SIZE");
        SysConfigResult sysConfigResult = sysConfigService.findByCode(param);
        String sysFileSize = sysConfigResult.getValue();
        String unit = sysFileSize.substring(sysFileSize.length()-1);
        int size = Integer.parseInt(sysFileSize.substring(0,sysFileSize.length()-1));
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len ;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len  / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len  / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len  / 1073741824;
        }
        HashMap<String, Object> map = new HashMap<>();

        if (fileType.equals(".pdf")){
            if (fileSize <= size) {
                UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
                fileId = uploadResult.getFileId();
                //pathReturn = uploadResult.getFileSavePath();
                pathReturn = uploadResult.getFinalName();
                map.put("sizeInfo", "yes");
            }else {
                map.put("sizeInfo", sysFileSize);
            }
        }


        map.put("fileId", fileId);
        map.put("path",pathReturn);
        map.put("type",fileType);

        return ResponseData.success(0, "上传成功", map);
    }

}


