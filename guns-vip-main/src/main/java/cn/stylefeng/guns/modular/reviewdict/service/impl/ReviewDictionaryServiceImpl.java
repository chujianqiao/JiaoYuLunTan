package cn.stylefeng.guns.modular.reviewdict.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.reviewdict.entity.ReviewDictionary;
import cn.stylefeng.guns.modular.reviewdict.mapper.ReviewDictionaryMapper;
import cn.stylefeng.guns.modular.reviewdict.model.params.ReviewDictionaryParam;
import cn.stylefeng.guns.modular.reviewdict.model.result.ReviewDictionaryResult;
import  cn.stylefeng.guns.modular.reviewdict.service.ReviewDictionaryService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 评审字典表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-07-28
 */
@Service
public class ReviewDictionaryServiceImpl extends ServiceImpl<ReviewDictionaryMapper, ReviewDictionary> implements ReviewDictionaryService {

    @Override
    public void add(ReviewDictionaryParam param){
        ReviewDictionary entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ReviewDictionaryParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ReviewDictionaryParam param){
        ReviewDictionary oldEntity = getOldEntity(param);
        ReviewDictionary newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ReviewDictionaryResult findBySpec(ReviewDictionaryParam param){
        return null;
    }

    @Override
    public List<ReviewDictionaryResult> findListBySpec(ReviewDictionaryParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ReviewDictionaryParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ReviewDictionaryParam param){
        return param.getDicId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ReviewDictionary getOldEntity(ReviewDictionaryParam param) {
        return this.getById(getKey(param));
    }

    private ReviewDictionary getEntity(ReviewDictionaryParam param) {
        ReviewDictionary entity = new ReviewDictionary();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
