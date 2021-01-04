package cn.stylefeng.guns.thesis.wrapper;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;

/**
 * 拼接字段
 * @author wucy
 */
public class ThesisWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private UserService userService = SpringContextHolder.getBean(UserService.class);
	private MeetService meetService = SpringContextHolder.getBean(MeetService.class);

	private ThesisDomainService thesisDomainService = SpringContextHolder.getBean(ThesisDomainService.class);
	private ThesisReviewMiddleService thesisReviewMiddleService = SpringContextHolder.getBean(ThesisReviewMiddleService.class);

	public ThesisWrapper(Map<String, Object> single) {
		super(single);
	}

	public ThesisWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public ThesisWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public ThesisWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		String userIds = map.get("thesisUser").toString();
		String []users = userIds.split(",");
		StringBuilder nameBuilder = new StringBuilder();
		List<String> unitList = new ArrayList<>();

		for (int i = 0;i < users.length;i++){
			User user = userService.getById(Long.parseLong(users[i]));
			nameBuilder.append(user.getName());
			if(i != users.length - 1){
				nameBuilder.append(",");
			}

			String unitName = user.getWorkUnit();
			if(!unitList.contains(unitName)){
				unitList.add(unitName);
			}
		}

		String unitsName = Arrays.toString(unitList.toArray()).replace("[","").replace("]","");

		Object statusObj = map.get("status");
		if(statusObj != null){
			if (!statusObj.toString().equals("")){
				map.put("status",statusObj.toString());
			}else {
				Object passObj = map.get("reviewResult");
				if(passObj != null){
					Map passMap = TransTypeUtil.getIsPass();
					String isPass = passMap.get(passObj).toString();
					map.put("status",isPass);
				}else {
					map.put("status","未评审");
				}
			}
		}else {
			Object passObj = map.get("reviewResult");
			if(passObj != null){
				Map passMap = TransTypeUtil.getIsPass();
				String isPass = passMap.get(passObj).toString();
				map.put("status",isPass);
			}else {
				map.put("status","未评审");
			}
		}

		Object greatObj = map.get("great");
		if(greatObj != null){
			String greatStr = TransTypeUtil.getIsOrNo().get(greatObj).toString();
			map.put("greatStr",greatStr);
		}

		String domainObj = map.get("belongDomain").toString();
		String belongDomainStr = "";
		if (domainObj.equals("") || domainObj == null){
			belongDomainStr = "";
		}else {
			String[] domainList = domainObj.split(",");
			for (int i = 0;i < domainList.length;i++){
				Long pid = Long.parseLong(domainList[i]);
				if (pid == null) {
					belongDomainStr = belongDomainStr + "";
				} else if (pid == 0L) {
					belongDomainStr = belongDomainStr + "顶级;";
				} else {
					ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(pid);
					if (ToolUtil.isNotEmpty(thesisDomainResult) && ToolUtil.isNotEmpty(thesisDomainResult.getDomainName())) {
						belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + "";
					}
				}
			}
		}

		//评审专家
		ThesisReviewMiddleParam param = new ThesisReviewMiddleParam();
		param.setThesisId(Long.parseLong(map.get("thesisId").toString()));
		List<ThesisReviewMiddleResult> middleList = thesisReviewMiddleService.findListBySpec(param);
		String firstName = "";
		String againName = "";
		if (middleList != null){
            for (int i = 0;i < middleList.size();i++){
                User user = userService.getById(middleList.get(i).getUserId());
                String name = user.getName();
                if (middleList.get(i).getReviewSort() == 1){
					if (middleList.get(i).getScore() != null){
						int score = middleList.get(i).getScore();
						firstName = name + "(" + score + ")";
					}else {
						firstName = name + "(未评审)";
					}
                }else {
					if (middleList.get(i).getScore() != null){
						int score = middleList.get(i).getScore();
						againName = againName + name + "(" + score + ");";
					}else {
						againName = againName + name + "(未评审);";
					}
                }
            }
        }

        //初评分数
		ThesisReviewMiddleParam middleParam = new ThesisReviewMiddleParam();
		long thesisId = Long.parseLong(map.get("thesisId").toString());
		LoginUser user = LoginContextHolder.getContext().getUser();
		middleParam.setThesisId(thesisId);
		middleParam.setUserId(user.getId());
		middleParam.setReviewSort(1);
		List<ThesisReviewMiddleResult> midRes = this.thesisReviewMiddleService.findListBySpec(middleParam);
		//List<ThesisReviewMiddleResult> midList = midRes.getData();
		if(midRes.size() != 0){
			ThesisReviewMiddleResult middleResult = midRes.get(0);
			Integer firstScore = middleResult.getScore();
			if(firstScore != null){
				map.put("firstScore",firstScore);
			}
			Date date = middleResult.getReviewTime();
			if(date != null && date.getTime() != 0){
				map.put("firstStatus","已评审");
			}else {
				map.put("firstStatus","未评审");
			}
		}

		//复评状态
		middleParam.setReviewSort(2);
		List<ThesisReviewMiddleResult> midResAgain = this.thesisReviewMiddleService.findListBySpec(middleParam);
		//List<ThesisReviewMiddleResult> midListAgain = midResAgain.getData();
		if(midResAgain.size() != 0){
			ThesisReviewMiddleResult middleResult = midResAgain.get(0);
			if (middleResult != null){
				Date date = middleResult.getReviewTime();
				if(date != null && date.getTime() != 0 && middleResult.getScore() != null){
					map.put("secondStatus","已评审");
					Integer secondScore = middleResult.getScore();
					map.put("secondScore",secondScore);
				}else {
					map.put("secondStatus","未评审");
				}
			}

		}

		//复评分数
		middleParam = new ThesisReviewMiddleParam();
		middleParam.setThesisId(thesisId);
		middleParam.setReviewSort(2);
		midRes = this.thesisReviewMiddleService.findListBySpec(middleParam);
		//midList = midRes.getData();
		if(midRes.size() > 0){
			StringBuilder scoreStr = new StringBuilder();
			for (int i = 0; i < midRes.size(); i++) {
				ThesisReviewMiddleResult middleResult = midRes.get(i);
				Integer score = middleResult.getScore();
				if(score != null){
					scoreStr.append(score + "; ");
				}
			}
			map.put("scoreStr",scoreStr);
		}

		if (firstName.equals("")){
			map.put("status","未分配");
		}
		map.put("firstName",firstName);
		map.put("againName",againName);
		map.put("belongDomainStr",belongDomainStr);
		map.put("userName",nameBuilder.toString());
		map.put("unitsName",unitsName);
		Object meetId = map.get("meetId");
		if (meetId != null){
			map.put("meetName",meetService.getById(Long.parseLong(meetId.toString())).getMeetName());
		}
	}
}
