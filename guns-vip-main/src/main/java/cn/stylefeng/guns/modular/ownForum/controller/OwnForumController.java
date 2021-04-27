package cn.stylefeng.guns.modular.ownForum.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.OwnForumDict;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.model.params.OwnForumParam;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.guns.modular.ownForum.wrapper.OwnForumWrapper;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.consts.model.params.SysConfigParam;
import cn.stylefeng.guns.sys.modular.consts.model.result.SysConfigResult;
import cn.stylefeng.guns.sys.modular.consts.service.SysConfigService;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private MeetService meetService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("")
    public String index(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","ownForum");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
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
    public String approve(Integer applyType) {
        //if (applyType == 1){
            return PREFIX + "/ownForum_approve.html";
        //}else {
        //    return PREFIX + "/ownForum_approveUnit.html";
        //}
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin(Integer applyType, Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl", "ownForum");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        if (LoginContextHolder.getContext().isAdmin()) {
            //if (applyType == 1) {
                return PREFIX + "/ownForum_detail.html";
            //} else {
            //    return PREFIX + "/ownForum_detailUnit.html";
            //}
        }else {
            return PREFIX + "/ownForum_detail_person.html";
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
        //if (applyType == 1){
            return PREFIX + "/ownForum_edit.html";
        //}else {
        //    return PREFIX + "/ownForum_editUnit.html";
        //}
    }

    /**
     * 设置页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/setUp")
    public String setUp(@RequestParam Long forumId) {
        OwnForum ownForum = ownForumService.getById(forumId);
        LogObjectHolder.me().set(ownForum);
        return PREFIX + "/ownForum_setUp.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-18
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "新增自设论坛申报信息", key = "forumName", dict = OwnForumDict.class)
    @ResponseBody
    public ResponseData addItem(OwnForumParam ownForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        ownForumParam.setApplyStatus(1);
        ownForumParam.setApplyId(userId);
        ownForumParam.setApplyTime(new Date());
        Meet meet = meetService.getByStatus(1);
        if (meet != null){
            ownForumParam.setMeetId(meet.getMeetId());
        }
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

    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(OwnForumParam ownForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        ownForumParam.setApplyId(userId);
        if (ownForumParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                ownForumParam.setMeetId(meet.getMeetId());
            }
        } else if (ownForumParam.getMeetId() == 0) {
            ownForumParam.setMeetId(null);
        }
        Page<Map<String, Object>> theses = this.ownForumService.findPageWrap(ownForumParam);
        Page wrapped = new OwnForumWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
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
        if (fileSize <= size) {
            map.put("fileId", fileId);
            map.put("path",uploadResult.getFileSavePath());
            return ResponseData.success(0, "上传成功", map);
        }else {
            map.put("status","大小问题");
            return ResponseData.success(0, "上传失败，文件大小超过限制，请上传"+sysFileSize+"以内的文件。", map);
        }


    }
}


