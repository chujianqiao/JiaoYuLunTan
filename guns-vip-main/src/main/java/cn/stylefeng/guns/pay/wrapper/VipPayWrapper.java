package cn.stylefeng.guns.pay.wrapper;

import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class VipPayWrapper extends BaseControllerWrapper {

	private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

	public VipPayWrapper(Map<String, Object> single) {
		super(single);
	}

	public VipPayWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public VipPayWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public VipPayWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		Long userId = Long.parseLong(map.get("payUser").toString());
		User user = userMapper.getById(userId);
		map.put("payUserName",user.getName());
		String payTypeStr = TransTypeUtil.getPayType().get(map.get("payType")).toString();
		map.put("payTypeStr",payTypeStr);
	}
}
