package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.entity.SeatDetail;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.modular.seat.wrapper.SeatDetailWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 座位详情表控制器
 * @author wucy
 * @Date 2020-09-11 10:35:51
 */
@Controller
@RequestMapping("/seatDetail")
public class SeatDetailController extends BaseController {

    private String PREFIX = "/seatDetail";

    @Autowired
    private SeatDetailService seatDetailService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private SeatService seatService;

    /**
     * 跳转到主页面
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/seatDetail.html";
    }

    /**
     * 新增页面
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/seatDetail_add.html";
    }

    /**
     * 编辑页面
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/seatDetail_edit.html";
    }

    /**
     * 新增接口
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SeatDetailParam seatDetailParam) {
        this.seatDetailService.add(seatDetailParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SeatDetailParam seatDetailParam, HttpServletRequest request) {
        SeatDetailParam tempParam = new SeatDetailParam();
        String seatIdStr = request.getParameter("seatId");
        Seat seat = this.seatService.getById(Long.parseLong(seatIdStr));
        Long meetType = seat.getMeetType();
        if(meetType.equals(1L)){
            seatDetailParam.setMeetType(1L);
            tempParam.setMeetType(1L);
        }else if(meetType.equals(2L)){
            seatDetailParam.setMeetType(2L);
            tempParam.setMeetType(2L);
        }
        tempParam.setMeetId(seatDetailParam.getMeetId());
        tempParam.setUserId(seatDetailParam.getUserId());
        //查询时使用tempParam作为条件，只包含userId、meetId、meetType
        LayuiPageInfo results = this.seatDetailService.findPageBySpec(tempParam);
        List<SeatDetailResult> list = results.getData();
        seatDetailParam.setCreatTime(new Date());
        if(list.size() != 0){
            //更新
            SeatDetailResult result = list.get(0);
            seatDetailParam.setSeatDetailId(result.getSeatDetailId());
            this.seatDetailService.update(seatDetailParam);
        }else {
            //添加
            this.seatDetailService.add(seatDetailParam);
        }
        return ResponseData.success();
    }

    /**
     * 删除接口
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SeatDetailParam seatDetailParam) {
        this.seatDetailService.delete(seatDetailParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SeatDetailParam seatDetailParam) {
        SeatDetail detail = this.seatDetailService.getById(seatDetailParam.getSeatDetailId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     * @author wucy
     * @Date 2020-09-11
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SeatDetailParam seatDetailParam) {
        return this.seatDetailService.findPageBySpec(seatDetailParam);
    }

    /**
     * 查询列表(拼接字段)
     * @author wucy
     * @Date 2020-09-11
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(SeatDetailParam seatDetailParam) {
        Page<Map<String, Object>> details = this.seatDetailService.findPageWrap(seatDetailParam);
        Page wrapped = new SeatDetailWrapper(details).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}


