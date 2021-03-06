package cn.stylefeng.guns.modular.material.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.material.entity.MeetMaterial;
import cn.stylefeng.guns.modular.material.model.params.MeetMaterialParam;
import cn.stylefeng.guns.modular.material.model.result.MeetMaterialResult;
import cn.stylefeng.guns.modular.material.service.MeetMaterialService;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议材料表控制器
 * @author wucy
 * @Date 2020-07-22 14:16:13
 */
@Controller
@RequestMapping("/meetMaterial")
public class MeetMaterialController extends BaseController {

    private String PREFIX = "/meetMaterial";

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private MeetMaterialService meetMaterialService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private FileInfoService fileInfoService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/meetMaterial.html";
    }

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("toFileDownload")
    public String toFileDownload(Long meetId, Model model) {
        model.addAttribute("meetId",meetId);
        return PREFIX + "/meetFiles.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/meetMaterial_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/meetMaterial_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(MeetMaterialParam meetMaterialParam) {
        this.meetMaterialService.add(meetMaterialParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(MeetMaterialParam meetMaterialParam) {
        this.meetMaterialService.update(meetMaterialParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(MeetMaterialParam meetMaterialParam) {
        MeetMaterial meetMaterial = this.meetMaterialService.getById(meetMaterialParam.getMaterialId());
        String filePath = meetMaterial.getMatPath();
        File file = new File(filePath);
        //删除文件
        boolean status = file.delete();
        if(status){
            //删除数据库数据
            this.meetMaterialService.delete(meetMaterialParam);
            //重新压缩
            toZipAll();
            return ResponseData.success();
        }else {
            return ResponseData.error("删除失败");
        }

    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-07-22
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(MeetMaterialParam meetMaterialParam) {
        MeetMaterial detail = this.meetMaterialService.getById(meetMaterialParam.getMaterialId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-07-22
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(MeetMaterialParam meetMaterialParam) {
        Long meetId = meetMaterialParam.getMeetId();
        if (meetId == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                meetMaterialParam.setMeetId(meet.getMeetId());
            }
        } else if (meetId == 0) {
            meetMaterialParam.setMeetId(null);
        }
        return this.meetMaterialService.findPageBySpec(meetMaterialParam);
    }

    /**
     * 个人中心下载列表
     * @author CHU
     * @Date 2020-07-22
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(MeetMaterialParam meetMaterialParam) {
        Long meetId = meetMaterialParam.getMeetId();
        Meet meet = new Meet();
        if (meetId != null){
            meetMaterialParam.setMeetId(meetId);
            meet = meetService.getById(meetId);
        }else {
            meet = meetService.getByStatus(1);
            if (meet != null){
                meetId = meet.getMeetId();
                meetMaterialParam.setMeetId(meet.getMeetId());
            }
        }


        Page<Map<String, Object>> forum = this.meetMaterialService.findPageWrap(meetMaterialParam);

        List<User> userList = this.userService.getByCanDownloadFile();
        for (int i = 0;i < userList.size();i++){
            MeetMemberParam meetMemberParam = new MeetMemberParam();
            meetMemberParam.setMeetId(meetId);
            meetMemberParam.setUserId(userList.get(i).getUserId());
            List<MeetMemberResult> meetMemberList = meetMemberService.customList(meetMemberParam);
            if (userList.get(i).getCanDownloadPpt() == 1){
                String pptName = userList.get(i).getPptName();
                String pptPath = userList.get(i).getPptPath();
                Map map = new HashMap();
                map.put("matPath",pptPath);
                map.put("matName",pptName);
                if (meetMemberList.size() > 0){
                    if (pptName != null && pptName != "" && pptPath != null && pptPath != "" ){
                        if (forum.getRecords().size() == 0){
                            List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
                            list.add(map);
                            forum.setRecords(list);
                        }else {
                            forum.getRecords().add(map);
                        }
                    }
                }

            }
            if (userList.get(i).getCanDownloadWord() == 1){
                String wordName = userList.get(i).getWordName();
                String wordPath = userList.get(i).getWordPath();
                Map map1 = new HashMap();
                map1.put("matPath",wordPath);
                map1.put("matName",wordName);
                if (meetMemberList.size() > 0){
                    if (wordName != null && wordName != "" && wordPath != null && wordPath != "" ){
                        if (forum.getRecords().size() == 0){
                            List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
                            list.add(map1);
                            forum.setRecords(list);
                        }else {
                            forum.getRecords().add(map1);
                        }
                    }
                }

            }
        }

        Map map2 = new HashMap();
        if (meet != null){
            map2.put("matPath","1");
            map2.put("matName","会议手册.doc");
            map2.put("meetId",meet.getMeetId());
        }

        if (forum.getRecords().size() == 0){
            List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
            list.add(map2);
            forum.setRecords(list);
        }else {
            forum.getRecords().add(map2);
        }


        return LayuiPageFactory.createPageInfo(forum);
    }

    /**
     * 上传文件
     * @author wucy
     * @Date 20209-07-23 10:48:29
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData upload(@RequestPart("file") MultipartFile file) {
        //检查是否有重复文件
        String originName = file.getOriginalFilename();
        checkRepeatFile(originName);

        String path = uploadFolder + "material" + File.separator ;
        UploadResult uploadResult = this.fileInfoService.uploadFile(file, path);
        String fileId = uploadResult.getFileId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        //map.put("path",uploadResult.getFileSavePath());
        map.put("path",uploadResult.getFinalName());

        String fileSuffix = cn.stylefeng.roses.core.util.ToolUtil.getFileSuffix(file.getOriginalFilename());
        String finalName = uploadResult.getOriginalFilename().replace("." + fileSuffix,System.currentTimeMillis() + "." + fileSuffix);

        MeetMaterialParam meetMaterialParam = new MeetMaterialParam();
        Long materialId = ToolUtil.getNum19();
        while (materialId == 0){
            materialId = ToolUtil.getNum19();
        }
        meetMaterialParam.setMaterialId(materialId);
        meetMaterialParam.setMatName(finalName);
        //meetMaterialParam.setMatPath(uploadResult.getFileSavePath());
        meetMaterialParam.setMatPath(uploadResult.getFinalName());

        Meet pubMeet = this.meetService.getByStatus(1);
        meetMaterialParam.setMeetId(pubMeet.getMeetId());
        this.meetMaterialService.add(meetMaterialParam);
        //上传完成后添加压缩文件
        toZipAll();
        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 下载单个模板文件
     * @author wucy
     */
    @RequestMapping(path = "/downloadOne")
    public void download(HttpServletResponse httpServletResponse, MeetMaterialParam meetMaterialParam, HttpServletRequest request) {
        //long materialId = meetMaterialParam.getMaterialId();
        //MeetMaterial meetMaterial = this.meetMaterialService.getById(materialId);
        //文件完整路径
        //String filePath = meetMaterial.getMatPath();
        String filePath = meetMaterialParam.getMatPath();
        //下载后看到的文件名
        //String fileName = meetMaterial.getMatName();
        String fileName = meetMaterialParam.getMatName();
        try {
            FileDownload.fileDownload(httpServletResponse, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载所有模板文件
     * @author wucy
     */
    @RequestMapping(path = "/downloadAll")
    public void downloadAll(HttpServletResponse httpServletResponse){
        String outPath = uploadFolder + "material" + File.separator + "allMaterial.zip";
        //文件完整路径
        String filePath = outPath;
        //下载后看到的文件名
        String fileName = "所有材料.zip";
        File zipFile = new File(outPath);
        if(!zipFile.exists()){
            //如果压缩包不存在，执行压缩
            toZipAll();
        }
        try {
            FileDownload.fileDownload(httpServletResponse, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩所有材料
     */
    private void toZipAll(){
        MeetMaterialParam meetMaterialParam = new MeetMaterialParam();
        //基础路径
        String basePath = uploadFolder + "material" + File.separator;
        //获取材料表中的所有信息
        LayuiPageInfo allData = list(meetMaterialParam);
        List<MeetMaterialResult> dataList = allData.getData();
        List<File> fileList = new ArrayList<>();
        for (int i = 0;i < dataList.size();i++){
            MeetMaterialResult meetMaterialResult = dataList.get(i);
            File orginFile = new File(meetMaterialResult.getMatPath());
            String copyPath = basePath + meetMaterialResult.getMatName();
            File newFile = new File(copyPath);
            try {
                Files.copy(orginFile.toPath(),newFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileList.add(newFile);
        }

        String outPath = basePath + "allMaterial.zip";
        File parPath = new File(outPath).getParentFile();
        if(!parPath.exists()){
            parPath.mkdirs();
        }
        FileOutputStream outZip= null;
        try {
            outZip = new FileOutputStream(new File(outPath));
        } catch (Exception e) {
            log.info("压缩文件出错");
        }
        ToolUtil.toZip(fileList,outZip);
        //压缩完成后删除临时文件
        for (int i = 0; i < fileList.size(); i++) {
            File tempFile = fileList.get(i);
            tempFile.delete();
        }
    }

    /**
     * 检查重复文件
     * 如果有同名文件，删除掉
     */
    private void checkRepeatFile(String originName){
        MeetMaterialParam meetMaterialParam = new MeetMaterialParam();
        meetMaterialParam.setMatName(originName);
        List<MeetMaterialResult> list = this.meetMaterialService.findListBySpec(meetMaterialParam);
        if(list.size() != 0){
            MeetMaterialResult meetMaterialResult = list.get(0);
            long materialId = meetMaterialResult.getMaterialId();
            meetMaterialParam.setMaterialId(materialId);
            delete(meetMaterialParam);
        }
    }

}


