package cn.stylefeng.guns.modular.educationResult.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.ResultDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.service.EducationResultService;
import cn.stylefeng.guns.modular.educationResult.wrapper.EducationResultWrapper;
import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import cn.stylefeng.guns.modular.educationReviewMiddle.service.EducationReviewMiddleService;
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
import io.swagger.models.auth.In;
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
 *
 * @author CHUJIANQIAO
 * @Date 2020-05-19 14:15:37
 */
@Controller
@RequestMapping("/educationResult")
public class EducationResultController extends BaseController {

    private String PREFIX = "/educationResult";

    @Autowired
    private UserService userService;

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Autowired
    private EducationResultService educationResultService;

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private EducationReviewMiddleService educationReviewMiddleService;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 跳转到主页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("")
    public String index() {
        if (ToolUtil.isAdminRole()){
            return PREFIX + "/educationResult.html";
        }else {
            return PREFIX + "/educationResult_person.html";
        }
    }

    /**
     * 新增页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/add")
    public String add() {
        LoginUser user = LoginContextHolder.getContext().getUser();
        List roles = user.getRoleList();
        long unit = 3;
        if (roles.contains(unit)){
            return "/unitResult.html";
        } else {
            return "/result.html";
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
            return PREFIX + "/educationResult_approve.html";
        //}else {
        //    return PREFIX + "/educationResult_approveUnit.html";
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
        boolean isAdmin = ToolUtil.isAdminRole();
        boolean isReview = ToolUtil.isReviewRole();
        model.addAttribute("menuUrl", "greatResult");
        if (isReview){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        if (isAdmin){
            //if (applyType == 1){
                return PREFIX + "/educationResult_detail.html";
            //}else {
            //    return PREFIX + "/educationResult_detailUnit.html";
            //}
        }else if(isReview){
            return PREFIX + "/educationResult_detail_review.html";
        }else {
            return PREFIX + "/educationResult_detail_person.html";
        }

    }

    /**
     * 编辑页面
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/edit")
    public String edit(Integer applyType,@RequestParam Long resultId) {
        EducationResult educationResult = educationResultService.getById(resultId);
        LogObjectHolder.me().set(educationResult);
        //if (applyType == 1){
            return PREFIX + "/educationResult_edit.html";
        //}else {
        //    return PREFIX + "/educationResult_editUnit.html";
        //}
    }

    /**
     * 评审页面
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/reviewPage")
    public String reviewPage(@RequestParam Long resultId, Model model) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        model.addAttribute("userName", user.getName());
        boolean isReivew = ToolUtil.isReviewRole();
        model.addAttribute("menuUrl", "thesis");
        if (ToolUtil.isReviewRole()){
            model.addAttribute("isReview", "yes");
        }else {
            model.addAttribute("isReview", "no");
        }
        if(isReivew){
            return PREFIX + "/educationResult_edit_review.html";
        } else {
            return "没有查看权限";
        }
    }

    /**
     * 分配评审人
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assign")
    public String assign() {
        return PREFIX + "/assign_education.html";
    }

    /**
     * 分配评审专家接口
     * @author wucy
     * @Date 2020-05-21
     */
    @RequestMapping("/assignItem")
    @ResponseBody
    public ResponseData assignItem(EducationResultParam educationResultParam,String reviewUser, String resultIds) {
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
                EducationReviewMiddleParam param = new EducationReviewMiddleParam();
                param.setResultId(Long.parseLong(resultId[j]));
                param.setUserId(userId);
                //param.setScore(0);
                param.setReviewResult(2);
                this.educationReviewMiddleService.add(param);
            }

        }
        return ResponseData.success();
    }

    /**
     * 新增接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "新增教改实验申报信息", key = "resultName", dict = ResultDict.class)
    @ResponseBody
    public ResponseData addItem(EducationResultParam educationResultParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        educationResultParam.setApplyId(userId);
        educationResultParam.setCheckStatus(1);
        educationResultParam.setApplyTime(new Date());
        this.educationResultService.add(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/editItem")
    @BussinessLog(value = "修改教改实验申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData editItem(EducationResultParam educationResultParam) {
        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 评审接口
     * @author wucy
     * @Date 2020-08-27
     */
    @RequestMapping("/reviewItem")
    @BussinessLog(value = "评审教改实验申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData reviewItem(EducationResultParam educationResultParam,EducationReviewMiddleParam middleParam) {
        Long resultId = educationResultParam.getResultId();
        EducationResult educationResult = educationResultService.getById(resultId);
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long userId = user.getId();
        middleParam.setUserId(userId);
        middleParam.setResultId(resultId);

        LayuiPageInfo records = this.educationReviewMiddleService.findPageBySpec(middleParam);
        List<EducationReviewMiddleResult> results = records.getData();
        EducationReviewMiddleResult result = results.get(0);
        Long middleId = result.getMiddleId();
        middleParam.setMiddleId(middleId);
        middleParam.setReviewTime(new Date());
        this.educationReviewMiddleService.update(middleParam);

        String templateId = "cLgN9uptYs5OAM6cSTeyHZxsRatqzhuJa4b6kTSRaA4";
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        User resultUser = userService.getById(educationResult.getApplyId());
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

//        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }


    /**
     * 审批通过接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/approveForum")
    @BussinessLog(value = "教改实验申报审批通过", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData approveForum(EducationResultParam educationResultParam) {
        educationResultParam.setCheckStatus(2);
        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 审批驳回接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/rejectForum")
    @BussinessLog(value = "教改实验申报审批驳回", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData rejectForum(EducationResultParam educationResultParam) {
        educationResultParam.setCheckStatus(3);
        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 取消接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/cancel")
    @BussinessLog(value = "教改实验申报取消", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData cancel(EducationResultParam educationResultParam) {
        educationResultParam.setCheckStatus(0);
        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 申请接口
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/editNew")
    @BussinessLog(value = "教改实验申报申请", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData editNew(EducationResultParam educationResultParam) {
        educationResultParam.setCheckStatus(1);
        this.educationResultService.update(educationResultParam);
        return ResponseData.success();
    }


    /**
     * 删除接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除教改实验申报信息", key = "resultId", dict = ResultDict.class)
    @ResponseBody
    public ResponseData delete(EducationResultParam educationResultParam) {
        this.educationResultService.delete(educationResultParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(EducationResultParam educationResultParam) {
        EducationResult detail = this.educationResultService.getById(educationResultParam.getResultId());
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);

        EducationReviewMiddleParam middleParam = new EducationReviewMiddleParam();
        middleParam.setResultId(detail.getResultId());
        List<EducationReviewMiddleResult> records = this.educationReviewMiddleService.findListBySpec(middleParam);
        if(records.size() != 0){
            EducationReviewMiddleResult result = records.get(0);
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
    public LayuiPageInfo list(EducationResultParam educationResultParam) {
        Long userId = LoginContextHolder.getContext().getUserId();
        educationResultParam.setApplyId(userId);
        return this.educationResultService.findPageBySpec(educationResultParam);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(EducationResultParam educationResultParam) {
        boolean isReview = ToolUtil.isReviewRole();
        List<Long> eduIdList = new ArrayList<>();
        String listStatus = "";
        if(isReview){
            eduIdList = getEduIdList();
            if(eduIdList.size() != 0){
                listStatus = "有数据";
            }
        }
        Page<Map<String, Object>> theses = this.educationResultService.findPageWrap(educationResultParam,eduIdList,listStatus);
        Page wrapped = new EducationResultWrapper(theses).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapListReview")
    public Object wrapListReview(EducationResultParam educationResultParam) {
        boolean isReview = ToolUtil.isReviewRole();
        List<Long> eduIdList = new ArrayList<>();
        String listStatus = "";
        if(isReview){
            eduIdList = getEduIdList();
            if(eduIdList.size() != 0){
                listStatus = "有数据";
            }
        }
        if (listStatus == "有数据"){
            Page<Map<String, Object>> theses = this.educationResultService.findPageWrap(educationResultParam,eduIdList,listStatus);
            Page wrapped = new EducationResultWrapper(theses).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
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

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        map.put("path",uploadResult.getFileSavePath());

        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 获取成果ID
     * @return
     */
    private List<Long> getEduIdList(){
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long userId = user.getId();
        EducationReviewMiddleParam educationReviewMiddleParam = new EducationReviewMiddleParam();
        educationReviewMiddleParam.setUserId(userId);
        List<EducationReviewMiddleResult> middles = this.educationReviewMiddleService.findListBySpec(educationReviewMiddleParam);
        List<Long> eduIdList = new ArrayList<>();
        for (int i = 0; i < middles.size(); i++) {
            EducationReviewMiddleResult result = middles.get(i);
            Long eduId = result.getResultId();
            eduIdList.add(eduId);
        }
        return eduIdList;
    }

}


