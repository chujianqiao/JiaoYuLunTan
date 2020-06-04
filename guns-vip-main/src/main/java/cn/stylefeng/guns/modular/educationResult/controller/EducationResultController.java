package cn.stylefeng.guns.modular.educationResult.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.ResultDict;
import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.service.EducationResultService;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
    private EducationResultService educationResultService;

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
        if (applyType == 1){
            return PREFIX + "/educationResult_approve.html";
        }else {
            return PREFIX + "/educationResult_approveUnit.html";
        }
    }

    /**
     * 详情页面
     *
     * @author
     * @Date 2020-05-13
     */
    @RequestMapping("/detailAdmin")
    public String detailAdmin(Integer applyType) {
        if (applyType == 1){
            return PREFIX + "/educationResult_detail.html";
        }else {
            return PREFIX + "/educationResult_detailUnit.html";
        }
    }

    /**
     * 编辑页面
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    @RequestMapping("/edit")
    public String edit(Integer applyType,@RequestParam Long resultId) {
        EducationResult educationResult = educationResultService.getById(resultId);
        LogObjectHolder.me().set(educationResult);
        if (applyType == 1){
            return PREFIX + "/educationResult_edit.html";
        }else {
            return PREFIX + "/educationResult_editUnit.html";
        }
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
     *
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
        return ResponseData.success(detail);
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

}


