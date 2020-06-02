package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

/**
 * 会议注册日志
 * @author
 */
public class MeetMemberDict extends AbstractDictMap {
	@Override
	public void init() {
		put("memberId","数据ID");
		put("userId","参会人员ID");
		put("thesisId","参会论文ID");
		put("thesisTitle","论文题目");
		put("cnKeyword","中文关键词");
	}

	@Override
	protected void initBeWrapped() {

	}
}
