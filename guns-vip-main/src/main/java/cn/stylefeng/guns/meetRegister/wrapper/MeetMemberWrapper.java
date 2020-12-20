package cn.stylefeng.guns.meetRegister.wrapper;

import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.mapper.ForumMapper;
import cn.stylefeng.guns.modular.ownForum.entity.OwnForum;
import cn.stylefeng.guns.modular.ownForum.mapper.OwnForumMapper;
import cn.stylefeng.guns.modular.ownForum.service.OwnForumService;
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
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MeetMemberWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

	private ThesisMapper thesisMapper = SpringContextHolder.getBean(ThesisMapper.class);

//	private OwnForumMapper ownForumMapper = SpringContextHolder.getBean(OwnForumMapper.class);

	private ForumMapper forumMapper = SpringContextHolder.getBean(ForumMapper.class);

	@Autowired
	private OwnForumService ownForumService;

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
		String thesisName = "";
		if (thesis != null){
			thesisName = thesis.getThesisTitle();
		}else {
			thesisName = "无";
		}


		//论坛
		String forumName = "";
		if (map.get("ownForumid") == null || map.get("ownForumid") == ""){
			forumName = "未选择";
		}else {
			Long ownForumid = Long.parseLong(map.get("ownForumid").toString());
			Forum forum = forumMapper.selectById(ownForumid);
			if (forum == null){
				forumName = "未选择";
			}else {
				forumName = forum.getForumName();
			}
		}

//		String speak = "";
//		Integer isSpeak = Integer.parseInt(map.get("speak").toString());
//		if(isSpeak != null){
//			speak = TransTypeUtil.getIsOrNo().get(isSpeak).toString();
//		}
		Object meetObj = map.get("meetStatus");
		if(meetObj != null){
			Integer meetStatus = Integer.parseInt(map.get("meetStatus").toString());
			String meetStatusStr = TransTypeUtil.getMeetStatus().get(meetStatus).toString();
			map.put("meetStatusStr",meetStatusStr);
		}

		Object forumObj = map.get("ownForumid");
		if (forumObj != null){
			map.put("ownForumid",forumObj);
		}


		//是否上传材料
		String wordName = user.getWordName();
		String pptName = user.getPptName();
		if(wordName != null || pptName !=null){
			map.put("material","是");
		}else{
			map.put("material","否");
		}

		String introduction = user.getIntroduction();
		map.put("introduction",introduction);

		map.put("memberName",memberName);
		map.put("unitName",unitName);
		map.put("userPost",userPost);
		map.put("direct",direct);
		map.put("thesisName",thesisName);
		map.put("forumName",forumName);
//		map.put("speak",speak);

	}
}
