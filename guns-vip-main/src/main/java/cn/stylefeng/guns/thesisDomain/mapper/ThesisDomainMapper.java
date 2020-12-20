package cn.stylefeng.guns.thesisDomain.mapper;

import cn.stylefeng.guns.base.pojo.node.LayuiTreeNode;
import cn.stylefeng.guns.base.pojo.node.TreeviewNode;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.thesisDomain.entity.ThesisDomain;
import cn.stylefeng.guns.thesisDomain.model.params.ThesisDomainParam;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文领域表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-07-07
 */
public interface ThesisDomainMapper extends BaseMapper<ThesisDomain> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    List<ThesisDomainResult> customList(@Param("paramCondition") ThesisDomainParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ThesisDomainParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    Page<ThesisDomainResult> customPageList(@Param("page") Page page, @Param("paramCondition") ThesisDomainParam paramCondition);
    Page<ThesisDomainResult> customPageListById(@Param("page") Page page, @Param("domainIds") List<String> domainIds);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-07
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ThesisDomainParam paramCondition);


    /**
     * 获取所有部门列表
     */
    Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition, @Param("domainId") Long domainId);


    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取layui树形节点
     */
    List<LayuiTreeNode> layuiTree();

    /**
     * 获取所有领域树列表
     */
    List<TreeviewNode> treeviewNodes();

    /**
     * where pids like ''
     */
    List<ThesisDomain> likePids(@Param("domainId") Long domainId);

    ThesisDomainResult findByPid(@Param("pid") Long pid);
}
