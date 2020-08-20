package cn.stylefeng.guns.meetRegister.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.meetRegister.wrapper.MeetMemberWrapper;
import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.service.ForumService;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private ForumService forumService;

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetService meetService;

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
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/meetMember_edit_admin.html";
        } else {
            return PREFIX + "/meetMember_edit_disable.html";
        }
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
            Forum forum = this.forumService.getById(ownForumId);
            String ownForumName = forum.getForumName();
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
        //个人信息
        Long userId = Long.parseLong(map.get("userId").toString());
        User user = this.userService.getById(userId);
        String userName = user.getName();
        if(userName != null && userName != ""){
            map.put("userName",userName);
        }
        String unitName = user.getWorkUnit();
        if(unitName != null && unitName != ""){
            map.put("unitName",unitName);
        }
        String postName = user.getPost();
        if(postName != null && postName != ""){
            map.put("postName",postName);
        } else {
            String titleName = user.getTitle();
            if(titleName != null && titleName != ""){
                map.put("postName",titleName);
            }
        }
        String direction = user.getDirection();
        if(direction != null && direction != ""){
            map.put("direction",direction);
        }

        Integer meetStatus = Integer.parseInt(map.get("meetStatus").toString());
        if(meetStatus != null){
            if(meetStatus == 4){
                map.put("isPay",1);
            } else {
                map.put("isPay",0);
            }
        }



        map.put("regTime",dateString);
        return ResponseData.success(map);
    }

    /**
     * 选择论坛时查看的详情
     * 主要处理 会议时间、报名时间
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/checkDetail")
    @ResponseBody
    public ResponseData checkDetail(MeetMemberParam meetMemberParam) {
        MeetMember detail = this.meetMemberService.getById(meetMemberParam.getMemberId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);

        MeetParam meetParam = new MeetParam();
        meetParam.setMeetStatus(1);

        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        Map<String,Object> meetMap = meets.getRecords().get(0);
        String begTimeStr = meetMap.get("beginTime").toString();
        String endTimeStr = meetMap.get("endTime").toString();

        String joinBegStr = meetMap.get("joinBegTime").toString();
        String joinEndStr = meetMap.get("joinEndTime").toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        try {
            //会议时间
            Date begTime = simpleDateFormat.parse(begTimeStr);
            String begStr = sdf.format(begTime);
            Date endTime = simpleDateFormat.parse(endTimeStr);
            String endStr = sdf.format(endTime);
            String meetTimeStr = begStr + " 至 " + endStr;
            map.put("meetTimeStr",meetTimeStr);
            //报名时间
            Date joinBegTime = simpleDateFormat.parse(joinBegStr);
            String joinBeg = sdf.format(joinBegTime);
            Date joinEndTime = simpleDateFormat.parse(joinEndStr);
            String joinEnd = sdf.format(joinEndTime);
            String joinTimeStr = joinBeg + " 至 " + joinEnd;
            map.put("joinTimeStr",joinTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        map.put("meetName",meetMap.get("meetName"));
        map.put("place",meetMap.get("place"));
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
        List<Long> userIdList = ToolUtil.getUserIdList(userName);

        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            meetMemberParam.setUserId(null);
        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            long userId = user.getId();
            meetMemberParam.setUserId(userId);
        }

        String listStatus;
        if(userIdList.size() != 0){
            listStatus = "有条件";
        }else{
            listStatus = null;
        }
        Page<Map<String, Object>> members = this.meetMemberService.findPageWrap(meetMemberParam,userIdList,listStatus);
        Page wrapped = new MeetMemberWrapper(members).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 管理员查询列表
     * @author wucy
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/adminList")
    public Object adminList(MeetMemberParam meetMemberParam,@RequestParam(required = false) String roleId,@RequestParam(required = false) String userName) {
        List<Long> userIdList = ToolUtil.getUserIdList(userName);
        String listStatus;
        if(userIdList.size() != 0){
            listStatus = "有条件";
        }else{
            listStatus = null;
        }

        Page<Map<String, Object>> members = this.meetMemberService.findPageWrap(meetMemberParam,userIdList,listStatus);
        List<Map<String, Object>> memberList = members.getRecords();
        //根据角色进行筛选
        Iterator iterator = memberList.iterator();
        while (iterator.hasNext()){
            Map<String, Object> resultMap = (Map<String, Object>) iterator.next();
            Long userId = Long.parseLong(resultMap.get("userId").toString());
            User user = this.userService.getById(userId);
            String myRoleId = user.getRoleId();
            String[] roleArr = myRoleId.split(",");
            if(Arrays.asList(roleArr).contains(roleId)){
                continue;
            }else {
               iterator.remove();
            }
        }
        //将筛选后的list赋值给记录
        members.setRecords(memberList);
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


