package cn.stylefeng.guns.thesis.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesis.entity.Thesis;
import cn.stylefeng.guns.thesis.mapper.ThesisMapper;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.model.result.ThesisResult;
import  cn.stylefeng.guns.thesis.service.ThesisService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-05-21
 */
@Service
public class ThesisServiceImpl extends ServiceImpl<ThesisMapper, Thesis> implements ThesisService {

    @Override
    public void add(ThesisParam param){
        Thesis entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ThesisParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ThesisParam param){
        Thesis oldEntity = getOldEntity(param);
        Thesis newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ThesisResult findBySpec(ThesisParam param){
        return null;
    }

    @Override
    public List<ThesisResult> findListBySpec(ThesisParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ThesisParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(ThesisParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    @Override
    public Page<Map<String, Object>> findPageWrapByBatch(ThesisParam param, Integer batch) {
        Page page = LayuiPageFactory.defaultPage();
        if (batch == 1){
            return this.baseMapper.customPageMapListFirst(page,param);
        }else {
            return this.baseMapper.customPageMapListAgain(page,param);
        }

    }

    @Override
    public Page<Map<String, Object>> findReview(List<Long> list) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customReview(page,list);
    }

    private Serializable getKey(ThesisParam param){
        return param.getThesisId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Thesis getOldEntity(ThesisParam param) {
        return this.getById(getKey(param));
    }

    private Thesis getEntity(ThesisParam param) {
        Thesis entity = new Thesis();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
