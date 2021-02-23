package cn.stylefeng.guns.modular.greatResult.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.ResultDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.greatResult.entity.GreatResult;
import cn.stylefeng.guns.modular.greatResult.model.params.GreatResultParam;
import cn.stylefeng.guns.modular.greatResult.service.GreatResultService;
import cn.stylefeng.guns.modular.greatResult.wrapper.GreatResultWrapper;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import cn.stylefeng.guns.modular.greatReviewMiddle.service.GreatReviewMiddleService;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
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

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 优秀成果表控制器
 * @author CHUJIANQIAO
 * @Date 2020-05-19 14:14:51
 */
@Controller
@RequestMapping("/greatResult")
public class GreatResultController extends BaseController {

    private String PREFIX = "/greatResult";

    @Autowired
    private UserService userService;

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Autowired
    private GreatResultService greatResultService;

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private GreatReviewMiddleService greatReviewMiddleService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("")
    public String index(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","greatResult");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        if (ToolUtil.isAdminRole()){
            return PREFIX + "/greatResult.html";
        }else {
            return PREFIX + "/greatResult_person.html";
        }
    }

    /**
     * 新增页面
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/add")
    public String add(Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl","greatResult");
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
                return "/unitResult.html";
            }else {
                return  "/meet_status.html";
            }
        } else {
            if (meetTimeStatusStr == "报名中" || meetTimeStatusStr == "报名结束"){
                return "/result.html";
            }else {
                return  "/meet_status.html";
            }
        }
    }

    /**
     * 审批页面
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approve")
    public String approve(Integer applyType) {
        //if (applyType == 1){
            return PREFIX + "/greatResult_approve.html";
        //}else {
        //    return PREFIX + "/greatResult_approveUnit.html";
        //}
    }

    /**
     * 详情页面
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin(Integer applyType, Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl", "greatResult");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        if (ToolUtil.isAdminRole()) {
            //if (applyType == 1) {
                return PREFIX + "/greatResult_detail.html";
            //} else {
            //    return PREFIX + "/greatResult_detailUnit.html";
            //}
        }else if(ToolUtil.isReviewRole()){
            if (applyType == 9){
                return PREFIX + "/greatResult_detail_person.html";
            }
            return PREFIX + "/greatResult_detail_review.html";
        }else {
            return PREFIX + "/greatResult_detail_person.html";
        }
    }

    /**
     * 详情页面
     * @author wucy
     * @Date 2020-08-28
     */
    @RequestMapping("/reviewDetail")
    public String reviewDetail() {
        boolean isReview = ToolUtil.isReviewRole();
        if(isReview){
            return PREFIX + "/greatResult_detail_review.html";
        }else {
            return "没有权限";
        }
    }

    /**
     * 编辑页面
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/edit")
    public String edit(Integer applyType,@RequestParam Long resultId) {
        GreatResult greatResult = greatResultService.getById(resultId);
        LogObjectHolder.me().set(greatResult);
        //if (applyType == 1){
            return PREFIX + "/greatResult_edit.html";
        //}else {
        //    return PREFIX + "/greatResult_editUnit.html";
        //}
    }

    /**
     * 评审页面
     * @author wucy
     * @Date 2020-08-27
     */
    @RequestMapping("/reviewPage")
    public String reviewPage(Integer applyType,@RequestParam Long resultId,Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        model.addAttribute("menuUrl", "thesis");
        boolean isReview = ToolUtil.isReviewRole();
        if(isReview){
            model.addAttribute("isReview", "yes");
            return PREFIX + "/greatResult_edit_review.html";
        } else {
            model.addAttribute("isReview", "no");
            return "无权限查看";
        }
    }

    /**
     * 分配评审人
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assign")
    public String assign() {
        return PREFIX + "/assign_great.html";
    }

    /**
     * 分配评审专家接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assignItem")
    @ResponseBody
    public ResponseData assignItem(GreatResultParam greatResultParam,String reviewUser, String resultIds) {
        String majors = reviewUser;
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

            String[] resultId = resultIds.split(";");
            for (int j = 0;j < resultId.length;j++){
                GreatReviewMiddleParam param = new GreatReviewMiddleParam();
                param.setResultId(Long.parseLong(resultId[j]));
                param.setUserId(userId);
                param.setReviewResult(2);
                //param.setScore(0);
                this.greatReviewMiddleService.add(param);
            }

        }
        return ResponseData.success();
    }

    /**
     * 新增接口
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "新增优秀论著申报信息", key = "resultName", dict = ResultDict.class)
    @ResponseBody
    public ResponseData addItem(GreatResultParam greatResultParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        greatResultParam.setApplyId(userId);
        greatResultParam.setCheckStatus(1);
        greatResultParam.setApplyTime(new Date());
        Meet meet = meetService.getByStatus(1);
        if (meet != null){
            greatResultParam.setMeetId(meet.getMeetId());
        }
        this.greatResultService.add(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/editItem")
    @BussinessLog(value = "修改优秀论著申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData editItem(GreatResultParam greatResultParam) {
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 评审接口
     * @author wucy
     * @Date 2020-08-28
     */
    @RequestMapping("/reviewItem")
    @BussinessLog(value = "评审优秀论著申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData reviewItem(GreatResultParam greatResultParam,GreatReviewMiddleParam middleParam) {
        Long resultId = greatResultParam.getResultId();
        GreatResult greatResult = greatResultService.getById(resultId);
        middleParam.setResultId(resultId);
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long userId = user.getId();
        middleParam.setUserId(userId);

        List<GreatReviewMiddleResult> middleResults = this.greatReviewMiddleService.findListBySpec(middleParam);
        GreatReviewMiddleResult result = middleResults.get(0);
        Long middleId = result.getMiddleId();
        middleParam.setMiddleId(middleId);
        middleParam.setReviewTime(new Date());

        this.greatReviewMiddleService.update(middleParam);

        GreatResultParam param = new GreatResultParam();
        param.setResultId(greatResultParam.getResultId());
        if (middleParam.getReviewResult() == 0){
            param.setCheckStatus(3);
        }else if (middleParam.getReviewResult() == 1){
            param.setCheckStatus(2);
        }
        this.greatResultService.update(param);

        String templateId = "cLgN9uptYs5OAM6cSTeyHZxsRatqzhuJa4b6kTSRaA4";
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        User resultUser = userService.getById(greatResult.getApplyId());
        String userWechatId = resultUser.getWechatId();
        if (userWechatId != null && userWechatId != ""){
            String first = "您的教改实验申报已审核";
            String remark = "您可登录中国教育科学论坛平台查看详细信息。";
            String reviewResult = "";
            if (middleParam.getReviewResult() == 0){
                reviewResult = "不推荐参会";
            }
            if (middleParam.getReviewResult() == 1){
                reviewResult = "推荐参会";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(middleParam.getReviewTime());
            List<String> dataList = new ArrayList<>();
            dataList.add("教改实验申报");
            dataList.add(reviewResult);
            dataList.add(time);
            CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
        }

//        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 审批通过接口
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approveForum")
    @BussinessLog(value = "优秀论著申报审批通过", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData approveForum(GreatResultParam greatResultParam) {
        greatResultParam.setCheckStatus(2);
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 审批驳回接口
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/rejectForum")
    @BussinessLog(value = "优秀论著申报审批驳回", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData rejectForum(GreatResultParam greatResultParam) {
        greatResultParam.setCheckStatus(3);
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 取消接口
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/cancel")
    @BussinessLog(value = "优秀论著申报取消", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData cancel(GreatResultParam greatResultParam) {
        greatResultParam.setCheckStatus(0);
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 申请接口
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/editNew")
    @BussinessLog(value = "优秀论著申报申请", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData editNew(GreatResultParam greatResultParam) {
        greatResultParam.setCheckStatus(1);
        this.greatResultService.update(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除优秀论著申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData delete(GreatResultParam greatResultParam) {
        this.greatResultService.delete(greatResultParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(GreatResultParam greatResultParam) {
        GreatResult detail = this.greatResultService.getById(greatResultParam.getResultId());
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);

        GreatReviewMiddleParam middleParam = new GreatReviewMiddleParam();
        middleParam.setResultId(greatResultParam.getResultId());
        List<GreatReviewMiddleResult> records = this.greatReviewMiddleService.findListBySpec(middleParam);
        if(records.size() != 0){
            GreatReviewMiddleResult result = records.get(0);
            Integer score = result.getScore();
            Integer reviewResult = result.getReviewResult();
            String desc = result.getDescription();
            if(reviewResult != null){
                map.put("reviewResult",reviewResult);
                map.put("score",score);
                map.put("description",desc);
            }
        }
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(GreatResultParam greatResultParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        greatResultParam.setApplyId(userId);
        return this.greatResultService.findPageBySpec(greatResultParam);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(GreatResultParam greatResultParam) {
        /*boolean isReview = ToolUtil.isReviewRole();
        List<Long> greatIdList = new ArrayList<>();
        String listStatus = "";
        if(isReview){
            greatIdList = getGreatIdList();
            if(greatIdList.size() != 0){
                listStatus = "有数据";
            }
        }*/
        Long userId = LoginContextHolder.getContext().getUserId();
        boolean isAdmin = ToolUtil.isAdminRole();
        if (!isAdmin){
            greatResultParam.setApplyId(userId);
        }
        if (greatResultParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                greatResultParam.setMeetId(meet.getMeetId());
            }
        } else if (greatResultParam.getMeetId() == 0) {
            greatResultParam.setMeetId(null);
        }
        Page<Map<String, Object>> theses = this.greatResultService.findPageWrap(greatResultParam);
        Page wrapped = new GreatResultWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapListReview")
    public Object wrapListReview(GreatResultParam educationResultParam) {
        boolean isReview = ToolUtil.isReviewRole();
        List<Long> greatIdList = new ArrayList<>();
        String listStatus = "";
        if(isReview){
            greatIdList = getGreatIdList();
            if(greatIdList.size() != 0){
                listStatus = "有数据";
            }
        }
        if (educationResultParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                educationResultParam.setMeetId(meet.getMeetId());
            }
        } else if (educationResultParam.getMeetId() == 0) {
            educationResultParam.setMeetId(null);
        }
        if (listStatus == "有数据"){
            Page<Map<String, Object>> theses = this.greatResultService.findPageWrap(educationResultParam,greatIdList,listStatus);
            Page wrapped = new GreatResultWrapper(theses).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 上传文件
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

    /**
     * 获取成果ID
     * @return
     */
    private List<Long> getGreatIdList(){
        List<Long> greatIdList = new ArrayList<>();
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long userId = user.getId();
        GreatReviewMiddleParam middleParam = new GreatReviewMiddleParam();
        middleParam.setUserId(userId);
        List<GreatReviewMiddleResult> middles = this.greatReviewMiddleService.findListBySpec(middleParam);
        for (int i = 0; i < middles.size(); i++) {
            GreatReviewMiddleResult result = middles.get(i);
            Long resultId = result.getResultId();
            greatIdList.add(resultId);
        }
        return greatIdList;
    }
}


