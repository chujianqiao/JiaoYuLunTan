package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.entity.SeatDetail;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.modular.seat.wrapper.SeatDetailWrapper;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 座位详情表控制器
 * @author wucy
 * @Date 2020-09-11 10:35:51
 */
@Controller
@RequestMapping("/seatDetail")
public class SeatDetailController extends BaseController {

    private String PREFIX = "/seatDetail";

    @Value("${weiXin.appid}")
    private String appid;

    @Value("${weiXin.secret}")
    private String secret;

    @Autowired
    private SeatDetailService seatDetailService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private UserService userService;

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
            //该用户之前有座位，更新
            SeatDetailResult result = list.get(0);
            Long dataId = result.getSeatDetailId();
            seatDetailParam.setSeatDetailId(dataId);
            this.seatDetailService.update(seatDetailParam);
            //继续判断有没有单位占用该座位
            SeatDetailParam tempParam1 = new SeatDetailParam();
            tempParam1.setSeatCol(seatDetailParam.getSeatCol());
            tempParam1.setSeatRow(seatDetailParam.getSeatRow());
            results = this.seatDetailService.findPageBySpec(tempParam1);
            list = results.getData();
            int size = list.size();
            if(size != 0){
                for (int i = 0; i < size; i++) {
                    SeatDetailResult seatDetailResult = list.get(i);
                    String unitName = seatDetailResult.getUnitName();
                    if(seatDetailResult.getUnitName() != null && !("").equals(unitName)){
                        //删除单位的数据
                        this.seatDetailService.removeById(seatDetailResult.getSeatDetailId());
                        continue;
                    }
                    Long orgUserId = seatDetailResult.getUserId();
                    if(orgUserId != null && !orgUserId.equals(seatDetailParam.getUserId())){
                        this.seatDetailService.removeById(seatDetailResult.getSeatDetailId());
                    }
                }
            }
        }else {
            //继续判断座位是否被占用
            SeatDetailParam seatDetailParam1 = new SeatDetailParam();
            BeanUtils.copyProperties(seatDetailParam, seatDetailParam1);
            seatDetailParam1.setUserId(null);
            LayuiPageInfo seatRes = this.seatDetailService.findPageBySpec(seatDetailParam1);
            List<SeatDetailResult> seatResList = seatRes.getData();
            if(seatResList.size() !=0){
                //更新
                SeatDetailResult result = seatResList.get(0);
                seatDetailParam.setSeatDetailId(result.getSeatDetailId());
                String unitName = result.getUnitName();
                //如果有单位名称，删除数据后再添加（直接更新不起作用）
                if(unitName != null && !("").equals(unitName)){
                    this.seatDetailService.removeById(seatDetailParam.getSeatDetailId());
                    seatDetailParam.setUnitName(null);
                    this.seatDetailService.add(seatDetailParam);
                }else{
                    //没有单位，直接更新
                    this.seatDetailService.update(seatDetailParam);
                }
            }else{
                //添加
                this.seatDetailService.add(seatDetailParam);
            }
        }


        String templateId = "dTxk2FjY3SZmx-X5AR1sJ4Aw9-Me4bhMSa6zU4Yq_Ac";
        User resultUser = userService.getById(seatDetailParam.getUserId());
        String userWechatId = resultUser.getWechatId();
        /*if (userWechatId != null && userWechatId != ""){
            String first = "您好，您的座位已被变更。";
            String remark = "您可登录中国教育科学论坛平台查看详细信息。";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time = format.format(new Date());
            List<String> dataList = new ArrayList<>();
            dataList.add(meetService.getById(seatDetailParam.getMeetId()).getMeetName());
            dataList.add(seatDetailParam.getSeatRow() + "排" + seatDetailParam.getSeatCol() + "号");
            dataList.add("排座成功");
            dataList.add(time);
            CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
        }*/

        return ResponseData.success();
    }

    /**
     * 主席台
     * @author wucy
     * @Date 2020-09-11
     */
    @RequestMapping("/platItem")
    @ResponseBody
    public ResponseData platItem(SeatDetailParam seatDetailParam, HttpServletRequest request) {
        SeatDetailParam tempParam = new SeatDetailParam();
        String seatIdStr = request.getParameter("seatId");
        Seat seat = this.seatService.getById(Long.parseLong(seatIdStr));
        Long meetType = seat.getMeetType();
        BeanUtils.copyProperties(seatDetailParam,tempParam);
        if(meetType.equals(1L)){
            tempParam.setMeetType(1L);
            seatDetailParam.setMeetType(1L);
        }else if(meetType.equals(2L)){
            tempParam.setMeetType(2L);
            seatDetailParam.setMeetType(2L);
        }
        tempParam.setUnitName(null);
        int seatRow = seatDetailParam.getSeatRow();
        int seatCol = seatDetailParam.getSeatCol();
        if(0 == seatRow){
            tempParam.setZeroRow("第0行");
        }
        if(0 == seatCol){
            tempParam.setZeroCol("第0列");
        }
        //查询时使用tempParam作为条件
        LayuiPageInfo results = this.seatDetailService.findPageBySpec(tempParam);
        List<SeatDetailResult> list = results.getData();
        seatDetailParam.setCreatTime(new Date());
        if(list.size() == 0){
            //新增
            this.seatDetailService.add(seatDetailParam);
        }else{
            //更新
            Long dataId = list.get(0).getSeatDetailId();
            seatDetailParam.setSeatDetailId(dataId);
            this.seatDetailService.update(seatDetailParam);
        }
        return ResponseData.success();
    }

    /**
     *  重置单个座位
     */
    @RequestMapping("/resetSeat")
    @ResponseBody
    public ResponseData resetItem(SeatDetailParam seatDetailParam, HttpServletRequest request) {
        String seatIdStr = request.getParameter("seatId");
        Seat seat = this.seatService.getById(Long.parseLong(seatIdStr));
        Long meetType = seat.getMeetType();
        seatDetailParam.setMeetType(meetType);
        LayuiPageInfo results = this.seatDetailService.findPageBySpec(seatDetailParam);
        List<SeatDetailResult> list = results.getData();
        SeatDetailResult seatDetailResult = list.get(0);
        Long seatDetailId = seatDetailResult.getSeatDetailId();
        this.seatDetailService.removeById(seatDetailId);

        String templateId = "dTxk2FjY3SZmx-X5AR1sJ4Aw9-Me4bhMSa6zU4Yq_Ac";
        User resultUser = userService.getById(seatDetailResult.getUserId());
        String userWechatId = resultUser.getWechatId();
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
    }

    /**
     *  批量分配（选择单位）
     */
    @RequestMapping("/batchItem")
    @ResponseBody
    public ResponseData batchItem(SeatDetailParam seatDetailParam, HttpServletRequest request) {
        Long seatId = Long.parseLong(request.getParameter("seatId"));
        Seat seat = this.seatService.getById(seatId);
        Long meetType = seat.getMeetType();
        //确定会议类型
        seatDetailParam.setMeetType(meetType);
        String divIds = request.getParameter("divIds");
        String [] idArr = divIds.split(",");
        Date createDate = new Date();
        for (int i = 0; i < idArr.length; i++) {
            String divId = idArr[i];
            String rowNum = divId.substring(divId.indexOf("_") + 1,divId.lastIndexOf("_"));
            String colNum = divId.substring(divId.lastIndexOf("_") + 1);
            seatDetailParam.setSeatRow(Integer.parseInt(rowNum));
            seatDetailParam.setSeatCol(Integer.parseInt(colNum));
            SeatDetailParam tempParam = new SeatDetailParam();
            BeanUtils.copyProperties(seatDetailParam,tempParam);
            //查询座位时参数不包含单位名称
            tempParam.setUnitName(null);
            LayuiPageInfo Res = this.seatDetailService.findPageBySpec(tempParam);
            List<SeatDetailResult> list = Res.getData();

            seatDetailParam.setCreatTime(createDate);
            if(list.size() == 0){
                seatDetailParam.setSeatDetailId(null);
                this.seatDetailService.add(seatDetailParam);
            }else{
                SeatDetailResult seatDetailResult = list.get(0);
                seatDetailParam.setSeatDetailId(seatDetailResult.getSeatDetailId());
                Long userId = seatDetailResult.getUserId();
                //如果有userId,删除数据再添加，直接更新不起作用
                if(userId != null){
                    this.seatDetailService.removeById(seatDetailParam.getSeatDetailId());
                    seatDetailParam.setUserId(null);
                    this.seatDetailService.add(seatDetailParam);
                }else{
                    this.seatDetailService.update(seatDetailParam);
                }

            }
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

    /**
     * 查询一条数据
     * @author wucy
     * @Date 2020-11-11
     */
    @ResponseBody
    @RequestMapping("/ownSeat")
    public ResponseData ownSeat(SeatDetailParam seatDetailParam) {
        Page<Map<String, Object>> details = this.seatDetailService.findPageWrap(seatDetailParam);
        Page wrapped = new SeatDetailWrapper(details).wrap();
        List list = wrapped.getRecords();
        Object obj = null;
        if(list.size() != 0){
            obj = list.get(0);
        }
        return ResponseData.success(obj);
    }

}


