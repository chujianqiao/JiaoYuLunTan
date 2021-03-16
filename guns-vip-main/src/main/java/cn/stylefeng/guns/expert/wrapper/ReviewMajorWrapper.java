package cn.stylefeng.guns.expert.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ReviewMajorWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

	private ThesisDomainService thesisDomainService = SpringContextHolder.getBean(ThesisDomainService.class);

	public ReviewMajorWrapper (Map<String, Object> single) {
		super(single);
	}

	public ReviewMajorWrapper (List<Map<String, Object>> multi) {
		super(multi);
	}

	public ReviewMajorWrapper (Page<Map<String, Object>> page) {
		super(page);
	}

	public ReviewMajorWrapper (PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		/*//转换申请状态
		Map applyStatus = TransTypeUtil.getApplyStatus();
		map.put("checkStatus",applyStatus.get(map.get("checkStatus")));*/
		//获取姓名
		long userId = Long.parseLong(map.get("reviewId").toString());
		String reviewName = ConstantFactory.me().getUserNameById(userId);
		map.put("reviewName",reviewName);
		//个人信息
		User user = this.userMapper.selectById(userId);
		if(user != null){
			String unitName = user.getWorkUnit();
			if(unitName != null && unitName != ""){
				map.put("unitName",unitName);
			}
		}
		String userPost = user.getPost();
		if(userPost == null || userPost.equals("")){
			String title = user.getTitle();
			map.put("userPost",title);
		}else {
			map.put("userPost",userPost);
		}
		String direct = user.getDirection();
		if(direct != null && !direct.equals("")){
			map.put("direct",direct);
		}

		//领域
		String belongDomainStr = "";
		if (map.get("belongDomain") == null){
			belongDomainStr = "";
		}else {
			String domains[] = map.get("belongDomain").toString().split(";");
			for (int i = 0;i < domains.length;i++){
				Long domainObj = Long.parseLong(domains[i]);
				ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(domainObj);
				belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
			}
			//Long domainObj = Long.parseLong(map.get("belongDomain").toString());
			//ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(domainObj);
			//belongDomainStr = thesisDomainResult.getDomainName();
			/*String domainObj = map.get("belongDomain").toString();
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
						if (cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult) && cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult.getDomainName())) {
							belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
						}
					}
				}
			}*/
		}


		map.put("belongDomainStr",belongDomainStr);
	}
}
