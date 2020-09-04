package cn.stylefeng.guns.thesisDomain.service.impl;

import cn.stylefeng.guns.base.pojo.node.LayuiTreeNode;
import cn.stylefeng.guns.base.pojo.node.TreeviewNode;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.thesisDomain.entity.ThesisDomain;
import cn.stylefeng.guns.thesisDomain.mapper.ThesisDomainMapper;
import cn.stylefeng.guns.thesisDomain.model.params.ThesisDomainParam;
import cn.stylefeng.guns.thesisDomain.model.result.ThesisDomainResult;
import  cn.stylefeng.guns.thesisDomain.service.ThesisDomainService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文领域表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-07-07
 */
@Service
public class ThesisDomainServiceImpl extends ServiceImpl<ThesisDomainMapper, ThesisDomain> implements ThesisDomainService {
    @Resource
    private ThesisDomainMapper thesisDomainMapper;

    @Override
    public void add(ThesisDomainParam param){
        ThesisDomain entity = getEntity(param);
        if (ToolUtil.isOneEmpty(entity, entity.getDomainName())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(entity);
        this.save(entity);
    }

    @Override
    public void delete(ThesisDomainParam param){
        //根据like查询删除所有级联的部门
        List<ThesisDomain> subDomains = thesisDomainMapper.likePids(param.getDomainId());

        for (ThesisDomain temp : subDomains) {
            this.removeById(temp.getDomainId());
        }
        this.removeById(getKey(param));
    }

    @Override
    public void update(ThesisDomainParam param){
        ThesisDomain oldEntity = getOldEntity(param);
        ThesisDomain newEntity = getEntity(param);
        if (ToolUtil.isOneEmpty(newEntity, newEntity.getDomainId(), newEntity.getDomainName()/*, newEntity.getPid()*/)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        ToolUtil.copyProperties(newEntity, oldEntity);

        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(newEntity);
        this.updateById(newEntity);
    }

    @Override
    public ThesisDomainResult findBySpec(ThesisDomainParam param){
        return null;
    }

    @Override
    public List<ThesisDomainResult> findListBySpec(ThesisDomainParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ThesisDomainParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(ThesisDomainParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(ThesisDomainParam param){
        return param.getDomainId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ThesisDomain getOldEntity(ThesisDomainParam param) {
        return this.getById(getKey(param));
    }

    private ThesisDomain getEntity(ThesisDomainParam param) {
        ThesisDomain entity = new ThesisDomain();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    /**
     * 获取ztree的节点列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

    /**
     * 获取layuiTree的节点列表
     *
     * @author fengshuonan
     * @Date 2019-8-27 15:23
     */
    public List<LayuiTreeNode> layuiTree() {
        return this.baseMapper.layuiTree();
    }

    /**
     * 获取ztree的节点列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public List<TreeviewNode> treeviewNodes() {
        return this.baseMapper.treeviewNodes();
    }

    /**
     * 设置部门的父级ids
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:58 PM
     */
    private void deptSetPids(ThesisDomain thesisDomain) {
        if (ToolUtil.isEmpty(thesisDomain.getPid()) || thesisDomain.getPid().equals(0L)) {
            thesisDomain.setPid(0L);
            thesisDomain.setPids("[0],");
        } else {
            Long pid = thesisDomain.getPid();
            ThesisDomain temp = this.getById(pid);
            String pids = temp.getPids();
            thesisDomain.setPid(pid);
            thesisDomain.setPids(pids + "[" + pid + "],");
        }
    }

    @Override
    public ThesisDomainResult findByPid(Long pid){
        return this.baseMapper.findByPid(pid);
    }
}
