package cn.stylefeng.guns.modular.educationResult.wrapper;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.service.MeetService;
import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import cn.stylefeng.guns.modular.educationReviewMiddle.service.EducationReviewMiddleService;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.util.ToolUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EducationResultWrapper extends BaseControllerWrapper {
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private UserService userService = SpringContextHolder.getBean(UserService.class);
    private MeetService meetService = SpringContextHolder.getBean(MeetService.class);
    private EducationReviewMiddleService educationReviewMiddleService = SpringContextHolder.getBean(EducationReviewMiddleService.class);
    private ThesisDomainService thesisDomainService = SpringContextHolder.getBean(ThesisDomainService.class);

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
        int checkStatus = -1;
        if (map.get("checkStatus") != null){
            checkStatus = Integer.parseInt(map.get("checkStatus").toString());
        }


        if (reviewResult == 2){
            map.put("reviewResult","未评审");
        } else if (reviewResult == 0){
            if (checkStatus == 2){
                map.put("reviewResult","推荐参会");
            }else {
                map.put("reviewResult","不推荐参会");
            }
        } else if (reviewResult == 1){
            if (checkStatus == 3){
                map.put("reviewResult","不推荐参会");
            }else {
                map.put("reviewResult","推荐参会");
            }
        } else {
            if (checkStatus == 2){
                map.put("reviewResult","推荐参会");
            }else if (checkStatus == 3){
                map.put("reviewResult","不推荐参会");
            }else {
                map.put("reviewResult","未分配");
            }

        }

        if (checkStatus == 0){
            map.put("reviewResult","取消申请");
        }

        EducationReviewMiddleParam middleParam = new EducationReviewMiddleParam();
        LoginUser user = LoginContextHolder.getContext().getUser();
        middleParam.setUserId(user.getId());
        middleParam.setResultId(Long.parseLong(map.get("resultId").toString()));
        List<EducationReviewMiddleResult> records = this.educationReviewMiddleService.findListBySpec(middleParam);
        if(records.size() != 0){
            EducationReviewMiddleResult result = records.get(0);
            Date date = result.getReviewTime();
            if(date == null || date.getTime() == 0L){
                map.put("reviewStatus","未评审");
            }
        }

        Object meetId = map.get("meetId");
        if (meetId != null){
            map.put("meetName",meetService.getById(Long.parseLong(meetId.toString())).getMeetName());
        }

        boolean isAdmin = ToolUtil.isAdminRole();
        boolean isReviewRole = ToolUtil.isReviewRole();
        if (!isAdmin && !isReviewRole){
            if (map.get("finalResult") != null){
                int finalResult = Integer.parseInt(map.get("finalResult").toString());
                if (finalResult != 2){
                    map.put("score","-");
                    map.put("reviewResult","未评审");
                }
            }
        }
        String domainObj = map.get("belongDomain").toString();
        String belongDomainStr = "";
        if (domainObj.equals("") || domainObj == null){
            belongDomainStr = "";
        }else {
            String[] domainList = domainObj.split(",");
            for (int i = 0;i < domainList.length;i++){
                Long pid = Long.parseLong(domainList[i]);
                if (pid == null) {
                    belongDomainStr = belongDomainStr + "";
                } else if (pid == 0L) {
                    belongDomainStr = belongDomainStr + "顶级;";
                } else {
                    ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(pid);
                    if (cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult) && cn.stylefeng.roses.core.util.ToolUtil.isNotEmpty(thesisDomainResult.getDomainName())) {
                        belongDomainStr = belongDomainStr + thesisDomainResult.getDomainName() + "";
                    }
                }
            }
        }
        map.put("belongDomainStr",belongDomainStr);
    }
}
