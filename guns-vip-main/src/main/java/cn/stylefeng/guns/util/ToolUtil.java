package cn.stylefeng.guns.util;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class ToolUtil {

	/**
	 * 判断当前登录用户是否为管理员角色
	 * @return
	 */
	public static boolean isAdminRole(){
		LoginUser user = LoginContextHolder.getContext().getUser();
		List roleIds = user.getRoleList();
		long adminRole = 1;
		if(roleIds.contains(adminRole)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 随机生成19位ID
	 * @return
	 */
	public static  long getNum19(){
		String numStr = "" ;
		String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
		String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		numStr = trandStr.toString().substring(0, 4);
		numStr = numStr + dataStr ;
		long intId = Long.parseLong(numStr);
		return intId ;
	}


}
