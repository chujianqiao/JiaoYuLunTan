package cn.stylefeng.guns.modular.answer.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.AnswerDict;
import cn.stylefeng.guns.modular.answer.entity.Answer;
import cn.stylefeng.guns.modular.answer.model.params.AnswerParam;
import cn.stylefeng.guns.modular.answer.service.AnswerService;
import cn.stylefeng.guns.modular.weixin.message.resp.TextMessage;
import cn.stylefeng.guns.modular.weixin.util.MessageUtil;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;


/**
 * 自动回复表控制器
 *
 * @author CHU
 * @Date 2020-11-09 15:11:59
 */
@Controller
@RequestMapping("/answer")
public class AnswerController extends BaseController {

    private String PREFIX = "/answer";

    @Autowired
    private AnswerService answerService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/answer.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/answer_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/answer_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(AnswerParam answerParam) {
        this.answerService.add(answerParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(AnswerParam answerParam) {
        this.answerService.update(answerParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(AnswerParam answerParam) {
        this.answerService.delete(answerParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(AnswerParam answerParam) {
        Answer detail = this.answerService.getById(answerParam.getAnswerId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-11-09
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(AnswerParam answerParam) {
        return this.answerService.findPageBySpec(answerParam);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/listAll")
    public LayuiPageInfo listAll(AnswerParam answerParam) {
        return this.answerService.findPageBySpecAll(answerParam);
    }

    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结", key = "answerId", dict = AnswerDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData freeze(@RequestParam Long answerId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(answerId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.answerService.setStatus(answerId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结", key = "answerId", dict = AnswerDict.class)
    //@Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long answerId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(answerId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.answerService.setStatus(answerId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }
}


