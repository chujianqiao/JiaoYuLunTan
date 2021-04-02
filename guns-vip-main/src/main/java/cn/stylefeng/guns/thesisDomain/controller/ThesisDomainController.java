package cn.stylefeng.guns.thesisDomain.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.node.LayuiTreeNode;
import cn.stylefeng.guns.base.pojo.node.TreeviewNode;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.dictmap.ThesisDomainDict;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.model.result.ReviewMajorResult;
import cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.factory.LayuiTreeFactory;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.sys.modular.system.warpper.DeptTreeWrapper;
import cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.guns.thesisDomain.entity.ThesisDomain;
import cn.stylefeng.guns.thesisDomain.model.ThesisDomainDto;
import cn.stylefeng.guns.thesisDomain.model.params.ThesisDomainParam;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.guns.thesisDomain.wrapper.ThesisDomainWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.treebuild.DefaultTreeBuildFactory;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 * 论文领域表控制器
 *
 * @author CHU
 * @Date 2020-07-07 11:36:49
 */
@Controller
@RequestMapping("/thesisDomain")
public class ThesisDomainController extends BaseController {

    private String PREFIX = "/thesisDomain";

    @Autowired
    private ThesisDomainService thesisDomainService;

    @Autowired
    private ReviewMajorService reviewMajorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThesisService thesisService;

    /**
     * 跳转到主页面
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/thesisDomain.html";
    }

    /**
     * 新增页面
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/thesisDomain_add.html";
    }

    /**
     * 编辑页面
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/edit")
    public String edit(@RequestParam Long domainId) {
        ThesisDomain thesisDomain = thesisDomainService.getById(domainId);
        LogObjectHolder.me().set(thesisDomain);
        return PREFIX + "/thesisDomain_edit.html";
    }

    /**
     * 新增接口
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/addItem")
    @BussinessLog(value = "添加部门", key = "simpleName", dict = ThesisDomainDict.class)
    @ResponseBody
    public ResponseData addItem(ThesisDomainParam thesisDomainParam) {
        this.thesisDomainService.add(thesisDomainParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ThesisDomainParam thesisDomainParam) {
        this.thesisDomainService.update(thesisDomainParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ThesisDomainParam thesisDomainParam) {
        this.thesisDomainService.delete(thesisDomainParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ThesisDomainParam thesisDomainParam) {
        ThesisDomain detail = this.thesisDomainService.getById(thesisDomainParam.getDomainId());
        ThesisDomainDto thesisDomainDto = new ThesisDomainDto();
        BeanUtil.copyProperties(detail, thesisDomainDto);

        Long pid = thesisDomainDto.getPid();
        String PName = "";

        if (pid == null) {
            return ResponseData.error("父领域为空");
        } else if (pid == 0L) {
            PName = "顶级";
        } else {
            ThesisDomainResult thesisDomainResult = thesisDomainService.findByPid(pid);
            if (ToolUtil.isNotEmpty(thesisDomainResult) && ToolUtil.isNotEmpty(thesisDomainResult.getDomainName())) {
                PName = thesisDomainResult.getDomainName();
            }
        }

        thesisDomainDto.setPName(PName);

        String domainId = detail.getDomainId().toString();
        ReviewMajorParam param = new ReviewMajorParam();
        param.setBelongDomain(domainId);
        List<ReviewMajorResult> list = reviewMajorService.findListByDomain(param);
        String belongMajor = "";
        for (int i = 0;i < list.size();i++){
            Long reviewId = list.get(i).getReviewId();
            Map<String, Object> user = userService.getUserInfo(reviewId);
            belongMajor = belongMajor + user.get("name") + ";";
        }
        thesisDomainDto.setBelongMajor(belongMajor);
        return ResponseData.success(thesisDomainDto);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ThesisDomainParam thesisDomainParam) {
        return this.thesisDomainService.findPageBySpec(thesisDomainParam);
    }

    /**
     * 查询列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    @ResponseBody
    @RequestMapping("/existList")
    public LayuiPageInfo existList(ThesisDomainParam thesisDomainParam) {
        List<String> list = this.thesisService.findExistList();
        if (list.size() > 0){
            return this.thesisDomainService.findPageById(list);
        }else {
            return null;
        }

    }

    /**
     * 查询列表（拼接字段）
     * @author wucy
     * @Date 2020-05-21
     */
    @ResponseBody
    @RequestMapping("/wrapList")
    public Object wrapList(ThesisDomainParam thesisDomainParam) {
        Page<Map<String, Object>> domains = this.thesisDomainService.findPageWrap(thesisDomainParam);
        Page wrapped = new ThesisDomainWrapper(domains).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }


    /**
     * 获取领域的tree列表，layuiTree格式
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping(value = "/layuiTree")
    @ResponseBody
    public List<LayuiTreeNode> layuiTree() {

        List<LayuiTreeNode> list = this.thesisDomainService.layuiTree();
        list.add(LayuiTreeFactory.createRoot());

        DefaultTreeBuildFactory<LayuiTreeNode> treeBuildFactory = new DefaultTreeBuildFactory<>();
        treeBuildFactory.setRootParentId("-1");
        return treeBuildFactory.doTreeBuild(list);
    }

    /**
     * 获取领域的tree列表，ztree格式
     *
     * @author fengshuonan
     * @Date 2019-8-27 15:24
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree(Long reviewId) {
        List<ZTreeNode> tree = this.thesisDomainService.tree();

        if (reviewId != null){
            ReviewMajor reviewMajor = reviewMajorService.getById(reviewId);
            if (reviewMajor.getBelongDomain() != null && reviewMajor.getBelongDomain() != null){
                String domains[] = reviewMajor.getBelongDomain().split(";");
                for (int i = 0;i < tree.size();i++){
                    for (int j = 0;j < domains.length;j++){
                        String treeId = tree.get(i).getId().toString();
                        String domain = domains[j];
                        if (treeId.equals(domain)){
                            tree.get(i).setChecked(true);
                        }
                    }
                }
            }

        }

        //tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 获取领域的tree列表，treeview格式
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/treeview")
    @ResponseBody
    public List<TreeviewNode> treeview() {
        List<TreeviewNode> treeviewNodes = this.thesisDomainService.treeviewNodes();

        //构建树
        DefaultTreeBuildFactory<TreeviewNode> factory = new DefaultTreeBuildFactory<>();
        factory.setRootParentId("0");
        List<TreeviewNode> results = factory.doTreeBuild(treeviewNodes);

        //把子节点为空的设为null
        DeptTreeWrapper.clearNull(results);

        return results;
    }


    /**
     * 通用的树列表选择器
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/thesisDomainAssign")
    public String thesisDomainAssign(@RequestParam("formName") String formName,
                               @RequestParam("formId") String formId,
                               @RequestParam("treeUrl") String treeUrl, Model model) {

        if (ToolUtil.isOneEmpty(formName, formId, treeUrl)) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        try {
            model.addAttribute("formName", URLDecoder.decode(formName, "UTF-8"));
            model.addAttribute("formId", URLDecoder.decode(formId, "UTF-8"));
            model.addAttribute("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        return PREFIX + "/thesisDomain_assign.html";
    }

}


