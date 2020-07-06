package cn.stylefeng.guns.domain.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.domain.entity.Domain;
import cn.stylefeng.guns.domain.model.params.DomainParam;
import cn.stylefeng.guns.domain.service.DomainService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 论文领域表控制器
 *
 * @author CHU
 * @Date 2020-07-06 16:56:57
 */
@Controller
@RequestMapping("/domain")
public class DomainController extends BaseController {

    private String PREFIX = "/domain";

    @Autowired
    private DomainService domainService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/domain.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/domain_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/domain_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(DomainParam domainParam) {
        this.domainService.add(domainParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(DomainParam domainParam) {
        this.domainService.update(domainParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(DomainParam domainParam) {
        this.domainService.delete(domainParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(DomainParam domainParam) {
        Domain detail = this.domainService.getById(domainParam.get());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-06
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(DomainParam domainParam) {
        return this.domainService.findPageBySpec(domainParam);
    }

}


