package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

/**
 * 理事单位申请
 * @author
 */
public class ReviewUnitDict extends AbstractDictMap {
	@Override
	public void init() {
		put("reviewId","单位ID");
		put("location","单位所在地");
		put("year","担任理事单位年份");
		put("post","代表职称/职务");
		put("repName","代表姓名");
		put("education","学历");
		put("createTime","导入时间");
	}

	@Override
	protected void initBeWrapped() {

	}
}
