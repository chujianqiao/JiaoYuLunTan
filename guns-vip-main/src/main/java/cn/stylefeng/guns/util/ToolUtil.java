package cn.stylefeng.guns.util;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
public class ToolUtil {

//	@Autowired
//	private UserService userService;

	private UserService userService = SpringContextHolder.getBean(UserService.class);

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

	/**
	 * 根据用户名模糊查询出用户id
	 * 可以直接拼接sql中的 in 条件
	 * @param userName
	 * @return
	 */
	public  String getUserIdsForName(String userName){
		Page<Map<String, Object>> users = userService.selectUsers(null, userName, null, null, null);
		List<Map<String,Object>> usersRecords = users.getRecords();
		StringBuilder userIds = new StringBuilder();
		String paramIds = "";
		for(int i = 0;i < usersRecords.size();i++){
			String userid = usersRecords.get(i).get("userId").toString();
			userIds.append(userid);
			if (i != usersRecords.size() - 1){
				userIds.append(",");
			}
		}
		paramIds = userIds.toString();
		if(paramIds.length() == 0){
			paramIds = "";
		}
		return paramIds;
	}


}
