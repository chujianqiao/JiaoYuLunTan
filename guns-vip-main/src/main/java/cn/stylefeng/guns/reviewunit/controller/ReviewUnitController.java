package cn.stylefeng.guns.reviewunit.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.ReviewMajorDict;
import cn.stylefeng.guns.core.constant.dictmap.ReviewUnitDict;
import cn.stylefeng.guns.expert.wrapper.ReviewMajorWrapper;
import cn.stylefeng.guns.reviewunit.entity.ReviewUnit;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.reviewunit.service.ReviewUnitService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.ExcelTool;
import cn.stylefeng.guns.util.ToolUtil;
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
 * 理事单位表控制器
 * @author wucy
 * @Date 2020-05-14 13:58:30
 */
@Controller
@RequestMapping("/reviewUnit")
public class ReviewUnitController extends BaseController {

    private String PREFIX = "/reviewUnit";

    @Autowired
    private ReviewUnitService reviewUnitService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private ExcelTool excelTool;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("")
    public String index() {
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/reviewUnit.html";
        }else{
            return PREFIX + "/reviewUnit_own.html";
        }
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/reviewUnit_add.html";
    }

    /**
     * 查看详情页面
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/edit")
    public String edit() {
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
     * @Date 2020-05-14
     */
    @RequestMapping("/editOwn")
    public String editOwn() {
        return PREFIX + "/reviewUnit_edit_own.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ReviewUnitParam reviewUnitParam) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        reviewUnitParam.setReviewId(user.getId());
        reviewUnitParam.setCreateTime(new Date());
        this.reviewUnitService.add(reviewUnitParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ReviewUnitParam reviewUnitParam) {
        this.reviewUnitService.update(reviewUnitParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ReviewUnitParam reviewUnitParam) {
        this.reviewUnitService.delete(reviewUnitParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ReviewUnitParam reviewUnitParam) {
        ReviewUnit detail = this.reviewUnitService.getById(reviewUnitParam.getReviewId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ReviewUnitParam reviewUnitParam) {
        return this.reviewUnitService.findPageBySpec(reviewUnitParam);
    }

    /**
     * 查询列表（拼接需要的字段）
     * @author wucy
     * @Date 2020-05-11
     */
    @ResponseBody
    @RequestMapping("/wraplist")
    public Object wrapList(ReviewUnitParam reviewUnitParam,
                           @RequestParam(required = false) String reviewName) {
        //按照用户姓名查询出用户id
        String paramIds = "";
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
            if(paramIds.length() == 0){
                paramIds = "0";
            }
        }

        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){

        }else{
            LoginUser user = LoginContextHolder.getContext().getUser();
            reviewUnitParam.setReviewId(user.getId());
        }

        Page<Map<String, Object>> units = this.reviewUnitService.findPageWrap(reviewUnitParam,paramIds);
        Page wrapped = new ReviewMajorWrapper(units).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 下载模板文件
     * @author fengshuonan
     * @Date 2019-2-23 10:48:29
     */
    @RequestMapping(path = "/download")
    public void download(HttpServletResponse httpServletResponse) {
        String proPath = System.getProperty("user.dir");
        String parentPath = proPath + File.separator + "template";
        String tempName = "理事单位导入模板.xls";
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
    @BussinessLog(value = "导入理事单位", key = "reviewId", dict = ReviewUnitDict.class)
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
                    UserDto user = new UserDto();
                    user.setName(dataCols.get(0));
                    user.setAccount(dataCols.get(0));
                    user.setPassword("111111");
                    long userId = ToolUtil.getNum19();
                    user.setUserId(userId);
                    ReviewUnitParam reviewUnitParam = new ReviewUnitParam();

                    for (int j = 1;j < dataCols.size();j++){
                        setReviewUnit(reviewUnitParam,j,dataCols.get(j));
                    }
                    this.userService.addUser(user);
                    reviewUnitParam.setCreateTime(new Date());
                    reviewUnitParam.setReviewId(userId);
                    this.reviewUnitService.add(reviewUnitParam);

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


    private ReviewUnitParam setReviewUnit(ReviewUnitParam reviewUnitParam,int i,String param){
        switch (i){
            case 1 :
                reviewUnitParam.setLocation(param);
                break;
            case 2 :
                reviewUnitParam.setYear(param);
                break;
            case 3 :
                reviewUnitParam.setRepName(param);
                break;
            case 4 :
                reviewUnitParam.setPost(param);
                break;
            case 5 :
                reviewUnitParam.setEducation(param);
                break;
        }
        return reviewUnitParam;
    }

}


