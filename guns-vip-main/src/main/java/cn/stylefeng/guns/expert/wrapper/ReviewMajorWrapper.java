package cn.stylefeng.guns.expert.wrapper;

import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ReviewMajorWrapper extends BaseControllerWrapper {

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
		//转换申请状态
		Map applyStatus = TransTypeUtil.getApplyStatus();
		map.put("checkStatus",applyStatus.get(map.get("checkStatus")));
		//获取姓名
		long userId = Long.parseLong(map.get("reviewId").toString());
		String reviewName = ConstantFactory.me().getUserNameById(userId);
		map.put("reviewName",reviewName);
	}
}
