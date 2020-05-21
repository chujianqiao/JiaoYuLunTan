package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class HoldForumDict extends AbstractDictMap {

    @Override
    public void init() {
        put("forumId", "论坛Id");
        put("forumName", "论坛名称");
        put("unitName", "单位名称");
        put("year", "承办年份");
        put("ability", "承办能力(会议规模)");
        put("manager", "负责人");
        put("manaPhone", "负责人电话");
        put("manaEmail", "负责人邮箱");
        put("planName", "承办方案附件");
    }

    @Override
    protected void initBeWrapped() {
    }

}
