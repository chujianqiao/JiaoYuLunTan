package cn.stylefeng.guns.thesis.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.MeetMemberDict;
import cn.stylefeng.guns.core.constant.dictmap.ReviewUnitDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.mapper.MeetMemberMapper;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.sys.modular.rest.service.RestRoleService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.RoleService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.thesis.wrapper.ThesisWrapper;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 论文表控制器
 * @author wucy
 * @Date 2020-05-21 15:15:05
 */
@Controller
@RequestMapping("/thesis")
@Slf4j
public class ThesisController extends BaseController {

    private String PREFIX = "/thesis";

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RestRoleService restRoleService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private ThesisDomainService thesisDomainService;


    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("")
    public String index() {
        boolean isReview = ToolUtil.isReviewRole();
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/thesis_disable.html";
        } else if(isReview){
            return PREFIX + "/thesis_review.html";
        } else{
            return PREFIX + "/thesis.html";
        }
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/thesis_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/thesis_edit.html";
    }

    /**
     * 仅查看
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/disable")
    public String disable() {
        return PREFIX + "/thesis_disable_edit.html";
    }

    /**
     * 分配评审人
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assign")
    public String assign() {
        return PREFIX + "/assign_review.html";
    }

    /**
     * 评审页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/review")
    public String review() {
        return PREFIX + "/thesis_review_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/addItem")
    @ResponseBody
    @BussinessLog(value = "注册会议", key = "thesisTitle", dict = MeetMemberDict.class)
    public ResponseData addItem(ThesisParam thesisParam, MeetMemberParam meetMemberParam, @Valid UserDto user) {
        //用户ID
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        Long userId = loginUser.getId();
        thesisParam.setThesisUser(userId.toString());
        thesisParam.setGreatNum(0);
        thesisParam.setGreat(0);

        user.setUserId(userId);
        meetMemberParam.setUserId(userId);
        //论文ID
        long thesisId = ToolUtil.getNum19();
        thesisParam.setThesisId(thesisId);
        meetMemberParam.setThesisId(thesisId);
        //时间
        Date date = new Date();
        meetMemberParam.setRegTime(date);
        thesisParam.setApplyTime(date);
        //状态
        meetMemberParam.setMeetStatus(1);

        //同时更新用户表、论文表、会议成员表
        this.userService.editUser(user);
        this.thesisService.add(thesisParam);
        this.meetMemberService.add(meetMemberParam);

        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ThesisParam thesisParam) {
        this.thesisService.update(thesisParam);
        return ResponseData.success();
    }

    /**
     * 分配评审专家接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assignItem")
    @ResponseBody
    public ResponseData assignItem(ThesisParam thesisParam) {
        String majors = thesisParam.getReviewUser();
        String [] majorIds = majors.split(",");
        for(int i =0 ;i < majorIds.length ;i++){
            long userId = Long.parseLong(majorIds[i]);
            ReviewMajor reviewMajor = this.reviewMajorService.getById(userId);
            int thesisCount = reviewMajor.getThesisCount();
            thesisCount += 1;
            ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
            reviewMajorParam.setReviewId(userId);
            reviewMajorParam.setThesisCount(thesisCount);
            this.reviewMajorService.update(reviewMajorParam);
        }
        this.thesisService.update(thesisParam);
        return ResponseData.success();
    }

    /**
     * 评审接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/reviewItem")
    @ResponseBody
    public ResponseData reviewItem(ThesisParam thesisParam) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        String userIdStr = user.getId().toString();

        Thesis thesis = this.thesisService.getById(thesisParam.getThesisId());
        String reviewUser = thesis.getReviewUser();
        if(reviewUser != null && reviewUser.length() != 0){
            reviewUser += "," + userIdStr;
        }else{
            reviewUser = userIdStr;
        }
        thesisParam.setReviewUser(reviewUser);
//        String greatUsers = thesisParam.getGreatId();
//        if(greatUsers == null){
//            greatUsers = "";
//        }
        //推优逻辑
//        Integer greatNum = thesisParam.getGreatNum();
//        Integer isGreat = thesisParam.getIsgreat();
//        if(isGreat != null && isGreat == 1 && (greatUsers.indexOf(userIdStr) == -1)){
//            int len = greatUsers.length();
//            if(len != 0){
//                greatUsers += "," + userIdStr;
//            }else {
//                greatUsers = userIdStr;
//            }
//            greatNum +=1;
//        }
//        if(greatNum >= 2){
//            thesisParam.setGreat(1);
//        }
//        thesisParam.setGreatId(greatUsers);
//        thesisParam.setGreatNum(greatNum);

        //同时修改会议状态
        long thesisId = thesisParam.getThesisId();
        MeetMemberParam meetMemberParam = new MeetMemberParam();
        meetMemberParam.setThesisId(thesisId);
        List<MeetMemberResult> members = this.meetMemberService.customList(meetMemberParam);
        MeetMemberResult meetMemberResult = members.get(0);
        long memberId = meetMemberResult.getMemberId();
        meetMemberParam.setMemberId(memberId);
        int reviewNum = thesisParam.getReviewResult();
        if(reviewNum == 0){
            meetMemberParam.setMeetStatus(5);
            //未通过，评审字典内容为空
            thesisParam.setStatus(null);
        }else {
            meetMemberParam.setMeetStatus(2);
        }

        this.thesisService.update(thesisParam);
        this.meetMemberService.update(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ThesisParam thesisParam) {
        this.thesisService.delete(thesisParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ThesisParam thesisParam) {
        Thesis detail = this.thesisService.getById(thesisParam.getThesisId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        Integer reviewNum = detail.getReviewResult();
        if(reviewNum != null){
            String reviewStr = TransTypeUtil.getIsPass().get(reviewNum).toString();
            map.put("reviewStr",reviewStr);
        }else {
            map.put("reviewStr","未评审");
        }

        Integer isGreatNum = detail.getGreat();
        if(isGreatNum != null){
            String isGreatStr = TransTypeUtil.getIsOrNo().get(isGreatNum).toString();
            map.put("isGreatStr",isGreatStr);
        }

        String domainObj = detail.getBelongDomain();
        String belongDomainStr = "";

        if (domainObj.equals("") || domainObj == null){
            belongDomainStr = "";
        }else {
            String[] domainList = domainObj.split(",");
            for (int i = 0;i < domainList.length;i++){
                Long pid = Long.parseLong(domainList[i]);
                if (pid == null) {
                    belongDomainStr = belongDomainStr + "";
                } else if (pid == 0L) {
                    belongDomainStr = belongDomainStr + "顶级;";
                } else {
                    ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(pid);
                    if (cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult) && cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult.getDomainName())) {
                        belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
                    }
                }
            }
        }
        map.put("belongDomainStr",belongDomainStr);

        Date date = new Date(Long.parseLong(map.get("applyTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("applyTime",dateString);

        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ThesisParam thesisParam) {
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return this.thesisService.findPageBySpec(thesisParam);
        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            Long userId = user.getId();
            thesisParam.setThesisUser(userId.toString());
            return this.thesisService.findPageBySpec(thesisParam);
        }

    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(ThesisParam thesisParam) {
        boolean isAdmin = ToolUtil.isAdminRole();
        boolean isReview = ToolUtil.isReviewRole();
        if(isAdmin){
            thesisParam.setThesisUser(null);
        } else if (isReview){
            LoginUser user = LoginContextHolder.getContext().getUser();
            Long userId = user.getId();
            thesisParam.setReviewUser(userId.toString());
        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            Long userId = user.getId();
            thesisParam.setThesisUser(userId.toString());
        }
        Page<Map<String, Object>> theses = this.thesisService.findPageWrap(thesisParam);
        Page wrapped = new ThesisWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }


    /**
     * 上传文件
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData thesisUpload(@RequestPart("file") MultipartFile file,HttpServletRequest request) {
        String fileSavePath = ConstantsContext.getFileUploadPath();
        UploadResult uploadResult = this.fileInfoService.uploadFile(file,fileSavePath);
        String fileId = uploadResult.getFileId();

        String idStr = request.getParameter("thesisId");
        long thesisId = Long.parseLong(idStr);
        ThesisParam thesisParam = new ThesisParam();
        thesisParam.setThesisId(thesisId);
        String orginName = file.getOriginalFilename();
        thesisParam.setFileName(orginName);
        thesisParam.setThesisPath(uploadResult.getFileSavePath());
        //更新论文表中的数据
        this.thesisService.update(thesisParam);

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);

        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 根据领域查询论文评审专家
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/majorList")
    public Object majorList(ThesisParam thesisParam) {

        return this.reviewMajorService.majorMapList(thesisParam.getBelongDomain());
    }

}


