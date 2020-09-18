package cn.stylefeng.guns.modular.seat.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class SeatDetailWrapper extends BaseControllerWrapper {
	public SeatDetailWrapper(Map<String, Object> single) {
		super(single);
	}

	public SeatDetailWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public SeatDetailWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public SeatDetailWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		Object userIdObj = map.get("userId");
		if(userIdObj != null){
			Long userId = Long.parseLong(userIdObj.toString());
			String userName = ConstantFactory.me().getUserNameById(userId);
			map.put("userName",userName);
		}

	}
}
