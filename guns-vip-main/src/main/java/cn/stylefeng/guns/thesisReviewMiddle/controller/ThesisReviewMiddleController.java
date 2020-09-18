package cn.stylefeng.guns.thesisReviewMiddle.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.thesis.wrapper.ThesisWrapper;
import cn.stylefeng.guns.thesisReviewMiddle.entity.ThesisReviewMiddle;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.guns.thesisReviewMiddle.wrapper.ThesisReviewMiddleWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 论文专家中间表控制器
 *
 * @author CHU
 * @Date 2020-08-05 16:20:10
 */
@Controller
@RequestMapping("/thesisReviewMiddle")
public class ThesisReviewMiddleController extends BaseController {

    private String PREFIX = "/thesisReviewMiddle";

    @Autowired
    private ThesisReviewMiddleService thesisReviewMiddleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThesisService thesisService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/thesisReviewMiddle.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/thesisReviewMiddle_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/thesisReviewMiddle_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.add(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.update(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        this.thesisReviewMiddleService.delete(thesisReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        ThesisReviewMiddle detail = this.thesisReviewMiddleService.getById(thesisReviewMiddleParam.getMiddleId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-08-05
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        return this.thesisReviewMiddleService.findPageBySpec(thesisReviewMiddleParam);
    }


    @RequestMapping("/toThesisResult")
    public String toThesisResult(){
        return "/thesis/thesis_result.html";
    }


    @RequestMapping("/thesisResult")
    @ResponseBody
    public ResponseData thesisResult(ThesisReviewMiddleParam thesisReviewMiddleParam) {
        Thesis thesis = thesisService.getById(thesisReviewMiddleParam.getThesisId());

        //类转Map
        Map map = JSON.parseObject(JSON.toJSONString(thesis), Map.class);

        List<ThesisReviewMiddleResult> theses = this.thesisReviewMiddleService.getByThesisId(thesisReviewMiddleParam.getThesisId(),1);
        String userNameFirst = "";

        for (int i = 0;i < theses.size();i++){
            Long userId = theses.get(i).getUserId();
            User user = userService.getById(userId);

            if (theses.get(i).getScore() == null){
                userNameFirst = userNameFirst + user.getName() + "(未评审)" + ";";
            }else {
                userNameFirst = userNameFirst + user.getName() + "(" + theses.get(i).getScore() + ")" + ";";
            }
        }
        if (theses.size() == 0){
            map.put("userNameFirst", "未分配");
        }else {
            map.put("userNameFirst", userNameFirst);
        }

        List<ThesisReviewMiddleResult> thesesSecond = this.thesisReviewMiddleService.getByThesisId(thesisReviewMiddleParam.getThesisId(),2);
        String userNameSecond = "";

        for (int i = 0;i < thesesSecond.size();i++){
            Long userId = thesesSecond.get(i).getUserId();
            User user = userService.getById(userId);

            if (thesesSecond.get(i).getScore() == null){
                userNameSecond = userNameSecond + user.getName() + "(未评审)" + ";";
            }else {
                userNameSecond = userNameSecond + user.getName() + "(" + thesesSecond.get(i).getScore() + ")" + ";";
            }
        }
        if (thesesSecond.size() == 0){
            map.put("userNameSecond", "未分配");
        }else {
            map.put("userNameSecond", userNameSecond);
        }

        if (thesis.getStatus() == "" || thesis.getStatus() == null){
            map.put("status", "未评审");
        }
        if (thesis.getIsgreat() == null){
            map.put("isgreat", "未评审");
        }else {
            if (thesis.getIsgreat() == 0){
                map.put("isgreat", "不推优");
            }else if (thesis.getIsgreat() == 1){
                map.put("isgreat", "推优");
            }
        }
        if (thesis.getReviewResult() == null){
            map.put("reviewResult", "未评审");
        }else {
            if (thesis.getReviewResult() == 0){
                map.put("reviewResult", "不同意参会");
            }else if (thesis.getReviewResult() == 1){
                map.put("reviewResult", "同意参会");
            }
        }

        return ResponseData.success(map);
    }
}


