package cn.stylefeng.guns.modular.checkIn.wrapper;

import cn.stylefeng.guns.modular.forum.model.result.ForumResult;
import cn.stylefeng.guns.modular.forum.service.ForumService;
import cn.stylefeng.guns.sys.modular.system.entity.Role;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.RoleMapper;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class CheckInWrapper extends BaseControllerWrapper {

    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);

    private ForumService forumService = SpringContextHolder.getBean(ForumService.class);

    public CheckInWrapper(Map<String, Object> single) {
        super(single);
    }

    public CheckInWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public CheckInWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public CheckInWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        //用户
        long userId = Long.parseLong(map.get("userId").toString());
        User user = userMapper.selectById(userId);
        String roleIds = user.getRoleId();
        String[] roleId = roleIds.split(",");
        String roleName = "";
        for (int i = 0;i < roleId.length;i++){
            Role role = roleMapper.selectById(Long.parseLong(roleId[i]));
            roleName = roleName + role.getName() + ";";
        }

        int registerStatus = 0;
        int signStatus = 0;
        if (map.get("registerStatus") != null && map.get("registerStatus") != ""){
            registerStatus = (Integer) map.get("registerStatus");
        }
        if (map.get("signStatus") != null && map.get("signStatus") != ""){
            signStatus = (Integer) map.get("signStatus");
        }
        if (registerStatus == 0){
            map.put("registerStatus","未报到");
            map.put("registerTime","-");
        }else {
            map.put("registerStatus","已报到");
        }
        if (signStatus == 0){
            map.put("signStatus","未签到");
            map.put("signTime","-");
        }else {
            map.put("signStatus","已签到");
        }

        //区分大会还是论坛
        int meetOrForum = (Integer) map.get("meetOrForum");
        Long forumId = (Long) map.get("forumId");
        String forumName = "";
        if (meetOrForum == 1){
            ForumResult forumResult = forumService.findById(forumId);
            forumName = forumResult.getForumName();
        }
        map.put("forumName",forumName);
        map.put("roleName",roleName);
        map.put("name",user.getName());
        map.put("post",user.getPost());
        map.put("workUnit",user.getWorkUnit());
        map.put("title",user.getTitle());
        map.put("phone",user.getPhone());
        map.put("email",user.getEmail());

    }
}
