package cn.stylefeng.guns.util;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;

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

}
