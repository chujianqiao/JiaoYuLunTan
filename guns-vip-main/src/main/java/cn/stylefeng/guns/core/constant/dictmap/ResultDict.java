package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class ResultDict extends AbstractDictMap {

    @Override
    public void init() {
        put("resultId", "成果Id");
        put("resultName", "成果名称");
        put("belongName", "姓名/单位名称");
        put("manager", "负责人姓名");
        put("manaPhone", "电话");
        put("manaEmail", "邮箱");
        put("team", "所在单位");
        put("manaPost", "申请人职称/职务");
        put("manaDirect", "申请人研究方向");
        put("keyword", "关键词");
        put("introduceName", "优秀成果简介附件");
        put("letterName", "专家推荐信附件");
        put("commitName", "原创承诺书");
    }

    @Override
    protected void initBeWrapped() {
    }
}
