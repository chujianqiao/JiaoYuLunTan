package cn.stylefeng.guns.modular.seat.controller;

import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

	/**
	 * 跳转到主页面
	 * @author wucy
	 * @Date 2020-09-01
	 */
	@RequestMapping("")
	public String index() {
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
	 * 提交座位信息
	 * @return
	 */
	@RequestMapping("updateSeat")
	public ResponseData updateSeat(MeetMemberParam meetMemberParam){
		this.meetMemberService.update(meetMemberParam);
		return ResponseData.success();
	}

}
