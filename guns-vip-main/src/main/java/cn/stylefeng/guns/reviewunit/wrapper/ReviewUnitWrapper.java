package cn.stylefeng.guns.reviewunit.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ReviewUnitWrapper extends BaseControllerWrapper {
	public ReviewUnitWrapper (Map<String, Object> single) {
		super(single);
	}

	public ReviewUnitWrapper (List<Map<String, Object>> multi) {
		super(multi);
	}

	public ReviewUnitWrapper (Page<Map<String, Object>> page) {
		super(page);
	}

	public ReviewUnitWrapper (PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		//获取单位名称
		long unitId = Long.parseLong(map.get("reviewId").toString());
		String reviewName = ConstantFactory.me().getUserNameById(unitId);
		map.put("reviewName",reviewName);
	}
}
