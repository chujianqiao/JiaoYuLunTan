package cn.stylefeng.guns.meetRegister.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.meetRegister.wrapper.MeetMemberWrapper;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.guns.sys.core.util.DefaultImages;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议注册成员表控制器
 * @author wucy
 * @Date 2020-05-20 15:32:22
 */
@Controller
@RequestMapping("/meetMember")
public class MeetMemberController extends BaseController {

    private String PREFIX = "/meetMember";

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private OwnForumService ownForumService;

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private UserService userService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("")
    public String index() {
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/meetMember_admin.html";
        }else{
            return PREFIX + "/meetMember.html";
        }
    }

    /**
     * 会议注册页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        //头像
        model.addAttribute("avatar", DefaultImages.defaultAvatarUrl());

        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        User user = userService.getById(loginUser.getId());
        String userTitle = user.getTitle();
        if(userTitle != null && userTitle != ""){
            request.setAttribute("userTitle",userTitle);
        }else{
            request.setAttribute("userTitle","无职称");
        }
        return  "/meet_reg.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/meetMember_edit.html";
    }

    /**
     * 仅查看详情
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/disable")
    public String disable(MeetMemberParam meetMemberParam) {
        return PREFIX + "/meetMember_edit_disable.html";
    }

    /**
     * 选择论坛
     * @author wucy
     * @Date 2020-07-14
     */
    @RequestMapping("/forum")
    public String selectForum(MeetMemberParam meetMemberParam) {
        return PREFIX + "/meetMember_edit_forum.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(MeetMemberParam meetMemberParam) {
        this.meetMemberService.add(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(MeetMemberParam meetMemberParam, ThesisParam thesisParam) {
        meetMemberParam.setMeetStatus(1);
        this.meetMemberService.update(meetMemberParam);
        this.thesisService.update(thesisParam);
        return ResponseData.success();
    }

    /**
     * 取消申请
     * @author wucy
     * @Date 2020-07-13
     */
    @RequestMapping("/cancelApply")
    @ResponseBody
    public ResponseData cancelApply(MeetMemberParam meetMemberParam) {
        meetMemberParam.setMeetStatus(3);
        this.meetMemberService.update(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 更新论坛信息
     * @author wucy
     * @Date 2020-07-13
     */
    @RequestMapping("/editForum")
    @ResponseBody
    public ResponseData editForum(MeetMemberParam meetMemberParam) {
        this.meetMemberService.update(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(MeetMemberParam meetMemberParam,HttpServletRequest request) {
        String thesisIdStr = request.getParameter("thesisId");
        //同时删除论文
        long thesisId = Long.parseLong(thesisIdStr);
        ThesisParam thesisParam = new ThesisParam();
        thesisParam.setThesisId(thesisId);

        this.meetMemberService.delete(meetMemberParam);
        this.thesisService.delete(thesisParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(MeetMemberParam meetMemberParam) {
         MeetMember detail = this.meetMemberService.getById(meetMemberParam.getMemberId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);

        //加入自设论坛名称
        Long ownForumId = detail.getOwnForumid();
        if(ownForumId != null){
            OwnForum ownForum = this.ownForumService.getById(ownForumId);
            String ownForumName = ownForum.getForumName();
            map.put("ownForumName",ownForumName);
        } else {
            map.put("ownForumName","未选择");
        }

        //加入论文题目
        Long thesisId = detail.getThesisId();
        if(thesisId != null){
            Thesis thesis = this.thesisService.getById(thesisId);
            String thesisName = thesis.getThesisTitle();
            map.put("thesisName",thesisName);
        }

        Date date = new Date(Long.parseLong(map.get("regTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("regTime",dateString);

        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(MeetMemberParam meetMemberParam) {
        return this.meetMemberService.findPageBySpec(meetMemberParam);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/wraplist")
    public Object wrapList(MeetMemberParam meetMemberParam,@RequestParam(required = false) String userName) {
        String userIds = "";
        if(userName != null && userName != ""){
            ToolUtil toolUtil = new ToolUtil();
            userIds = toolUtil.getUserIdsForName(userName);
        }

        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            meetMemberParam.setUserId(null);
        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            long userId = user.getId();
            meetMemberParam.setUserId(userId);
        }

        Page<Map<String, Object>> members = this.meetMemberService.findPageWrap(meetMemberParam,userIds);
        Page wrapped = new MeetMemberWrapper(members).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 下载模板文件
     * @author wucy
     */
    @RequestMapping(path = "/downloadThesis")
    public void download(HttpServletResponse httpServletResponse, MeetMemberParam meetMemberParam, HttpServletRequest request) {
        long thesisId = meetMemberParam.getThesisId();
        Thesis thesis = this.thesisService.getById(thesisId);
        //文件完整路径
        String filePath = thesis.getThesisPath();
        //下载后看到的文件名
        String fileName = thesis.getFileName();
        try {
            FileDownload.fileDownload(httpServletResponse, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板文件
     * @author wucy
     */
    @GetMapping(path = "/loadThesisPdf", produces = "application/pdf")
    @ResponseBody
    public void loadPdf(@RequestParam(value = "thesisId", required = false)Long thesisId, HttpServletRequest request, HttpServletResponse response) {
        Thesis thesis = this.thesisService.getById(thesisId);
        //文件完整路径
        String filePath = thesis.getThesisPath();
        try{
            response.setContentType("application/pdf");
            FileInputStream in = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();

            byte[] b = new byte[1024];
            while ((in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        /*if (fileId!=null&&!fileId.equals("")){
            LoginUser loginUser = LoginContextHolder.getContext().getUser();
            //List<MeetMemberResult> list = this.meetMemberService.findListByUserId(loginUser.getId());
            //for (int i = 0;i < list.size();i++){
                this.meetMemberService.updateWord(loginUser.getId(),file.getOriginalFilename(),uploadResult.getFileSavePath());
            //}
        }*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        map.put("path",uploadResult.getFileSavePath());

        return ResponseData.success(0, "上传成功", map);
    }


    /**
     * 更新ppt发言稿
     * @author wucy
     * @Date 2020-07-13
     */
    @RequestMapping("/updateFile")
    @ResponseBody
    public ResponseData updateFile(MeetMemberParam meetMemberParam) {
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        List<MeetMemberResult> list = this.meetMemberService.findListByUserId(loginUser.getId());
        ResponseData responseData = new ResponseData();
        for (int i = 0;i < list.size();i++){
            meetMemberParam.setMemberId(list.get(i).getMemberId());
            this.meetMemberService.update(meetMemberParam);
        }
        if (list.size() != 0){
            responseData.setMessage("success");
            return responseData;
        }else {
            responseData.setMessage("sizeError");
            return responseData;
        }
    }
}


