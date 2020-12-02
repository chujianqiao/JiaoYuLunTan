package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class AnswerDict extends AbstractDictMap {

    @Override
    public void init() {
        put("answerId", "自动回复Id");
        put("answerKey", "问题");
        put("answerValue", "答案");
        put("status", "状态");
        put("createTime", "创建时间");
        put("sort", "排序");
    }

    @Override
    protected void initBeWrapped() {
    }
}
