package cn.stylefeng.guns.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 转换数据类型的工具类
 */
public class TransTypeUtil {

	/**
	 * 申请状态
	 * 状态码 → 字符串
	 */
	private static Map<Object,Object> applyStatus = new HashMap <>();

	/**
	 * 申请状态
	 * 字符串 → 状态码
	 */
	private static Map<Object,Integer> applyStatusNum = new HashMap <>();

	/**
	 * 是否
	 * 状态码 → 字符串
	 */
	private static Map<Object,Object> isOrNo = new HashMap();

	/**
	 * 是否通过
	 * 状态码→字符串
	 */
	private static Map<Object,Object> isPass = new HashMap();

	/**
	 * 会议状态
	 * 状态码 → 字符串
	 */
	private static Map<Object,Object> meetStatus = new HashMap<>();

	/**
	 * 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
	 * @return
	 */
	public static Map<Object, Object> getApplyStatus() {
		applyStatus.put(0,"已取消");
		applyStatus.put(1,"申请中");
		applyStatus.put(2,"已通过");
		applyStatus.put(3,"未通过");
		return applyStatus;
	}

	public static Map<Object, Integer> getApplyStatusNum() {
		applyStatus.put("已取消",0);
		applyStatus.put("申请中",1);
		applyStatus.put("已通过",2);
		applyStatus.put("未通过",3);
		return applyStatusNum;
	}

	/**
	 * 0-否，1-是
	 * @return
	 */
	public static Map<Object, Object> getIsOrNo() {
		isOrNo.put(0,"否");
		isOrNo.put(1,"是");
		return isOrNo;
	}

	/**
	 * 0-未通过，1-通过
	 * @return
	 */
	public static Map<Object,Object> getIsPass(){
		isPass.put(0,"不同意参会");
		isPass.put(1,"同意参会");
		isPass.put(2,"同意参会并推荐优秀");
		return isPass;
	}

	/**
	 * 1-评审中,2-评审通过,3-已取消,4-已缴费
	 * @return
	 */
	public static Map<Object, Object> getMeetStatus() {
		meetStatus.put(1,"评审中");
		meetStatus.put(2,"评审通过");
		meetStatus.put(3,"已取消");
		meetStatus.put(4,"已缴费");
		return meetStatus;
	}
}
