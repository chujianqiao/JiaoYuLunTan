package cn.stylefeng.guns.modular.bill.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.bill.entity.Bill;
import cn.stylefeng.guns.modular.bill.model.params.BillParam;
import cn.stylefeng.guns.modular.bill.service.BillService;
import cn.stylefeng.guns.modular.bill.wrapper.BillWrapper;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author CHU
 * @Date 2020-11-05 16:02:07
 */
@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {

    private String PREFIX = "/bill";

    @Autowired
    private BillService billService;

    @Autowired
    private MeetMemberService meetMemberService;

    @Autowired
    private UserService userService;
    @Autowired
    private MeetService meetService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/bill.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/bill_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/bill_edit.html";
    }

    @RequestMapping("/toDetail")
    public String toDetail() {
        return PREFIX + "/bill_detail.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(BillParam billParam) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        billParam.setUserId(user.getId());
        this.billService.add(billParam);
        MeetMemberParam meetMemberParam = new MeetMemberParam();
        meetMemberParam.setMemberId(billParam.getMeetMemberId());
        meetMemberParam.setMeetStatus(6);
        this.meetMemberService.update(meetMemberParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(BillParam billParam) {
        this.billService.update(billParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(BillParam billParam) {
        this.billService.delete(billParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(BillParam billParam) {
        LoginUser loginUser = LoginContextHolder.getContext().getUser();
        if (billParam.getUserId() == null){
            billParam.setUserId(loginUser.getId());
        }
        Bill bill = this.billService.findByUserAndMeetMember(billParam.getUserId(), billParam.getMeetMemberId());
        Bill detail = this.billService.getById(bill.getBillId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        String userName = userService.getById(bill.getUserId()).getName();
        MeetMember meetMember = meetMemberService.getById(bill.getMeetMemberId());
        String meetName = meetService.getById(meetMember.getMeetId()).getMeetName();

        map.put("userName",userName);
        map.put("meetName",meetName);
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-11-05
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(BillParam billParam) {
        return this.billService.findPageBySpec(billParam);
    }

    @ResponseBody
    @RequestMapping("/listAll")
    public LayuiPageInfo listAll(BillParam billParam, String userName) {
        List<User> userList = new ArrayList<>();
        userList = userService.listByRole("", userName);
        List<Long> userIds = new ArrayList<>();
        if (userList.size() > 0){
            for (int i = 0;i < userList.size();i++){
                userIds.add(userList.get(i).getUserId());
            }
        }else {
            Page wrapped = new Page();
            return LayuiPageFactory.createPageInfo(wrapped);
        }

        if (billParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            billParam.setMeetId(meet.getMeetId());
        } else if (billParam.getMeetId() == 0) {
            billParam.setMeetId(null);
        }


        Page<Map<String, Object>> bill = this.billService.findPageWrap(billParam, userIds);
        Page wrapped = new BillWrapper(bill).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}


