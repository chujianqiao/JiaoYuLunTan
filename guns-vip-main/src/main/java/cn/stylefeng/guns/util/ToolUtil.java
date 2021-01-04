package cn.stylefeng.guns.util;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.sys.modular.system.entity.Role;
import cn.stylefeng.guns.sys.modular.system.service.RoleService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 工具类
 * @author wucy
 */
public class ToolUtil {

	private static Logger log = LoggerFactory.getLogger("ToolUtil");

	private UserService userService = SpringContextHolder.getBean(UserService.class);

	private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);
	private static MeetService meetService = SpringContextHolder.getBean(MeetService.class);
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
	 * 判断当前登录用户是否为论文评审专家角色
	 * @return
	 */
	public static boolean isReviewRole(){
		LoginUser user = LoginContextHolder.getContext().getUser();
		List<Long> roleIds = user.getRoleList();
		boolean isReview = false;
		for (int i = 0;i < roleIds.size();i++){
			long roleId = roleIds.get(i);
			Role role = roleService.getById(roleId);
			String roleName = role.getName();
			if(roleId == 4 || roleName.equals("论文评审专家")){
				isReview = true;
				break;
			}
		}
		return isReview;
	}

	/**
	 * 随机生成19位ID
	 * @return
	 */
	public static  long getNum19(){
		String numStr = "" ;
		String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
		String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		numStr = trandStr.substring(0, 4);
		numStr = numStr + dataStr ;
		long intId = 0;
		try {
			intId = Long.parseLong(numStr);
		}catch (NumberFormatException e){
			getNum19();
		}

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
			paramIds = "0";
		}
		return paramIds;
	}

	/**
	 * 获取userId的List
	 * @param userName
	 * @return
	 */
	public static List<Long> getUserIdList(String userName){
		String userIds = "";
		List<Long> userIdList = new ArrayList<>();
		if(userName != null && userName != ""){
			ToolUtil toolUtil = new ToolUtil();
			userIds = toolUtil.getUserIdsForName(userName);
		}
		if(userIds.equals("")){
			return userIdList;
		}
		String[] userIdArr = userIds.split(",");
		for (int i = 0; i < userIdArr.length; i++) {
			userIdList.add(Long.parseLong(userIdArr[i]));
		}
		return userIdList;
	}

	private static final int  BUFFER_SIZE = 2 * 1024;
	/**
	 * 压缩文件
	 * @param srcFiles 需要压缩的文件列表
	 * @param out 压缩文件输出流
	 * @throws RuntimeException
	 */
	public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1){
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			log.info("压缩完成，耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询当前是否有会议，会议时间的状态
	 * @return
	 */
	public static String getMeetTimeStatus(){
		Meet meet = meetService.getByStatus(1);
		if (meet == null){
			return "无会议";
		}else {
			long nowDate = new Date().getTime();
			long joinBegTime = meet.getJoinBegTime().getTime();
			long joinEndTime = meet.getJoinEndTime().getTime();
			long beginTime = meet.getBeginTime().getTime();
			long endTime = meet.getEndTime().getTime();

			if (nowDate <= joinEndTime && nowDate > joinBegTime){
				return "报名中";
			} else if (nowDate <= endTime && nowDate >= beginTime){
				return "进行中";
			} else if (nowDate > endTime){
				return "已结束";
			} else if (nowDate > joinEndTime && nowDate < beginTime){
				return "报名结束";
			} else {
				return "未开始";
			}
		}

	}
}
