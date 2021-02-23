package cn.stylefeng.guns.modular.socialLink.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.SocialLinkDict;
import cn.stylefeng.guns.modular.socialLink.entity.SocialLink;
import cn.stylefeng.guns.modular.socialLink.model.params.SocialLinkParam;
import cn.stylefeng.guns.modular.socialLink.service.SocialLinkService;
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


/**
 * 合作方式表控制器
 *
 * @author CHU
 * @Date 2020-07-15 10:57:17
 */
@Controller
@RequestMapping("/socialLink")
public class SocialLinkController extends BaseController {

    private String PREFIX = "/socialLink";

    @Autowired
    private SocialLinkService socialLinkService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/socialLink.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/socialLink_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/socialLink_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SocialLinkParam socialLinkParam) {
        this.socialLinkService.add(socialLinkParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SocialLinkParam socialLinkParam) {
        this.socialLinkService.update(socialLinkParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SocialLinkParam socialLinkParam) {
        this.socialLinkService.delete(socialLinkParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SocialLinkParam socialLinkParam) {
        SocialLink detail = this.socialLinkService.getById(socialLinkParam.getLinkId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SocialLinkParam socialLinkParam) {
        return this.socialLinkService.findPageBySpec(socialLinkParam);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-15
     */
    @ResponseBody
    @RequestMapping("/listAll")
    public LayuiPageInfo listAll(SocialLinkParam socialLinkParam) {
        return this.socialLinkService.findPageBySpecAll(socialLinkParam);
    }

    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结", key = "linkId", dict = SocialLinkDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData freeze(@RequestParam Long linkId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(linkId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.socialLinkService.setStatus(linkId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结", key = "linkId", dict = SocialLinkDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Long linkId) {
        if (cn.stylefeng.roses.core.util.ToolUtil.isEmpty(linkId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.socialLinkService.setStatus(linkId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

}


