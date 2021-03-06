package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.params.SeatParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.modular.seat.wrapper.SeatWrapper;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 座位表控制器
 * @author wucy
 * @Date 2020-09-10 15:21:15
 */
@Controller
@RequestMapping("/seat")
public class SeatController extends BaseController {

    private String PREFIX = "/seat";

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Autowired
    private SeatService seatService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeatDetailService seatDetailService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/seat.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/seat_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/seat_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SeatParam seatParam) {
        this.seatService.add(seatParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SeatParam seatParam) {
        this.seatService.update(seatParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SeatParam seatParam) {
        this.seatService.delete(seatParam);
        return ResponseData.success();
    }

    /**
     * 重置座次接口
     * @author wucy
     * @Date 2020-09-15
     */
    @RequestMapping("/reset")
    @ResponseBody
    public ResponseData reset(SeatParam seatParam) {
        Long seatId = seatParam.getSeatId();
        Seat seat = this.seatService.getById(seatId);
        Long meetId = seat.getMeetId();
        Long meetType = seat.getMeetType();
        if(meetId != null){
            SeatDetailParam seatDetailParam = new SeatDetailParam();
            seatDetailParam.setMeetId(meetId);
            seatDetailParam.setMeetType(meetType);

            LayuiPageInfo results = this.seatDetailService.findPageBySpec(seatDetailParam);
            List<SeatDetailResult> list = results.getData();
            SeatDetailResult seatDetailResult = new SeatDetailResult();
            if (list.size() > 0){
                seatDetailResult = list.get(0);
                this.seatDetailService.deleteData(seatDetailParam);
            }


            //String templateId = "dTxk2FjY3SZmx-X5AR1sJ4Aw9-Me4bhMSa6zU4Yq_Ac";
            //User resultUser = userService.getById(seatDetailResult.getUserId());
            //String userWechatId = resultUser.getWechatId();
            /*if (userWechatId != null && userWechatId != ""){
                String first = "您好，您的座位已被变更。";
                String remark = "您可登录中国教育科学论坛平台查看详细信息。";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String time = format.format(new Date());
                List<String> dataList = new ArrayList<>();
                dataList.add(meetService.getById(seatDetailResult.getMeetId()).getMeetName());
                dataList.add(seatDetailResult.getSeatRow() + "排" + seatDetailResult.getSeatCol() + "号");
                dataList.add("取消成功");
                dataList.add(time);
                CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
            }*/

            return ResponseData.success();
        }else {
            throw new ServiceException(BizExceptionEnum.PARAM_IS_NULL);
        }
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-09-10
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SeatParam seatParam) {
        Seat detail = this.seatService.getById(seatParam.getSeatId());
        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(detail), Map.class);
        //会议类型
        Long meetType = Long.parseLong(map.get("meetType").toString());
        if(meetType.equals(1L)){
            Long meetId = Long.parseLong(map.get("meetId").toString());
            Meet meet = this.meetService.getById(meetId);
            String meetName = meet.getMeetName();
            map.put("meetName",meetName);
            map.put("meetTypeStr","大会");
        }else if(meetType.equals(2L)){
            map.put("meetTypeStr","分论坛");
        }
        //排列方式
        String sortType = map.get("seatType").toString();
        if(sortType.equals("A")){
            map.put("seatTypeStr","正常排列（按照从左到右顺序排列）");
        }else if(sortType.equals("B")){
            map.put("seatTypeStr","非正常排列（按照奇偶数排列）");
        }
        return ResponseData.success(map);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-09-10
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SeatParam seatParam) {
        return this.seatService.findPageBySpec(seatParam);
    }

    /**
     * 查询列表(wrap)
     * @author wucy
     * @Date 2020-09-10
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(SeatParam seatParam,@RequestParam(required = false) String seatName) {
        List<Long> meetIdList = new ArrayList<>();
        if(seatName != null && !("").equals(seatName)){
            MeetParam meetParam = new MeetParam();
            meetParam.setMeetName(seatName);
            Page<Map<String, Object>> meetsRes = this.meetService.findPageWrap(meetParam);
            List<Map<String,Object>> meets =  meetsRes.getRecords();
            for (int i = 0; i < meets.size(); i++) {
                Long meetId = Long.parseLong(meets.get(i).get("meetId").toString());
                meetIdList.add(meetId);
            }
            //查询不到会议，list元素为0
            if(meetIdList.size() == 0){
                meetIdList.add(0L);
            }
        }
        //只返回当前环境变量设置的会议
        if (seatParam.getMeetId() == null){
            Meet meet = meetService.getByStatus(1);
            if (meet != null){
                Long meetId = meet.getMeetId();
                seatParam.setMeetId(meetId);
            }
        } else if (seatParam.getMeetId() == 0) {
            seatParam.setMeetId(null);
        }

        Page<Map<String, Object>> seats = this.seatService.findPageWrap(seatParam,meetIdList);
        Page wrapped = new SeatWrapper(seats).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}


