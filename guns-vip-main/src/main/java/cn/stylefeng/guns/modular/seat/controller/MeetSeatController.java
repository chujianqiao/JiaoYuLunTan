package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.service.SeatDetailService;
import cn.stylefeng.guns.modular.seat.service.SeatService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	private MeetMemberService meetMemberService;

	@Autowired
	private SeatService seatService;

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
		//先重置座次表
		SeatDetailParam seatDetailParam = new SeatDetailParam();
		seatDetailParam.setMeetId(meetId);
		seatDetailParam.setMeetType(meetType);
		this.seatDetailService.deleteData(seatDetailParam);

		//开始分配
		List<Long> memberList = getMember(meetId,meetType);
		Integer [][] seatArr = new Integer[rowNum][colNum];
		int i = 0;
		seatDetailParam.setCreatTime(new Date());
		for (int j = 1; j <= seatArr.length; j++) {
			for (int k = 1; k <= seatArr[0].length; k++) {
				if(i >= memberList.size()) {
					break;
				}
				//设置行、列、userId
				Long userId = memberList.get(i);
				seatDetailParam.setUserId(userId);
				seatDetailParam.setSeatRow(j);
				seatDetailParam.setSeatCol(k);
				this.seatDetailService.add(seatDetailParam);
				i ++;
			}
		}
		return ResponseData.success();
	}

	/**
	 * 获取成员并排序
	 * 有排序权重字段的在前，其余按照省份分类
	 * @return
	 */
	private List<Long> getMember(Long meetId,Long type){
		List<Long> resList = new ArrayList<>();
		List<Long> sortMember = this.meetMemberService.sortMember();
		List<Long> generalMember = this.meetMemberService.generalMember();
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

}
