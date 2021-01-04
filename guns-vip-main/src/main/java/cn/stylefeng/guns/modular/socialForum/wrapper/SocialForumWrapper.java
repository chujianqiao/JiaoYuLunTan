package cn.stylefeng.guns.modular.socialForum.wrapper;

import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.mapper.MeetMapper;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class SocialForumWrapper extends BaseControllerWrapper {
    private MeetMapper meetMapper = SpringContextHolder.getBean(MeetMapper.class);

    public SocialForumWrapper(Map<String, Object> single) {
        super(single);
    }

    public SocialForumWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public SocialForumWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public SocialForumWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {

        Object meetIdObj = map.get("meetId");
        if (meetIdObj != null){
            Long meetId = Long.parseLong(meetIdObj.toString());
            Meet meet = meetMapper.selectById(meetId);
            map.put("meetName",meet.getMeetName());
        }

    }
}
