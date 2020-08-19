package cn.stylefeng.guns.modular.educationResult.wrapper;

import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import cn.stylefeng.guns.modular.educationReviewMiddle.service.EducationReviewMiddleService;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public class EducationResultWrapper extends BaseControllerWrapper {
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private UserService userService = SpringContextHolder.getBean(UserService.class);
    private EducationReviewMiddleService educationReviewMiddleService = SpringContextHolder.getBean(EducationReviewMiddleService.class);


    public EducationResultWrapper(Map<String, Object> single) {
        super(single);
    }

    public EducationResultWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public EducationResultWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public EducationResultWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
//评审专家
        EducationReviewMiddleParam param = new EducationReviewMiddleParam();
        param.setResultId(Long.parseLong(map.get("resultId").toString()));
        List<EducationReviewMiddleResult> middleList = educationReviewMiddleService.findListBySpec(param);
        String reviewName = "无";
        int score = 0;
        int reviewResult = 3;
        if (middleList != null){
            for (int i = 0;i < middleList.size();i++){
                User user = userService.getById(middleList.get(i).getUserId());
                reviewName = user.getName();
                if (middleList.get(i).getReviewResult()!=null) {
                    reviewResult = middleList.get(i).getReviewResult();
                }
                if (middleList.get(i).getScore() != null){
                    score = middleList.get(i).getScore();
                }
            }
        }
        map.put("reviewName",reviewName);
        if (score == 0){
            map.put("score","-");
        }else {
            map.put("score",score);
        }
        if (reviewResult == 2){
            map.put("reviewResult","未评审");
        } else if (reviewResult == 0){
            map.put("reviewResult","不推荐参会");
        } else if (reviewResult == 1){
            map.put("reviewResult","推荐参会");
        } else {
            map.put("reviewResult","未评审");
        }

    }
}
