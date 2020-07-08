package cn.stylefeng.guns.thesisDomain.service;

import cn.stylefeng.guns.base.pojo.node.LayuiTreeNode;
import cn.stylefeng.guns.base.pojo.node.TreeviewNode;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesisDomain.entity.ThesisDomain;
import cn.stylefeng.guns.thesisDomain.model.params.ThesisDomainParam;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论文领域表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-07-07
 */
public interface ThesisDomainService extends IService<ThesisDomain> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-07-07
     */
    void add(ThesisDomainParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-07-07
     */
    void delete(ThesisDomainParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-07-07
     */
    void update(ThesisDomainParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-07
     */
    ThesisDomainResult findBySpec(ThesisDomainParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-07-07
     */
    List<ThesisDomainResult> findListBySpec(ThesisDomainParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-07
     */
     LayuiPageInfo findPageBySpec(ThesisDomainParam param);

    /**
     * 获取ztree的节点列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    List<ZTreeNode> tree();

    /**
     * 获取layuiTree的节点列表
     *
     * @author fengshuonan
     * @Date 2019-8-27 15:23
     */
    List<LayuiTreeNode> layuiTree();

    /**
     * 获取ztree的节点列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    List<TreeviewNode> treeviewNodes();


    /**
     * 根据父id查询单条数据
     *
     * @author CHU
     * @Date 2020-07-07
     */
    ThesisDomainResult findByPid(Long pid);

}
