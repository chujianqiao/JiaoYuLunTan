package cn.stylefeng.guns.meetRegister.wrapper;

import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.mapper.OwnForumMapper;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.mapper.ThesisMapper;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class MeetMemberWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

	private ThesisMapper thesisMapper = SpringContextHolder.getBean(ThesisMapper.class);

	private OwnForumMapper ownForumMapper = SpringContextHolder.getBean(OwnForumMapper.class);

	public MeetMemberWrapper(Map<String, Object> single) {
		super(single);
	}

	public MeetMemberWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public MeetMemberWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public MeetMemberWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		//用户
		long userId = Long.parseLong(map.get("userId").toString());
		User user = userMapper.selectById(userId);
		String memberName = user.getName();
		String unitName = user.getWorkUnit();
		String userPost = user.getPost();
		if(userPost == null || userPost.equals("")){
			userPost = user.getTitle();
		}
		String direct = user.getDirection();

		//论文
		long thesisId = Long.parseLong(map.get("thesisId").toString());
		Thesis thesis = thesisMapper.selectById(thesisId);
		String thesisName = thesis.getThesisTitle();

		//论坛
		long ownForumid = Long.parseLong(map.get("ownForumid").toString());
		OwnForum ownForum = ownForumMapper.selectById(ownForumid);
		String forumName = "";
		if (ownForum == null){
			forumName = "未选择";
		}else {
			forumName = ownForum.getForumName();
		}


		int isSpeak = Integer.parseInt(map.get("speak").toString());
		String speak = TransTypeUtil.getIsOrNo().get(isSpeak).toString();

		map.put("memberName",memberName);
		map.put("unitName",unitName);
		map.put("userPost",userPost);
		map.put("direct",direct);
		map.put("thesisName",thesisName);
		map.put("forumName",forumName);
		map.put("speak",speak);
	}
}
