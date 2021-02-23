package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class OwnForumDict extends AbstractDictMap {

    @Override
    public void init() {
        put("forumId", "论坛Id");
        put("forumName", "论坛名称");
        put("title", "职称");
        put("post", "职务");
        put("unitName", "单位名称");
        put("manager", "姓名");
        put("manaPhone", "手机号");
        put("manaEmail", "邮箱");
        put("direction", "研究方向");
        put("forumTopic", "论坛主题名称");
        put("forumSize", "论坛计划规模");
        put("issubject", "是否有课题团队");
        put("subjectLev", "课题级别");
        put("subjectName", "课题名称");
        put("planName", "论坛申报方案附件");
    }

    @Override
    protected void initBeWrapped() {
    }
}
