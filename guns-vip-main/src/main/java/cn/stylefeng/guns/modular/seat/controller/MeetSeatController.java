package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.modular.weixin.util.CommonUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 座位控制器
 * @author wucy
 */
@Controller
@RequestMapping("/meetSeat")
public class MeetSeatController {

	private static final String PREFIX = "/seat";

	@Value("${weiXin.appid}")
	private String appid;

	@Value("${weiXin.secret}")
	private String secret;

	@Autowired
	private MeetMemberService meetMemberService;

	@Autowired
	private SeatService seatService;

	@Autowired
	private UserService userService;

	@Autowired
	private MeetService meetService;

	@Autowired
	private SeatDetailService seatDetailService;

	/**
	 * 跳转到主页面
	 * @author wucy
	 * @Date 2020-09-01
	 */
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		boolean isAdmin = ToolUtil.isAdminRole();
		if(!isAdmin){
			long userId = LoginContextHolder.getContext().getUser().getId();
			request.setAttribute("loginUser",userId);
		}else{
			request.setAttribute("loginUser","admin");
		}
		return PREFIX + "/meetSeat.html";
	}

	/**
	 * 分配单个座位
	 * @return
	 */
	@RequestMapping("changeOne")
	public String changeUser(HttpServletRequest request){
		String divId = request.getParameter("divId");
		String meetId = request.getParameter("meetId");
		String seatId = request.getParameter("seatId");
		request.setAttribute("divId",divId);
		request.setAttribute("meetId",meetId);
		request.setAttribute("seatId",seatId);
		return PREFIX + "/seat_change.html";
	}

	/**
	 * 分配主席台座位
	 * @return
	 */
	@RequestMapping("changePlat")
	public String changePlat(HttpServletRequest request){
		String divId = request.getParameter("divId");
		String meetId = request.getParameter("meetId");
		String seatId = request.getParameter("seatId");
		request.setAttribute("divId",divId);
		request.setAttribute("meetId",meetId);
		request.setAttribute("seatId",seatId);
		return PREFIX + "/plat_change.html";
	}

	/**
	 * 批量分配页面（选择单位）
	 * @return
	 */
	@RequestMapping("batch")
	public String batch(HttpServletRequest request){
		String divIds = request.getParameter("divIds");
		String meetId = request.getParameter("meetId");
		String seatId = request.getParameter("seatId");
		request.setAttribute("divIds",divIds);
		request.setAttribute("meetId",meetId);
		request.setAttribute("seatId",seatId);
		return PREFIX + "/seat_batch.html";
	}

	/**
	 * 自动分配
	 * @return
	 */
	@RequestMapping("autoAssign")
	@ResponseBody
	public ResponseData autoAssign(@RequestParam(required = false) Long seatId){
		Seat seat = this.seatService.getById(seatId);
		Integer rowNum = seat.getRowNum();
		Integer colNum = seat.getColNum();
		Long meetId = seat.getMeetId();
		Long meetType = seat.getMeetType();
		String seatType = seat.getSeatType();
		//先重置座次表
		SeatDetailParam seatDetailParam = new SeatDetailParam();
		seatDetailParam.setMeetId(meetId);
		seatDetailParam.setMeetType(meetType);
		this.seatDetailService.deleteData(seatDetailParam);

		//开始分配
		Integer[] colArr = getColArr(seatType,colNum);
		List<Long> memberList = getMember(meetId,meetType);
		Integer [][] seatArr = new Integer[rowNum][colNum];
		int i = 0;
		seatDetailParam.setCreatTime(new Date());

		String templateId = "dTxk2FjY3SZmx-X5AR1sJ4Aw9-Me4bhMSa6zU4Yq_Ac";
		for (int j = 1; j <= seatArr.length; j++) {
			for (int k = 0; k < seatArr[0].length; k++) {
				if(i >= memberList.size()) {
					break;
				}
				//设置行、列、userId
				Long userId = memberList.get(i);
				seatDetailParam.setUserId(userId);
				seatDetailParam.setSeatRow(j);
				seatDetailParam.setSeatCol(colArr[k]);
				this.seatDetailService.add(seatDetailParam);
				i ++;

				User resultUser = userService.getById(userId);
				String userWechatId = resultUser.getWechatId();
				/*if (userWechatId != null && userWechatId != ""){
					String first = "您好，您的座位已被变更。";
					String remark = "您可登录中国教育科学论坛平台查看详细信息。";
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String time = format.format(new Date());
					List<String> dataList = new ArrayList<>();
					dataList.add(meetService.getById(seatDetailParam.getMeetId()).getMeetName());
					dataList.add(j + "排" + colArr[k] + "号");
					dataList.add("排座成功");
					dataList.add(time);
					CommonUtil.push(appid, secret, templateId, dataList, userWechatId, first, remark);
				}*/

			}
		}
		return ResponseData.success();
	}

	@RequestMapping("seatMember")
	@ResponseBody
	public List<User> seatMember(Long meetId,Long type){
		List<User> userList = new ArrayList<>();
		List<Long> memberIds = getMember(meetId,type);
		for(Long userId:memberIds){
			User user = this.userService.getById(userId);
			userList.add(user);
		}
		return userList;
	}

	/**
	 * 获取成员并排序
	 * 有排序权重字段的在前，其余按照省份分类
	 * @return
	 */
	public List<Long> getMember(Long meetId,Long type){
		List<Long> resList = new ArrayList<>();
		List<Long> sortMember = this.meetMemberService.sortMember(meetId);
		List<Long> generalMember = this.meetMemberService.generalMember(meetId);
		resList.addAll(sortMember);
		resList.addAll(generalMember);
		return resList;
	}

	/**
	 * 提交座位信息
	 * @return
	 */
	@RequestMapping("updateSeat")
	public ResponseData updateSeat(MeetMemberParam meetMemberParam){
		this.meetMemberService.update(meetMemberParam);
		return ResponseData.success();
	}

	/**
	 * 获取“列”数组（根据奇偶）
	 * @param seatType
	 * @param colNum
	 * @return
	 */
	private Integer[] getColArr(String seatType,Integer colNum){
		Integer[] colArr = new Integer[colNum];
		List<Integer> colList = new ArrayList<>();
		List<Integer> evenList = new ArrayList<>();
		List<Integer> oddList = new ArrayList<>();
		if(("A").equals(seatType)){
			for (int i = 1; i <= colNum; i++) {
				colList.add(i);
			}
		}else if(("B").equals(seatType)){
			for (int i = 1; i <= colNum; i++) {
				if(i%2 ==0){
					evenList.add(i);
				}else{
					oddList.add(i);
				}
			}
			oddList.addAll(evenList);
			colList.addAll(oddList);
		}
		colList.toArray(colArr);
		return colArr;
	}

}
