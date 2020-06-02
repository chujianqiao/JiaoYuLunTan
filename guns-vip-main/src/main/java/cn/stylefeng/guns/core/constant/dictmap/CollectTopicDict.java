package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class CollectTopicDict extends AbstractDictMap {

	@Override
	public void init() {
		put("topicId","主题ID");
		put("createUser","创建人ID");
		put("topicName","大会主题");
		put("topicDesc","选题意义");
	}

	@Override
	protected void initBeWrapped() {

	}
}
