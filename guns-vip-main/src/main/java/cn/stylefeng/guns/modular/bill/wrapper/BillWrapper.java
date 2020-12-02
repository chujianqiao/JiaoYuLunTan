package cn.stylefeng.guns.modular.bill.wrapper;

import cn.stylefeng.guns.meet.mapper.MeetMapper;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class BillWrapper extends BaseControllerWrapper {

    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

    private MeetService meetService = SpringContextHolder.getBean(MeetService.class);
    private MeetMemberService meetMemberService = SpringContextHolder.getBean(MeetMemberService.class);

    public BillWrapper(Map<String, Object> single) {
        super(single);
    }

    public BillWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public BillWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public BillWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Long userId = Long.parseLong(map.get("userId").toString());
        String userName = userMapper.getById(userId).getName();

        Long meetMemberId = Long.parseLong(map.get("meetMemberId").toString());
        MeetMember meetMember = meetMemberService.getById(meetMemberId);
        String meetName = meetService.getById(meetMember.getMeetId()).getMeetName();

        map.put("userName",userName);
        map.put("meetName",meetName);
    }
}
