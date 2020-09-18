package cn.stylefeng.guns.thesisReviewMiddle.wrapper;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.guns.util.TransTypeUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;

public class ThesisReviewMiddleWrapper extends BaseControllerWrapper {
    private UserService userService = SpringContextHolder.getBean(UserService.class);

    private ThesisDomainService thesisDomainService = SpringContextHolder.getBean(ThesisDomainService.class);
    private ThesisReviewMiddleService thesisReviewMiddleService = SpringContextHolder.getBean(ThesisReviewMiddleService.class);

    public ThesisReviewMiddleWrapper(Map<String, Object> single) {
        super(single);
    }

    public ThesisReviewMiddleWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ThesisReviewMiddleWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ThesisReviewMiddleWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {



    }
}
