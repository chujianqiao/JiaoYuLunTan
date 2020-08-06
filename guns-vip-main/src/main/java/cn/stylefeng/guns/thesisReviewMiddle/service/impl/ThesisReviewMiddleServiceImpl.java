package cn.stylefeng.guns.thesisReviewMiddle.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.thesisReviewMiddle.entity.ThesisReviewMiddle;
import cn.stylefeng.guns.thesisReviewMiddle.mapper.ThesisReviewMiddleMapper;
import cn.stylefeng.guns.thesisReviewMiddle.model.params.ThesisReviewMiddleParam;
import cn.stylefeng.guns.thesisReviewMiddle.model.result.ThesisReviewMiddleResult;
import  cn.stylefeng.guns.thesisReviewMiddle.service.ThesisReviewMiddleService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 论文专家中间表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-08-05
 */
@Service
public class ThesisReviewMiddleServiceImpl extends ServiceImpl<ThesisReviewMiddleMapper, ThesisReviewMiddle> implements ThesisReviewMiddleService {

    @Override
    public void add(ThesisReviewMiddleParam param){
        ThesisReviewMiddle entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ThesisReviewMiddleParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ThesisReviewMiddleParam param){
        ThesisReviewMiddle oldEntity = getOldEntity(param);
        ThesisReviewMiddle newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ThesisReviewMiddleResult findBySpec(ThesisReviewMiddleParam param){
        return null;
    }

    @Override
    public List<ThesisReviewMiddleResult> findListBySpec(ThesisReviewMiddleParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(ThesisReviewMiddleParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ThesisReviewMiddleParam param){
        return param.getMiddleId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ThesisReviewMiddle getOldEntity(ThesisReviewMiddleParam param) {
        return this.getById(getKey(param));
    }

    private ThesisReviewMiddle getEntity(ThesisReviewMiddleParam param) {
        ThesisReviewMiddle entity = new ThesisReviewMiddle();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
