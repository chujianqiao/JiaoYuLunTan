package cn.stylefeng.guns.thesis.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 拼接字段
 * @author wucy
 */
public class ThesisWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
	private UserService userService = SpringContextHolder.getBean(UserService.class);

	private ThesisDomainService thesisDomainService = SpringContextHolder.getBean(ThesisDomainService.class);

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

		Object passObj = map.get("reviewResult");
		if(passObj != null){
			Map passMap = TransTypeUtil.getIsPass();
			String isPass = passMap.get(passObj).toString();
			map.put("reviewStr",isPass);
		}else {
			map.put("reviewStr","未评审");
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
						belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + ";";
					}
				}
			}
		}

		map.put("belongDomainStr",belongDomainStr);





		map.put("userName",nameBuilder.toString());
		map.put("unitsName",unitsName);

	}
}
