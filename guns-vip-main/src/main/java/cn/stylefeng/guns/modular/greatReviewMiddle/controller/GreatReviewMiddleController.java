package cn.stylefeng.guns.modular.greatReviewMiddle.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.greatReviewMiddle.entity.GreatReviewMiddle;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.service.GreatReviewMiddleService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 著作专家中间表控制器
 *
 * @author CHU
 * @Date 2020-08-18 15:57:22
 */
@Controller
@RequestMapping("/greatReviewMiddle")
public class GreatReviewMiddleController extends BaseController {

    private String PREFIX = "/greatReviewMiddle";

    @Autowired
    private GreatReviewMiddleService greatReviewMiddleService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/greatReviewMiddle.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/greatReviewMiddle_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/greatReviewMiddle_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(GreatReviewMiddleParam greatReviewMiddleParam) {
        this.greatReviewMiddleService.add(greatReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(GreatReviewMiddleParam greatReviewMiddleParam) {
        this.greatReviewMiddleService.update(greatReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(GreatReviewMiddleParam greatReviewMiddleParam) {
        this.greatReviewMiddleService.delete(greatReviewMiddleParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(GreatReviewMiddleParam greatReviewMiddleParam) {
        GreatReviewMiddle detail = this.greatReviewMiddleService.getById(greatReviewMiddleParam.getMiddleId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-08-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(GreatReviewMiddleParam greatReviewMiddleParam) {
        return this.greatReviewMiddleService.findPageBySpec(greatReviewMiddleParam);
    }

}


