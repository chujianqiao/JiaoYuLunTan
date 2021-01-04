package cn.stylefeng.guns.modular.ownForum.wrapper;

import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class OwnForumWrapper extends BaseControllerWrapper {
    private MeetService meetService = SpringContextHolder.getBean(MeetService.class);
    public OwnForumWrapper(Map<String, Object> single) {
        super(single);
    }

    public OwnForumWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public OwnForumWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public OwnForumWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Object meetId = map.get("meetId");
        if (meetId != null){
            map.put("meetName",meetService.getById(Long.parseLong(meetId.toString())).getMeetName());
        }
    }
}
