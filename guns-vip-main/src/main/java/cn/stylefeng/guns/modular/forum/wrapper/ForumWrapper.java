package cn.stylefeng.guns.modular.forum.wrapper;

import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ForumWrapper extends BaseControllerWrapper {

    private MeetMemberService meetMemberService = SpringContextHolder.getBean(MeetMemberService.class);

    public ForumWrapper(Map<String, Object> single) {
        super(single);
    }

    public ForumWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ForumWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ForumWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        int status = Integer.parseInt(map.get("status").toString());
        if (status == 0){
            map.put("status", "未发布");
        }else {
            map.put("status", "已发布");
        }

        /*Long forumId = Long.parseLong(map.get("forumId").toString());
        MeetMemberParam param = new MeetMemberParam();
        param.setOwnForumid(forumId);
        List<MeetMemberResult> list = meetMemberService.customList(param);
        map.put("existNum", list.size());*/
    }
}
