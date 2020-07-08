package cn.stylefeng.guns.core.constant.dictmap;

import cn.stylefeng.guns.base.dict.AbstractDictMap;

public class ThesisDomainDict extends AbstractDictMap {
    @Override
    public void init() {
        put("domainId", "领域ID");
        put("domainName", "领域名称");
        put("pid", "父领域ID");
        put("pids", "父级ids");
        put("description", "描述");
        put("createTime", "创建时间");
        put("updateTime", "修改时间");
    }

    @Override
    protected void initBeWrapped() {
    }
}
