package cn.stylefeng.guns.meet.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meet.wrapper.MeetWrapper;
import cn.stylefeng.guns.util.WordUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 会议表控制器
 *
 * @author wucy
 * @Date 2020-08-05 15:21:03
 */
@Controller
@RequestMapping("/meet")
public class MeetController extends BaseController {

    private String PREFIX = "/meet";

    @Autowired
    private MeetService meetService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/meet.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/meet_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("meetIdParam","");
        return PREFIX + "/meet_edit.html";
    }

    /**
     * 会议基本信息页面
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/info")
    public String info(MeetParam meetParam, HttpServletRequest request) {
        meetParam.setMeetStatus(1);
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        List<Map<String, Object>> list = meets.getRecords();
        Long meetId = Long.parseLong(list.get(0).get("meetId").toString());
        meetParam.setMeetId(meetId);
        request.setAttribute("meetIdParam",meetId);
        return PREFIX + "/meet_edit_admin.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public Object addItem(Meet meet) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        meet.setRegUser(user.getId());
        meet.setRegTime(new Date());
        this.meetService.save(meet);
        return SUCCESS_TIP;
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(MeetParam meetParam) {
        this.meetService.update(meetParam);
        return ResponseData.success();
    }

    /**
     * 发布接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/pubMeet")
    @ResponseBody
    public ResponseData pubMeet(MeetParam meetParam) {
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        List<Map<String,Object>> list = meets.getRecords();
        //重置会议状态
        for (int i = 0; i < list.size() ; i++) {
            Map map = list.get(i);
            long meetId = Long.parseLong(map.get("meetId").toString());
            MeetParam meetPar = new MeetParam();
            meetPar.setMeetId(meetId);
            meetPar.setMeetStatus(0);
            this.meetService.update(meetPar);
        }
        //修改需要发布的会议的状态
        meetParam.setMeetStatus(1);
        this.meetService.update(meetParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(MeetParam meetParam) {
        this.meetService.delete(meetParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(MeetParam meetParam) {
        Meet detail = this.meetService.getById(meetParam.getMeetId());
        return ResponseData.success(detail);
    }

    /**
     * 查看发布会议详情
     * @author wucy
     * @Date 2020-08-05
     */
    @RequestMapping("/detailPub")
    @ResponseBody
    public ResponseData detailPub(MeetParam meetParam) {
        Meet detail = this.meetService.getByStatus(1);
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-08-05
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(MeetParam meetParam) {
        return this.meetService.findPageBySpec(meetParam);
    }

    /**
     * 查询列表（添加所需的字段）
     * @param meetParam
     * @return
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(MeetParam meetParam){
        Page<Map<String, Object>> meets = this.meetService.findPageWrap(meetParam);
        Page wrapped = new MeetWrapper(meets).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    @RequestMapping("/exportWord")
    public void exportWord(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("meetId");
        Long meetId = Long.parseLong(idStr);
        Meet meet = this.meetService.getById(meetId);
        String html = meet.getContent();
        String title = meet.getMeetName();
        WordUtil.exportWords(request,response,html,title);
    }

}


