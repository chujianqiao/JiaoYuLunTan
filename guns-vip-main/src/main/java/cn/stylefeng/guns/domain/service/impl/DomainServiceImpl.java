package cn.stylefeng.guns.domain.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.domain.entity.Domain;
import cn.stylefeng.guns.domain.mapper.DomainMapper;
import cn.stylefeng.guns.domain.model.params.DomainParam;
import cn.stylefeng.guns.domain.model.result.DomainResult;
import  cn.stylefeng.guns.domain.service.DomainService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 论文领域表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-07-06
 */
@Service
public class DomainServiceImpl extends ServiceImpl<DomainMapper, Domain> implements DomainService {

    @Override
    public void add(DomainParam param){
        Domain entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(DomainParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(DomainParam param){
        Domain oldEntity = getOldEntity(param);
        Domain newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public DomainResult findBySpec(DomainParam param){
        return null;
    }

    @Override
    public List<DomainResult> findListBySpec(DomainParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(DomainParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(DomainParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Domain getOldEntity(DomainParam param) {
        return this.getById(getKey(param));
    }

    private Domain getEntity(DomainParam param) {
        Domain entity = new Domain();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
