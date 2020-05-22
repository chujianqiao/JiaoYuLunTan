package cn.stylefeng.guns.meetRegister.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.meetRegister.wrapper.MeetMemberWrapper;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
import cn.stylefeng.guns.sys.core.util.FileDownload;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 会议注册成员表控制器
 * @author wucy
 * @Date 2020-05-20 15:32:22
 */
@Controller
@RequestMapping("/meetMember")
public class MeetMemberController extends BaseController {

    private String PREFIX = "/meetMember";

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private OwnForumService ownForumService;

    @Autowired
    private ThesisService thesisService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("")
    public String index() {
        boolean isAdmin = ToolUtil.isAdminRole();
        if(isAdmin){
            return PREFIX + "/meetMember_admin.html";
        }else{
            return PREFIX + "/meetMember.html";
        }
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/meetMember_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/meetMember_edit.html";
    }

    /**
     * 仅查看详情
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/disable")
    public String disable(MeetMemberParam meetMemberParam) {
        return PREFIX + "/meetMember_edit_admin.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(MeetMemberParam meetMemberParam) {
        this.meetMemberService.add(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(MeetMemberParam meetMemberParam) {
        this.meetMemberService.update(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(MeetMemberParam meetMemberParam) {
        this.meetMemberService.delete(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-05-20
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(MeetMemberParam meetMemberParam) {
        MeetMember detail = this.meetMemberService.getById(meetMemberParam.getMemberId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);

        //加入自设论坛名称
        Long ownForumId = detail.getOwnForumid();
        if(ownForumId != null){
            OwnForum ownForum = this.ownForumService.getById(ownForumId);
            String ownForumName = ownForum.getForumName();
            map.put("ownForumName",ownForumName);
        }

        //加入论文题目
        Long thesisId = detail.getThesisId();
        if(thesisId != null){
            Thesis thesis = this.thesisService.getById(thesisId);
            String thesisName = thesis.getThesisTitle();
            map.put("thesisName",thesisName);
        }

        Date date = new Date(Long.parseLong(map.get("regTime").toString()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        map.put("regTime",dateString);

        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(MeetMemberParam meetMemberParam) {
        return this.meetMemberService.findPageBySpec(meetMemberParam);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-05-20
     */
    @ResponseBody
    @RequestMapping("/wraplist")
    public Object wrapList(MeetMemberParam meetMemberParam,@RequestParam(required = false) String userName) {
        String userIds = "";
        if(userName != null && userName != ""){
            ToolUtil toolUtil = new ToolUtil();
            userIds = toolUtil.getUserIdsForName(userName);
        }
        Page<Map<String, Object>> members = this.meetMemberService.findPageWrap(meetMemberParam,userIds);
        Page wrapped = new MeetMemberWrapper(members).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 下载模板文件
     * @author wucy
     */
    @RequestMapping(path = "/downloadThesis")
    public void download(HttpServletResponse httpServletResponse, MeetMemberParam meetMemberParam, HttpServletRequest request) {
        String a = request.getParameter("thesisId");
        long thesisId = meetMemberParam.getThesisId();
        Thesis thesis = this.thesisService.getById(thesisId);
        //文件完整路径
        String filePath = thesis.getThesisPath();
        //下载后看到的文件名
        String fileName = thesis.getFileName();
        try {
            FileDownload.fileDownload(httpServletResponse, filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


