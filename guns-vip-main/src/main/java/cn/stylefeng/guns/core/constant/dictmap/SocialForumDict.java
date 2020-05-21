package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class SocialForumDict extends AbstractDictMap {

    @Override
    public void init() {
        put("forumId", "论坛Id");
        put("forumName", "论坛名称");
        put("unitName", "申报企业/单位名称");
        put("unitPlace", "企业/单位所在地");
        put("manager", "负责人");
        put("manaPhone", "负责人电话");
        put("manaEmail", "负责人邮箱");
        put("alreadyMeet", "已资助的会议");
        put("supPlate", "拟资助版块");
        put("supMoney", "资助金额");
        put("contractName", "合同条件");
    }

    @Override
    protected void initBeWrapped() {
    }
}
