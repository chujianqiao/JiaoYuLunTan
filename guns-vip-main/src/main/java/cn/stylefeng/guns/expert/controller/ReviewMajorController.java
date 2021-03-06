package cn.stylefeng.guns.expert.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.CollectTopicDict;
import cn.stylefeng.guns.core.constant.dictmap.ReviewMajorDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.expert.wrapper.ReviewMajorWrapper;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.dictmap.UserDict;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.sys.modular.system.warpper.UserWrapper;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.util.ExcelTool;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 评审专家表控制器
 *
 * @author wucy
 * @Date 2020-05-11 14:56:49
 */
@Controller
@RequestMapping("/reviewMajor")
@Slf4j
public class ReviewMajorController extends BaseController {

    private String PREFIX = "/expert/reviewMajor";

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private ThesisDomainService thesisDomainService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private ExcelTool excelTool;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("")
    public String index() {
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/reviewMajor_admin.html";
        }else{
            return PREFIX + "/reviewMajor.html";
        }

    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/add")
    public String add() {
        LoginUser user = LoginContextHolder.getContext().getUser();
        List roles = user.getRoleList();
        long unit = 3;
        if (roles.contains(unit)){
            return "/unitReport.html";
        } else {
            return "/majorReport.html";
        }
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/reviewMajor_edit.html";
    }

    /**
     * 仅查看
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/disable")
    public String disable() {
        return PREFIX + "/reviewMajor_edit_disable.html";
    }

    /**
     * 审批
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/approve")
    public String approve() {
        return PREFIX + "/reviewMajor_edit_admin.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
//    @BussinessLog(value = "提交申请", key = "direct", dict = ReviewMajorDict.class)
    public ResponseData addItem(ReviewMajorParam reviewMajorParam) {
        Long reviewId = reviewMajorParam.getReviewId();
        ReviewMajor reviewMajor = this.reviewMajorService.getById(reviewId);
        if(reviewMajor == null){
//            LoginUser user = LoginContextHolder.getContext().getUser();
            reviewMajorParam.setReviewId(reviewId);
            reviewMajorParam.setApplyTime(new Date());
            reviewMajorParam.setApplyFrom("非邀请");
            reviewMajorParam.setCheckStatus(ManagerStatus.OK.getCode());
            reviewMajorParam.setThesisCount(0);
            reviewMajorParam.setReviewCount(0);
            reviewMajorParam.setRefuseCount(0);
            this.reviewMajorService.add(reviewMajorParam);
        }
        return ResponseData.success();
    }

    @RequestMapping("/addByRole")
    @ResponseBody
    public ResponseData addByRole(String userId) {
        String userIdArr[] = userId.split(";");
        for (int i = 0;i < userIdArr.length;i++){
            ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
            ReviewMajor reviewMajor = this.reviewMajorService.getById(Long.parseLong(userIdArr[i]));
            if(reviewMajor == null){
                reviewMajorParam.setReviewId(Long.parseLong(userIdArr[i]));
                reviewMajorParam.setApplyTime(new Date());
                reviewMajorParam.setApplyFrom("非邀请");
                reviewMajorParam.setCheckStatus(ManagerStatus.OK.getCode());
                reviewMajorParam.setThesisCount(0);
                reviewMajorParam.setReviewCount(0);
                reviewMajorParam.setRefuseCount(0);
                this.reviewMajorService.add(reviewMajorParam);
            }
        }
        return ResponseData.success();
    }

    /**
     * 编辑
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    @BussinessLog(value = "编辑申请", key = "direct", dict = ReviewMajorDict.class)
    public ResponseData editItem(ReviewMajorParam reviewMajorParam) {
        reviewMajorParam.setCheckStatus("ENABLE");
        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 管理员编辑
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/adminEditItem")
    @ResponseBody
    @BussinessLog(value = "管理员修改", key = "direct", dict = ReviewMajorDict.class)
    public ResponseData adminEditItem(ReviewMajorParam reviewMajorParam,@Valid UserDto user) {
        Long userId = reviewMajorParam.getReviewId();
        user.setUserId(userId);
        this.reviewMajorService.update(reviewMajorParam);
        this.userService.editUser(user);
        return ResponseData.success();
    }

    /**
     * 重新申请
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/reApply")
    @ResponseBody
    @BussinessLog(value = "重新申请", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData checkApplyStatus(ReviewMajorParam reviewMajorParam) {
        //reviewMajorParam.setCheckStatus(1);
        reviewMajorParam.setApplyTime(new Date());
        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 取消申请
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/cancelApply")
    @ResponseBody
    @BussinessLog(value = "取消申请", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData cancelApply(ReviewMajorParam reviewMajorParam) {
        //reviewMajorParam.setCheckStatus(0);
        Date date = new Date();
        reviewMajorParam.setCancelTime(date);
        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 同意申请
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/agreeApply")
    @ResponseBody
    @BussinessLog(value = "同意申请", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData agreeApply(ReviewMajorParam reviewMajorParam) {
        //reviewMajorParam.setCheckStatus(2);
        reviewMajorParam.setAgreeTime(new Date());
        long reviewId = reviewMajorParam.getReviewId();

        User user = this.userService.getById(reviewId);
        String roleStr = user.getRoleId();
        if(roleStr == null || roleStr.equals("")){
            roleStr = "1234567890";
        } else {
            roleStr += ",1234567890";
        }

//        UserDto userDto = new UserDto();
//        userDto.setUserId(reviewId);
//        userDto.setRoleId(roleStr);
        user.setRoleId(roleStr);
        this.userService.updateById(user);


        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 驳回申请
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/disAgreeApply")
    @ResponseBody
    @BussinessLog(value = "驳回申请", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData disAgreeApply(ReviewMajorParam reviewMajorParam) {
        //reviewMajorParam.setCheckStatus(3);
        reviewMajorParam.setRefuseTime(new Date());
        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    @BussinessLog(value = "删除", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData delete(ReviewMajorParam reviewMajorParam) {
        this.reviewMajorService.delete(reviewMajorParam);
        return ResponseData.success();
    }
    @RequestMapping("/deleteByRole")
    @ResponseBody
    @BussinessLog(value = "删除", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData deleteByRole(String userId) {
        String userIdArr[] = userId.split(";");
        ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
        for (int i = 0;i < userIdArr.length;i++){
            reviewMajorParam.setReviewId(Long.parseLong(userIdArr[i]));
            this.reviewMajorService.delete(reviewMajorParam);
        }

        return ResponseData.success();
    }

    /**
     * 管理员删除接口
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/adminDelete")
    @ResponseBody
    @BussinessLog(value = "管理员删除", key = "reviewId", dict = ReviewMajorDict.class)
    public ResponseData adminDelete(ReviewMajorParam reviewMajorParam) {
        this.reviewMajorService.delete(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ReviewMajorParam reviewMajorParam) {
        ReviewMajor detail = this.reviewMajorService.getById(reviewMajorParam.getReviewId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        String belongDomain = detail.getBelongDomain();
        if(belongDomain != null){
            String domains[] = detail.getBelongDomain().split(";");
            String belongDomainStr = "";
            for (int i = 0;i < domains.length;i++){
                Long domainObj = Long.parseLong(domains[i]);
                ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(domainObj);
                belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
            }
            //Long domainObj = Long.parseLong(detail.getBelongDomain());
            //ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(domainObj);
            map.put("domainName",belongDomainStr);
        }else{
            map.put("domainName","");
        }
        //个人信息
        Long userId = detail.getReviewId();
        User user = this.userService.getById(userId);
        String reviewName = user.getName();
        map.put("reviewName",reviewName);
        String unitName = user.getWorkUnit();
        if(unitName != null && unitName != ""){
            map.put("unitName",unitName);
        }
        String userPost = user.getPost();
        if(userPost == null || userPost.equals("")){
            String title = user.getTitle();
            map.put("userPost",title);
        }else {
            map.put("userPost",userPost);
        }
        String direct = user.getDirection();
        if(direct != null && !direct.equals("")){
            map.put("direct",direct);
        }

        Date date = new Date(Long.parseLong(map.get("applyTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("applyTime",dateString);
        /*if (domainObj.equals("") || domainObj == null){
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
        }*/
        //detail.setBelongDomain(thesisDomainResult.getDomainName());
        return ResponseData.success(map);
    }

    /**
     * 管理员查看详情接口
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/adminDetail")
    @ResponseBody
    public ResponseData adminDetail(ReviewMajorParam reviewMajorParam) {
        ReviewMajor detail = this.reviewMajorService.getById(reviewMajorParam.getReviewId());
        String belongDomains = "";
        String belongDomainStr = "";
        if (detail.getBelongDomain() != null && detail.getBelongDomain() != ""){
            belongDomains = detail.getBelongDomain();
            String belongDomain[] = belongDomains.split(";");
            for (int i = 0;i < belongDomain.length;i++){
                Long domainObj = Long.parseLong(belongDomain[i]);
                ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(domainObj);
                belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
            }
        }
        detail.setBelongDomain(belongDomainStr);

        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        //个人信息
        Long userId = detail.getReviewId();
        User user = this.userService.getById(userId);
        String name = user.getName();
        map.put("name",name);
        String workUnit = user.getWorkUnit();
        if(workUnit != null && workUnit != ""){
            map.put("workUnit",workUnit);
        }
        String post = user.getPost();
        if(post == null || post.equals("")){
            String title = user.getTitle();
            map.put("post",title);
        }else {
            map.put("post",post);
        }
        String direction = user.getDirection();
        if(direction != null && !direction.equals("")){
            map.put("direction",direction);
        }

        Date date = new Date(Long.parseLong(map.get("applyTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("applyTime",dateString);
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-11
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ReviewMajorParam reviewMajorParam) {
        return this.reviewMajorService.findPageBySpec(reviewMajorParam);
    }


    /**
     * 查询列表（拼接需要的字段）
     * @author wucy
     * @Date 2020-05-11
     */
    @ResponseBody
    @RequestMapping("/wraplist")
    public Object wrapList(ReviewMajorParam reviewMajorParam ,
                           @RequestParam(required = false) String reviewName) {
        List<Long> userIdList = userService.getUserIdByName(reviewName);
        Page<Map<String, Object>> majors = this.reviewMajorService.findPageWrap(reviewMajorParam ,userIdList);
        Page wrapped = new ReviewMajorWrapper(majors).wrap();

        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 下载模板文件
     */
    @RequestMapping(path = "/download")
    public void download(HttpServletResponse httpServletResponse) {
        String proPath = System.getProperty("user.dir");
        String parentPath = proPath + File.separator + "template";
        String tempName = "评审专家导入模板.xls";
        String allPath = parentPath + File.separator + tempName;
        try {
            FileDownload.fileDownload(httpServletResponse, allPath, tempName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传excel填报
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public ResponseData uploadExcel(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String name = file.getOriginalFilename();
        request.getSession().setAttribute("upFile", name);
        String fileSavePath = ConstantsContext.getFileUploadPath();
        try {
            file.transferTo(new File(fileSavePath + name));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", IdWorker.getIdStr());
        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 获取上传成功的数据
     */
    @RequestMapping("/getUploadData")
//    @ResponseBody
    public void getUploadData(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("upFile");
        String fileSavePath = ConstantsContext.getFileUploadPath();
        if (name != null) {
            File excelFile = new File(fileSavePath + File.separator + name);
            List<List> dataList = excelTool.readExcel(excelFile);
            if(dataList != null && dataList.size() != 0){
                List<String> cols = dataList.get(0);
                for (int i = 1;i < dataList.size();i++){
                    List<String> dataCols = dataList.get(i);
                    //新增的用户
                    UserDto user = new UserDto();
                    long userId = ToolUtil.getNum19();
                    while (userId == 0){
                        userId = ToolUtil.getNum19();
                    }
                    user.setUserId(userId);
                    user.setName(dataCols.get(0));
                    user.setAccount(dataCols.get(0));
                    user.setPassword("111111");

                    //新增的专家
                    ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
                    reviewMajorParam.setReviewId(userId);
                    reviewMajorParam.setDirect(dataCols.get(3));
                    reviewMajorParam.setApplyFrom("邀请");

                    this.userService.addUser(user);
                    this.reviewMajorService.add(reviewMajorParam);

                }
            }
            //解析完成后删除文件
            boolean result = excelFile.delete();
            int tryCount = 0;
            while (!result && tryCount++ < 10) {
                System.gc();    //回收资源
                result = excelFile.delete();
            }
        }
//        return ResponseData.success(0, "导入成功", "");
    }

    @RequestMapping("/addGuest")
    @ResponseBody
    public ResponseData addGuest(String userId) {
        String userIdArr[] = userId.split(";");
        ResponseData responseData = new ResponseData();
        Meet meet = meetService.getByStatus(1);
        if (meet == null){
            responseData.setMessage("null");
        }else {
            MeetMemberParam meetMemberParam = new MeetMemberParam();
            meetMemberParam.setRegTime(new Date());
            meetMemberParam.setSpeak(1);
            meetMemberParam.setMeetId(meet.getMeetId());
            meetMemberParam.setThesisId(0l);
            meetMemberParam.setOwnForumid(0l);
            for (int i = 0;i < userIdArr.length;i++){
                User user = userService.getById(Long.parseLong(userIdArr[i]));
                meetMemberParam.setUserId(Long.parseLong(userIdArr[i]));
                meetMemberParam.setRoleId(user.getRoleId());
                List<MeetMemberResult> list = meetMemberService.customListIfExist(meet.getMeetId(),Long.parseLong(userIdArr[i]));
                if (list.size() > 0){
                    meetMemberParam.setMemberId(list.get(0).getMemberId());
                    meetMemberService.update(meetMemberParam);
                }else {
                    meetMemberService.add(meetMemberParam);
                }
            }
            responseData.setMessage("success");
        }

        return responseData;
    }

    @RequestMapping("/addAccount")
    @ResponseBody
    public ResponseData addAccount(@Valid UserDto user, String majorType,
                                   Integer joinType, Long ownForumid,
                                   String belongDomain, Long meetId) {
        ResponseData responseData = new ResponseData();
        /*StringBuffer id=new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char s = 0;
            int j=random.nextInt(3) % 4;
            switch (j) {
                case 0:
                    //随机生成数字
                    s = (char) (random.nextInt(57) % (57 - 48 + 1) + 48);
                    break;
                case 1:
                    //随机生成大写字母
                    s = (char) (random.nextInt(90) % (90 - 65 + 1) + 65);
                    break;
                case 2:
                    //随机生成小写字母
                    s = (char) (random.nextInt(122) % (122 - 97 + 1) + 97);
                    break;
            }
            id.append(s);
        }
        String password = id.toString();
        log.info("password---" + password);*/
        long uid = ToolUtil.getNum19();
        while (uid == 0){
            uid = ToolUtil.getNum19();
        }
        user.setUserId(uid);
        //user.setAccount(user.getPhone());
        //user.setPassword("Nies2020");


        if (joinType != null){
            user.setRoleId("5");
            MeetMemberParam meetMemberParam = new MeetMemberParam();
            meetMemberParam.setUserId(uid);
            meetMemberParam.setRegTime(new Date());
            meetMemberParam.setSpeak(1);
            meetMemberParam.setMeetId(meetId);
            meetMemberParam.setThesisId(0l);
            meetMemberParam.setRoleId("5");
            if (joinType == 0){
                meetMemberParam.setOwnForumid(0l);
            }else {
                meetMemberParam.setOwnForumid(ownForumid);
            }
            this.userService.addUser(user);
            meetMemberService.add(meetMemberParam);
        }else{
            user.setRoleId("4");
            ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
            reviewMajorParam.setReviewId(uid);
            reviewMajorParam.setApplyTime(new Date());
            reviewMajorParam.setThesisCount(0);
            reviewMajorParam.setRefuseCount(0);
            reviewMajorParam.setReviewCount(0);
            reviewMajorParam.setCheckStatus(ManagerStatus.OK.getCode());
            reviewMajorParam.setMajorType(majorType);
            reviewMajorParam.setBelongDomain(belongDomain);
            this.userService.addUser(user);
            reviewMajorService.add(reviewMajorParam);
        }

        return SUCCESS_TIP;
    }



    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结专家", key = "reviewId", dict = ReviewMajorDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData freeze(@RequestParam Long reviewId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(reviewId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.reviewMajorService.setStatus(reviewId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结专家", key = "reviewId", dict = ReviewMajorDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long reviewId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(reviewId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.reviewMajorService.setStatus(reviewId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }
}


