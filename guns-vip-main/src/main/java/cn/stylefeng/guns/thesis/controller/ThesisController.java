package cn.stylefeng.guns.thesis.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.MeetMemberDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.thesis.wrapper.ThesisWrapper;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;


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

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private ThesisDomainService thesisDomainService;

    @Autowired
    private ThesisReviewMiddleService thesisReviewMiddleService;

    @Autowired
    private MeetService meetService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("")
    public String index(Model model) {
        boolean isReview = ToolUtil.isReviewRole();
        boolean isAdmin = ToolUtil.isAdminRole();
        model.addAttribute("menuUrl","thesis");
        model.addAttribute("isReview", "yes");
        if(isAdmin){
            return PREFIX + "/thesis_disable.html";
        } else if(isReview){
            return PREFIX + "/thesis_review.html";
        } else{
            return "没有权限";
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
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/thesis_admin_edit.html";
        } else {
            return PREFIX + "/thesis_disable_edit.html";
        }
    }

    /**
     * 初评后查看详情
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/firstDetail")
    public String firstDetail(Model model) {
        model.addAttribute("menuUrl", "thesis");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        return PREFIX + "/thesis_disable_firstDetail.html";
    }

    /**
     * 复评后查看详情
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/secondDetail")
    public String secondDetail(Model model) {
        model.addAttribute("menuUrl", "thesis");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        return PREFIX + "/thesis_disable_secondDetail.html";
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
     * 分配评审人
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assignAgain")
    public String assignAgain() {
        return PREFIX + "/assignAgain_review.html";
    }

    /**
     * 分配评审人
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assignSelect")
    public String assignSelect() {
        return PREFIX + "/assignSelect_review.html";
    }

    /**
     * 初评页面
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/review")
    public String review(Model model) {
        model.addAttribute("menuUrl", "thesis");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        return PREFIX + "/thesis_review_edit.html";
    }

    /**
     * 复评页面
     * @author wucy
     * @Date 2020-08-13
     */
    @RequestMapping("/reviewAgain")
    public String reviewAgain(Model model) {
        model.addAttribute("menuUrl", "thesis");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        return PREFIX + "/thesis_review_editAgain.html";
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
        Meet pubMeet = this.meetService.getByStatus(1);
        //检查报名人数
        checkMeetNum(pubMeet);
        //大小会
        String meetSize = pubMeet.getSize();
        //用户ID
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        Long userId = loginUser.getId();
        thesisParam.setThesisUser(userId.toString());
        thesisParam.setGreatNum(0);
        thesisParam.setGreat(0);
        thesisParam.setReviewBatch(1);

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

        Meet meet = meetService.getByStatus(1);
        meetMemberParam.setMeetId(meet.getMeetId());
        //同时更新用户表、论文表、会议成员表
        this.userService.editUser(user);
        if("big".equals(meetSize)){
            //大会添加论文，小会不添加
            this.thesisService.add(thesisParam);
        }else if("small".equals(meetSize)){
            //小会直接提交通过
            meetMemberParam.setMeetStatus(7);
            meetMemberParam.setThesisId(null);
        }
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
        long thesisId = thesisParam.getThesisId();
        Integer isPass = thesisParam.getReviewResult();
        if(isPass != null){
            MeetMemberParam meetMemberParam = new MeetMemberParam();
            meetMemberParam.setThesisId(thesisId);
            LayuiPageInfo members = this.meetMemberService.findPageBySpec(meetMemberParam);
            List<MeetMemberResult> memberResults = members.getData();
            MeetMemberResult meetMemberResult = memberResults.get(0);
            long memberId = meetMemberResult.getMemberId();
            //设置参会成员ID
            meetMemberParam.setMemberId(memberId);
            if(isPass == 1){
                meetMemberParam.setMeetStatus(2);
            }else if(isPass == 0){
                meetMemberParam.setMeetStatus(5);
            }
            this.meetMemberService.update(meetMemberParam);
        }
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
    public ResponseData assignItem(ThesisParam thesisParam, String thesisIds) {
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

            String[] thesisId = thesisIds.split(";");
            for (int j = 0;j < thesisId.length;j++){
                ThesisReviewMiddleParam param = new ThesisReviewMiddleParam();
                param.setThesisId(Long.parseLong(thesisId[j]));
                param.setUserId(userId);
                //param.setScore(0);
                param.setReviewSort(thesisParam.getReviewBatch());
                this.thesisReviewMiddleService.add(param);
            }

        }
        this.thesisService.update(thesisParam);
        return ResponseData.success();
    }

    /**
     * 初评接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/reviewItem")
    @ResponseBody
    public ResponseData reviewItem(ThesisParam thesisParam) {
        int reviewNum = thesisParam.getReviewResult();
        //通过，检查参会人数；拒绝则不检查
        if(reviewNum == 1){
            checkMeetRealNum();
        }
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
        thesisParam.setReviewBatch(2);

        //同时修改会议状态
        long thesisId = thesisParam.getThesisId();
        MeetMemberParam meetMemberParam = new MeetMemberParam();
        meetMemberParam.setThesisId(thesisId);
        List<MeetMemberResult> members = this.meetMemberService.customList(meetMemberParam);
        MeetMemberResult meetMemberResult = members.get(0);
        long memberId = meetMemberResult.getMemberId();
        meetMemberParam.setMemberId(memberId);
        if(reviewNum == 0){
            meetMemberParam.setMeetStatus(5);
            //未通过，评审字典内容为空
            thesisParam.setStatus(null);
        }else {
            meetMemberParam.setMeetStatus(2);
        }

        //同时修改中间表，查询条件：论文ID、专家ID、评审顺序（1）
        ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
        middleParam.setThesisId(thesisId);
        middleParam.setUserId(Long.parseLong(userIdStr));
        middleParam.setReviewSort(1);
        LayuiPageInfo midRes = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midList = midRes.getData();
        ThesisReviewMiddleResult middleResult = midList.get(0);
        middleParam.setMiddleId(middleResult.getMiddleId());
        middleParam.setScore(thesisParam.getScore());
        Date reviewDate = new Date();
        middleParam.setReviewTime(reviewDate);
        thesisParam.setReviewTime(reviewDate);

        //同时修改专家表
        ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
        long reviewId = Long.parseLong(userIdStr);
        ReviewMajor reviewMajor = this.reviewMajorService.getById(reviewId);
        if(reviewMajor != null){
            Integer reviewCount = reviewMajor.getReviewCount();
            if (reviewCount == null){
                reviewCount = 1;
            }else {
                reviewCount += 1;
            }
            reviewMajorParam.setReviewCount(reviewCount);
        }
        reviewMajorParam.setReviewId(reviewId);

        this.thesisService.update(thesisParam);
        this.meetMemberService.update(meetMemberParam);
        this.thesisReviewMiddleService.update(middleParam);
        this.reviewMajorService.update(reviewMajorParam);

        String templateId = "cLgN9uptYs5OAM6cSTeyHZxsRatqzhuJa4b6kTSRaA4";
        User resultUser = userService.getById(meetMemberResult.getUserId());
        String userWechatId = resultUser.getWechatId();
        if (userWechatId != null && userWechatId != ""){
            String first = "您的论文已初评完毕";
            String remark = "您可登录中国教育科学论坛平台查看详细信息。";
            String reviewResult = "";
            if (reviewNum == 0){
                reviewResult = "不同意参会";
            }
            if (reviewNum == 1){
                reviewResult = "同意参会;" + thesisParam.getStatus();
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(middleParam.getReviewTime());
            List<String> dataList = new ArrayList<>();
            dataList.add("论文");
            dataList.add(reviewResult);
            dataList.add(time);
            CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
        }

        return ResponseData.success();
    }

    /**
     * 复评接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/reviewItemAgain")
    @ResponseBody
    public ResponseData reviewItemAgain(ThesisParam thesisParam) {
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
//        thesisParam.setReviewBatch(2);

        //同时修改会议状态
        long thesisId = thesisParam.getThesisId();

		//同时修改专家表
		ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
		long reviewId = Long.parseLong(userIdStr);
		ReviewMajor reviewMajor = this.reviewMajorService.getById(reviewId);
		if(reviewMajor != null){
			Integer reviewCount = reviewMajor.getReviewCount();
			if (reviewCount == null){
				reviewCount = 1;
			}else {
				reviewCount += 1;
			}
			reviewMajorParam.setReviewCount(reviewCount);
		}
		reviewMajorParam.setReviewId(reviewId);

        //同时修改中间表，查询条件：论文ID、专家ID、评审顺序（1）
        ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
        middleParam.setThesisId(thesisId);
        middleParam.setUserId(Long.parseLong(userIdStr));
        middleParam.setReviewSort(2);
        LayuiPageInfo midRes = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midList = midRes.getData();
        ThesisReviewMiddleResult middleResult = midList.get(0);
        middleParam.setMiddleId(middleResult.getMiddleId());
        middleParam.setScore(thesisParam.getScore());
        middleParam.setGreat(thesisParam.getIsgreat());

        //是否优秀
        ThesisReviewMiddleParam middleParam2 = new ThesisReviewMiddleParam();
        middleParam2.setThesisId(thesisId);
        middleParam2.setGreat(1);
        LayuiPageInfo greatRes = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        int greatNum = greatRes.getData().size();
        if(greatNum >= 2){
            thesisParam.setGreat(1);
        }

        Date reviewDate = new Date();
        middleParam.setReviewTime(reviewDate);
        thesisParam.setReviewTime(reviewDate);
        this.thesisReviewMiddleService.update(middleParam);
        this.thesisService.update(thesisParam);
		this.reviewMajorService.update(reviewMajorParam);

        String templateId = "cLgN9uptYs5OAM6cSTeyHZxsRatqzhuJa4b6kTSRaA4";
        User resultUser = userService.getById(middleParam.getUserId());
        String userWechatId = resultUser.getWechatId();
        if (userWechatId != null && userWechatId != ""){
            String first = "您的论文被复评";
            String remark = "您可登录中国教育科学论坛平台查看详细信息。";
            String reviewResult = "";
            if (thesisParam.getGreat() == 0){
                reviewResult = "论文不推荐优秀";
            }
            if (thesisParam.getGreat() == 1){
                reviewResult = "论文推荐优秀";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(middleParam.getReviewTime());
            List<String> dataList = new ArrayList<>();
            dataList.add("论文");
            dataList.add(reviewResult);
            dataList.add(time);
            CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
        }

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
                        belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + "";
                    }
                }
            }
        }
        map.put("belongDomainStr",belongDomainStr);

        Date date = new Date(Long.parseLong(map.get("applyTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("applyTime",dateString);


        //初评
        ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
        long thesisId = Long.parseLong(map.get("thesisId").toString());
        LoginUser user = LoginContextHolder.getContext().getUser();
        Thesis thesis = this.thesisService.getById(thesisId);
        middleParam.setThesisId(thesisId);
        middleParam.setUserId(user.getId());
        middleParam.setReviewSort(1);
        LayuiPageInfo midRes = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midList = midRes.getData();
        if(midList.size() != 0){
            ThesisReviewMiddleResult middleResult = midList.get(0);
            Integer firstScore = middleResult.getScore();
            if(firstScore != null){
                map.put("firstScore",firstScore);
            }
            Integer reviewResult = thesis.getReviewResult();
            if(reviewResult != null && reviewResult == 1){
                String status = thesis.getStatus();
                if(status == null || ("").equals(status)){
                    map.put("firstReviewStr","同意参会");
                }else{
                    map.put("firstReviewStr",status);
                }
            }else if(reviewResult != null && reviewResult == 0){
                map.put("firstReviewStr","不同意参会");
            }
        }

        //复评
        middleParam.setReviewSort(2);
        LayuiPageInfo midResAgain = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midListAgain = midResAgain.getData();
        if(midListAgain.size() != 0){
            ThesisReviewMiddleResult middleResult = midListAgain.get(0);
                Integer secondScore = middleResult.getScore();
                if(secondScore != null){
                    map.put("secondScore",secondScore);
                }

                Integer great = middleResult.getGreat();
                if(great != null && great == 0){
                    map.put("secondGreat",0);
                }else if(great != null && great == 1){
                    map.put("secondGreat",1);
                }
        }

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
     * 专家评审列表
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/reviewList")
    public Object reviewList(ThesisParam thesisParam,HttpServletRequest request) {
        boolean isReview = ToolUtil.isReviewRole();
        List<Long> thesisIdList = new ArrayList<>();
        if (isReview){
            LoginUser user = LoginContextHolder.getContext().getUser();
            Long userId = user.getId();
            Long thesisId = thesisParam.getThesisId();

            ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
            middleParam.setUserId(userId);
            //评审顺序（1或2）
            String reviewSort = request.getParameter("reviewSort");
            middleParam.setReviewSort(Integer.parseInt(reviewSort));

            List<ThesisReviewMiddleResult> mids = this.thesisReviewMiddleService.findListBySpec(middleParam);
            //List<ThesisReviewMiddleResult> mids = midRes.getData();
            for (int i = 0; i < mids.size(); i++) {
                ThesisReviewMiddleResult middleResult = mids.get(i);
                thesisId = middleResult.getThesisId();
                thesisIdList.add(thesisId);
            }
        }
        if(thesisIdList.size() == 0){
        	thesisIdList.add(new Long(0));
		}
        Page<Map<String, Object>> theses = this.thesisService.findReview(thesisIdList,thesisParam);
        Page wrapped = new ThesisWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapListFirst")
    public Object wrapListFirst(ThesisParam thesisParam) {
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
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapListAgain")
    public Object wrapListAgain(ThesisParam thesisParam) {
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
        Page<Map<String, Object>> theses = this.thesisService.findPageWrapByBatch(thesisParam,2);
        Page wrapped = new ThesisWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }


    /**
     * 上传Word文件
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadWord")
    @ResponseBody
    public ResponseData thesisUploadWord(@RequestPart("file") MultipartFile file,HttpServletRequest request) {
        String message = "";
        String path = uploadFolder;
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        HashMap<String, Object> map = new HashMap<>();
        if((".doc").equalsIgnoreCase(fileType) || ".docx".equalsIgnoreCase(fileType)){
            UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
            String fileId = uploadResult.getFileId();
            map.put("fileId", fileId);
            map.put("path",uploadResult.getFileSavePath());
            map.put("status","成功");
            message = "上传成功";
            return ResponseData.success(0, message, map);
        }else{
            message = "上传失败，文件格式不匹配";
            map.put("status","格式问题");
            return ResponseData.success(0, message, map);
        }
    }

    /**
     * 上传PPT文件
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadPPT")
    @ResponseBody
    public ResponseData thesisUploadPPT(@RequestPart("file") MultipartFile file,HttpServletRequest request) {
        String message = "";
        String path = uploadFolder;
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        HashMap<String, Object> map = new HashMap<>();
        if((".ppt").equalsIgnoreCase(fileType) || ".pptx".equalsIgnoreCase(fileType)){
            UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
            String fileId = uploadResult.getFileId();
            map.put("fileId", fileId);
            map.put("path",uploadResult.getFileSavePath());
            map.put("status","成功");
            message = "上传成功";
            return ResponseData.success(0, message, map);
        }else{
            message = "上传失败，文件格式不匹配";
            map.put("status","格式问题");
            return ResponseData.success(0, message, map);
        }
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

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/detailPub")
    @ResponseBody
    public ResponseData detailPub() {
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long userId = user.getId();
        List<MeetMemberResult> list = this.meetMemberService.findListByUserId(userId);
        Long thesisId = null;
        if (list != null){
            if (list.size() == 1){
                thesisId = list.get(0).getThesisId();
            }else {
                for (int i = 0;i < list.size();i++){
                    if (list.get(i).getMeetStatus() != 5){
                        thesisId = list.get(i).getThesisId();
                    }
                }
            }
        }

        Thesis detail = this.thesisService.getById(thesisId);
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
                        belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + "";
                    }
                }
            }
        }
        map.put("belongDomainStr",belongDomainStr);

        Date date = new Date(Long.parseLong(map.get("applyTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("applyTime",dateString);


        //初评
        ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
        Thesis thesis = this.thesisService.getById(thesisId);
        middleParam.setThesisId(thesisId);
        middleParam.setUserId(user.getId());
        middleParam.setReviewSort(1);
        LayuiPageInfo midRes = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midList = midRes.getData();
        if(midList.size() != 0){
            ThesisReviewMiddleResult middleResult = midList.get(0);
            Integer firstScore = middleResult.getScore();
            if(firstScore != null){
                map.put("firstScore",firstScore);
            }
            Integer reviewResult = thesis.getReviewResult();
            if(reviewResult != null && reviewResult == 1){
                String status = thesis.getStatus();
                if(status == null || ("").equals(status)){
                    map.put("firstReviewStr","同意参会");
                }else{
                    map.put("firstReviewStr",status);
                }
            }else if(reviewResult != null && reviewResult == 0){
                map.put("firstReviewStr","不同意参会");
            }
        }

        //复评
        middleParam.setReviewSort(2);
        LayuiPageInfo midResAgain = this.thesisReviewMiddleService.findPageBySpec(middleParam);
        List<ThesisReviewMiddleResult> midListAgain = midResAgain.getData();
        if(midListAgain.size() != 0){
            ThesisReviewMiddleResult middleResult = midListAgain.get(0);
            Integer secondScore = middleResult.getScore();
            if(secondScore != null){
                map.put("secondScore",secondScore);
            }

            Integer great = middleResult.getGreat();
            if(great != null && great == 0){
                map.put("secondGreat",0);
            }else if(great != null && great == 1){
                map.put("secondGreat",1);
            }
        }
        map.put("thesisUser", user.getName());
        return ResponseData.success(map);


    }

    /**
     * 检查报名人数
     * 人数未满，会议投稿人数+1
     */
    private void checkMeetNum(Meet pubMeet){
        Long meetId = pubMeet.getMeetId();
        //投稿限制人数
        Integer thesisNum = pubMeet.getThesisNum();
        //当前投稿人数
        Integer realTheNum = pubMeet.getRealTheNum();
        if(thesisNum.equals(realTheNum)){
            //报名人数已满
            throw new ServiceException(BizExceptionEnum.FORUM_NUM_OVER);
        }else{
            MeetParam meetParam = new MeetParam();
            realTheNum +=1;
            meetParam.setMeetId(meetId);
            meetParam.setRealTheNum(realTheNum);
            this.meetService.update(meetParam);
        }
    }

    /**
     * 检查参会人数
     */
    private void checkMeetRealNum() {
        Meet pubMeet = this.meetService.getByStatus(1);
        Long meetId = pubMeet.getMeetId();
        //参会限制人数
        Integer peoNum = pubMeet.getPeopleNum();
        //当前参会人数
        Integer realPeoNum = pubMeet.getRealPeoNum();
        if(peoNum.equals(realPeoNum)){
            //参会人数已满
            throw new ServiceException(BizExceptionEnum.REAL_NUM_OVER);
        }else{
            MeetParam meetParam = new MeetParam();
            realPeoNum +=1;
            meetParam.setMeetId(meetId);
            meetParam.setRealPeoNum(realPeoNum);
            this.meetService.update(meetParam);
        }
    }
}


