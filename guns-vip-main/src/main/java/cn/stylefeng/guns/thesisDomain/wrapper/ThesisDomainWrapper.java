package cn.stylefeng.guns.thesisDomain.wrapper;

import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.model.result.ReviewMajorResult;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class ThesisDomainWrapper extends BaseControllerWrapper {

    private UserService userService = SpringContextHolder.getBean(UserService.class);

    private ReviewMajorService reviewMajorService = SpringContextHolder.getBean(ReviewMajorService.class);

    public ThesisDomainWrapper(Map<String, Object> single) {
        super(single);
    }

    public ThesisDomainWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ThesisDomainWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ThesisDomainWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        String domainId = map.get("domainId").toString();
        ReviewMajorParam param = new ReviewMajorParam();
        param.setBelongDomain(domainId);
        List<ReviewMajorResult> list = reviewMajorService.findListByDomain(param);
        String belongMajor = "";
        for (int i = 0;i < list.size();i++){
            Long reviewId = list.get(i).getReviewId();
            Map<String, Object> user = userService.getUserInfo(reviewId);
            belongMajor = belongMajor + user.get("name") + ";";
        }
        map.put("belongMajor",belongMajor);
    }
}
