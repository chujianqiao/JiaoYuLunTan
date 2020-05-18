package cn.stylefeng.guns.expert.controller;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.expert.wrapper.ReviewMajorWrapper;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.sys.modular.system.warpper.UserWrapper;
import cn.stylefeng.guns.util.ExcelTool;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 评审专家表控制器
 *
 * @author wucy
 * @Date 2020-05-11 14:56:49
 */
@Controller
@RequestMapping("/reviewMajor")
public class ReviewMajorController extends BaseController {

    private String PREFIX = "/expert/reviewMajor";

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private UserService userService;

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
        return PREFIX + "/reviewMajor_add.html";
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
    public ResponseData addItem(ReviewMajorParam reviewMajorParam) {
        this.reviewMajorService.add(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 编辑
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ReviewMajorParam reviewMajorParam) {
        this.reviewMajorService.update(reviewMajorParam);
        return ResponseData.success();
    }

    /**
     * 重新申请
     * @author wucy
     * @Date 2020-05-11
     */
    @RequestMapping("/reApply")
    @ResponseBody
    public ResponseData checkApplyStatus(ReviewMajorParam reviewMajorParam) {
        reviewMajorParam.setCheckStatus(1);
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
    public ResponseData cancelApply(ReviewMajorParam reviewMajorParam) {
        reviewMajorParam.setCheckStatus(0);
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
    public ResponseData agreeApply(ReviewMajorParam reviewMajorParam) {
        reviewMajorParam.setCheckStatus(2);
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
    public ResponseData disAgreeApply(ReviewMajorParam reviewMajorParam) {
        reviewMajorParam.setCheckStatus(3);
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
    public ResponseData delete(ReviewMajorParam reviewMajorParam) {
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
        return ResponseData.success(detail);
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
        //按照用户姓名查询
        String paramIds = null;
        if(reviewName != null && !reviewName.equals("")){
            Page<Map<String, Object>> users = userService.selectUsers(null, reviewName, null, null, null);
            List<Map<String,Object>> usersRecords = users.getRecords();

            StringBuilder userIds = new StringBuilder();
            for(int i = 0;i < usersRecords.size();i++){
                String userid = usersRecords.get(i).get("userId").toString();
                userIds.append(userid);
                if (i != usersRecords.size() - 1){
                    userIds.append(",");
                }
            }
            paramIds = userIds.toString();
        }
        Page<Map<String, Object>> majors = this.reviewMajorService.findPageWrap(reviewMajorParam ,paramIds);
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
                    user.setUserId(userId);
                    user.setName(dataCols.get(0));
                    user.setAccount(dataCols.get(0));
                    user.setPassword("111111");

                    //新增的专家
                    ReviewMajorParam reviewMajorParam = new ReviewMajorParam();
                    reviewMajorParam.setReviewId(userId);
                    reviewMajorParam.setDirect(dataCols.get(3));


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

}


