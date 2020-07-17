package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class SocialLinkDict extends AbstractDictMap {

    @Override
    public void init() {
        put("linkId", "环节Id");
        put("linkName", "环节名称");
        put("description", "备注");
        put("status", "状态");
        put("createTime", "创建时间");
        put("sort", "排序");
    }

    @Override
    protected void initBeWrapped() {
    }
}
