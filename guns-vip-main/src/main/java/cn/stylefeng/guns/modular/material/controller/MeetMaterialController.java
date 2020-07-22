package cn.stylefeng.guns.modular.material.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.material.entity.MeetMaterial;
import cn.stylefeng.guns.modular.material.model.params.MeetMaterialParam;
import cn.stylefeng.guns.modular.material.service.MeetMaterialService;
import cn.stylefeng.guns.sys.modular.system.model.UploadResult;
import cn.stylefeng.guns.sys.modular.system.service.FileInfoService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

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
    private FileInfoService fileInfoService;

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
        boolean status = file.delete();
        if(status){
            this.meetMaterialService.delete(meetMaterialParam);
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
        return this.meetMaterialService.findPageBySpec(meetMaterialParam);
    }

    /**
     * 上传文件
     * @author wucy
     * @Date 20209-07-23 10:48:29
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

        MeetMaterialParam meetMaterialParam = new MeetMaterialParam();
        meetMaterialParam.setMaterialId(ToolUtil.getNum19());
        meetMaterialParam.setMatName(uploadResult.getOriginalFilename());
        meetMaterialParam.setMatPath(uploadResult.getFileSavePath());
        this.meetMaterialService.add(meetMaterialParam);
        return ResponseData.success(0, "上传成功", map);
    }

}


