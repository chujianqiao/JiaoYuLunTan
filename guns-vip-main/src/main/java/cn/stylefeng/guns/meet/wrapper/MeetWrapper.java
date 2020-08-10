package cn.stylefeng.guns.meet.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class MeetWrapper extends BaseControllerWrapper {
	public MeetWrapper(Map<String, Object> single) {
		super(single);
	}

	public MeetWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public MeetWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public MeetWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		long regId = Long.parseLong(map.get("regUser").toString());
		//注册人名称
		String regName = ConstantFactory.me().getUserNameById(regId);

		map.put("regName",regName);
	}
}
