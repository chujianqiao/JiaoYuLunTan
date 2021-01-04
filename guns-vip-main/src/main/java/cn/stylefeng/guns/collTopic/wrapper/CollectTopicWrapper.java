package cn.stylefeng.guns.collTopic.wrapper;

import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.mapper.MeetMapper;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.modular.system.controller.UserMgrController;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class CollectTopicWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private MeetMapper meetMapper = SpringContextHolder.getBean(MeetMapper.class);

	public CollectTopicWrapper(Map<String, Object> single) {
		super(single);
	}

	public CollectTopicWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public CollectTopicWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public CollectTopicWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		//获取填报人的信息
		long userId = Long.parseLong(map.get("createUser").toString());
		User user = userMapper.selectById(userId);

		String userName = user.getName();
		String unitName = user.getWorkUnit();
		String userPost = user.getPost();
		if(userPost == null || userPost == ""){
			userPost = user.getTitle();
		}

		Object meetIdObj = map.get("meetId");
		if (meetIdObj != null){
			Long meetId = Long.parseLong(meetIdObj.toString());
			Meet meet = meetMapper.selectById(meetId);
			map.put("meetName",meet.getMeetName());
		}

		//添加返回的数据
		map.put("userName",userName);
		map.put("unitName",unitName);
		map.put("userPost",userPost);
	}
}
