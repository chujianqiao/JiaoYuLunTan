package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

/**
 * 专家申请
 * @author
 */
public class ReviewMajorDict extends AbstractDictMap {
	@Override
	public void init() {
		put("reviewId","专家ID");
		put("direct","研究方向和专长");
		put("majorType","专家分类");
		put("applyFrom","来源");
		put("checkStatus","申报状态");
		put("applyTime","提交申请时间");
		put("refuseTime","驳回时间");
		put("agreeTime","通过时间");
		put("cancelTime","取消时间");
	}

	@Override
	protected void initBeWrapped() {

	}
}
