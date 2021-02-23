package cn.stylefeng.guns.modular.checkIn.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.checkIn.entity.CheckIn;
import cn.stylefeng.guns.modular.checkIn.model.params.CheckInParam;
import cn.stylefeng.guns.modular.checkIn.service.CheckInService;
import cn.stylefeng.guns.modular.checkIn.wrapper.CheckInWrapper;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 报到签到表控制器
 *
 * @author CHU
 * @Date 2020-07-30 15:21:46
 */
@Controller
@RequestMapping("/checkIn")
public class CheckInController extends BaseController {

    private String PREFIX = "/checkIn";

    @Value("${weiXin.url}")
    private String systemUrl;

    @Value("${file.imagesFolder}")
    private String imagesFolder;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/checkIn.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/checkIn_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/checkIn_edit.html";
    }

    /**
     * 详情页面
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/toDetail")
    public String toDetail() {
        return PREFIX + "/checkIn_detail.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CheckInParam checkInParam) {
        this.checkInService.add(checkInParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(CheckInParam checkInParam) {
        this.checkInService.update(checkInParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(CheckInParam checkInParam) {
        this.checkInService.delete(checkInParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(CheckInParam checkInParam) {
        CheckIn detail = this.checkInService.getById(checkInParam.getCheckId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(map.get("registerTime") != "" && map.get("registerTime") != null){
            map.put("registerTime",sf.format(map.get("registerTime")));
        }
        if(map.get("signTime") != "" && map.get("signTime") != null){
            map.put("signTime",sf.format(map.get("signTime")));
        }

        map = new CheckInWrapper(map).wrap();
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CheckInParam checkInParam) {
        return this.checkInService.findPageBySpec(checkInParam);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public LayuiPageInfo wrapList(CheckInParam checkInParam, Long roleId, String name) {

        List<User> userList = new ArrayList<>();
        if (roleId != null){
            userList = userService.listByRole(roleId.toString(), name);
        }else {
            userList = userService.listByRole("", name);
        }

        List<Long> userIds = new ArrayList<>();
        if (userList.size() > 0){
            for (int i = 0;i < userList.size();i++){
                userIds.add(userList.get(i).getUserId());
            }
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
        }

        if (checkInParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                checkInParam.setMeetId(meet.getMeetId());
            }
        } else if (checkInParam.getMeetId() == 0) {
            checkInParam.setMeetId(null);
        }

        Page<Map<String, Object>> checkIn = this.checkInService.findPageWrap(checkInParam, userIds);
        Page wrapped = new CheckInWrapper(checkIn).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-30
     */
    @ResponseBody
    @RequestMapping("/wrapListForum")
    public LayuiPageInfo wrapListForum(CheckInParam checkInParam, Long roleId, String name) {
        List<User> userList = new ArrayList<>();
        if (roleId != null){
            userList = userService.listByRole(roleId.toString(), name);
        }else {
            userList = userService.listByRole("", name);
        }

        List<Long> userIds = new ArrayList<>();
        if (userList.size() > 0){
            for (int i = 0;i < userList.size();i++){
                userIds.add(userList.get(i).getUserId());
            }
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
        }

        Page<Map<String, Object>> checkIn = this.checkInService.findPageWrapForum(checkInParam, userIds);
        Page wrapped = new CheckInWrapper(checkIn).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    @RequestMapping("/getCheckImage")
    @ResponseBody
    public Map<String, Object> getCheckImage(String meetId){
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        String fileName = meetId + "_check.jpg";

        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(path + "\\" + fileName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("checkName", Base64.encodeBase64String(data));
        return map;
    }

    @RequestMapping("/getSignImage")
    @ResponseBody
    public Map<String, Object> getSignImage(String meetId){
        //	指定路径：D:\User\Desktop\testQrcode
        //String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode";
        String path = imagesFolder;
        System.out.println(path);
        String fileName = meetId + "_sign.jpg";

        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(path + "\\" + fileName);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("signName", Base64.encodeBase64String(data));
        return map;
    }
}


