package cn.stylefeng.guns.modular.socialForum.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.SocialForumDict;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.socialForum.entity.SocialForum;
import cn.stylefeng.guns.modular.socialForum.model.params.SocialForumParam;
import cn.stylefeng.guns.modular.socialForum.service.SocialForumService;
import cn.stylefeng.guns.modular.socialForum.wrapper.SocialForumWrapper;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private MeetService meetService;
    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("")
    public String index(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","socialForum");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
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
    public String add(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","socialForum");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        String meetTimeStatusStr = ToolUtil.getMeetTimeStatus();
        model.addAttribute("meetTimeStatusStr",meetTimeStatusStr);
        if (meetTimeStatusStr == "报名中" || meetTimeStatusStr == "报名结束"){
            return "/social.html";
        }else {
            return  "/meet_status.html";
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
    public String edit(@RequestParam Long forumId) {
        SocialForum socialForum = socialForumService.getById(forumId);
        LogObjectHolder.me().set(socialForum);
        return PREFIX + "/socialForum_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-15
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "新增论坛资助申报信息", key = "forumName", dict = SocialForumDict.class)
    @ResponseBody
    public ResponseData addItem(SocialForumParam socialForumParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        Meet meet = meetService.getByStatus(1);
        if (meet != null){
            socialForumParam.setMeetId(meet.getMeetId());
        }
        socialForumParam.setApplyStatus(1);
        socialForumParam.setApplyId(userId);
        socialForumParam.setApplyTime(new Date());
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
    @BussinessLog(value = "修改论坛资助申报信息", key = "forumId", dict = SocialForumDict.class)
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
    @BussinessLog(value = "论坛资助申报审批通过", key = "forumId", dict = SocialForumDict.class)
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
    @BussinessLog(value = "论坛资助申报审批驳回", key = "forumId", dict = SocialForumDict.class)
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
    @BussinessLog(value = "删除论坛资助申报信息", key = "forumId", dict = SocialForumDict.class)
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
    @BussinessLog(value = "论坛资助申报取消", key = "forumId", dict = SocialForumDict.class)
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
    @BussinessLog(value = "论坛资助申报申请", key = "forumId", dict = SocialForumDict.class)
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
        if (socialForumParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                socialForumParam.setMeetId(meet.getMeetId());
            }
        } else if (socialForumParam.getMeetId() == 0) {
            socialForumParam.setMeetId(null);
        }
        Page<Map<String, Object>> socialForum = this.socialForumService.findPageBySpec(socialForumParam);
        Page wrapped = new SocialForumWrapper(socialForum).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
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
        //获取文件名及文件类型
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));

        UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
        String fileId = uploadResult.getFileId();

        HashMap<String, Object> map = new HashMap<>();
        if((".doc").equalsIgnoreCase(fileType) || ".docx".equalsIgnoreCase(fileType)|| ".pdf".equalsIgnoreCase(fileType)) {

            map.put("fileId", fileId);
            //map.put("path",uploadResult.getFileSavePath());
            map.put("path", uploadResult.getFinalName());
            return ResponseData.success(0, "上传成功", map);
        }else{
            map.put("status","格式问题");
            return ResponseData.success(0, "上传失败，文件格式不匹配", map);
        }
    }

}


