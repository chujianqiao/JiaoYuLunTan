package cn.stylefeng.guns.modular.educationReviewMiddle.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationReviewMiddle.entity.EducationReviewMiddle;
import cn.stylefeng.guns.modular.educationReviewMiddle.mapper.EducationReviewMiddleMapper;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.params.EducationReviewMiddleParam;
import cn.stylefeng.guns.modular.educationReviewMiddle.model.result.EducationReviewMiddleResult;
import  cn.stylefeng.guns.modular.educationReviewMiddle.service.EducationReviewMiddleService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 教改成果专家中间表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
@Service
public class EducationReviewMiddleServiceImpl extends ServiceImpl<EducationReviewMiddleMapper, EducationReviewMiddle> implements EducationReviewMiddleService {

    @Override
    public void add(EducationReviewMiddleParam param){
        EducationReviewMiddle entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EducationReviewMiddleParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(EducationReviewMiddleParam param){
        EducationReviewMiddle oldEntity = getOldEntity(param);
        EducationReviewMiddle newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EducationReviewMiddleResult findBySpec(EducationReviewMiddleParam param){
        return null;
    }

    @Override
    public List<EducationReviewMiddleResult> findListBySpec(EducationReviewMiddleParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(EducationReviewMiddleParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(EducationReviewMiddleParam param){
        return param.getMiddleId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private EducationReviewMiddle getOldEntity(EducationReviewMiddleParam param) {
        return this.getById(getKey(param));
    }

    private EducationReviewMiddle getEntity(EducationReviewMiddleParam param) {
        EducationReviewMiddle entity = new EducationReviewMiddle();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
