package cn.stylefeng.guns.modular.answer.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.answer.entity.Answer;
import cn.stylefeng.guns.modular.answer.mapper.AnswerMapper;
import cn.stylefeng.guns.modular.answer.model.params.AnswerParam;
import cn.stylefeng.guns.modular.answer.model.result.AnswerResult;
import  cn.stylefeng.guns.modular.answer.service.AnswerService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 自动回复表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-11-09
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    @Override
    public void add(AnswerParam param){
        Answer entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AnswerParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(AnswerParam param){
        Answer oldEntity = getOldEntity(param);
        Answer newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AnswerResult findBySpec(AnswerParam param){
        return null;
    }

    @Override
    public List<AnswerResult> findListBySpec(AnswerParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AnswerParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo findPageBySpecAll(AnswerParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageListAll(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AnswerParam param){
        return param.getAnswerId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Answer getOldEntity(AnswerParam param) {
        return this.getById(getKey(param));
    }

    private Answer getEntity(AnswerParam param) {
        Answer entity = new Answer();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public Integer setStatus(Long answerId, String status) {
        return this.baseMapper.setStatus(answerId, status);
    }

}
