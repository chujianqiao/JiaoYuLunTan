package cn.stylefeng.guns.modular.greatReviewMiddle.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.greatReviewMiddle.entity.GreatReviewMiddle;
import cn.stylefeng.guns.modular.greatReviewMiddle.mapper.GreatReviewMiddleMapper;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.params.GreatReviewMiddleParam;
import cn.stylefeng.guns.modular.greatReviewMiddle.model.result.GreatReviewMiddleResult;
import  cn.stylefeng.guns.modular.greatReviewMiddle.service.GreatReviewMiddleService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 著作专家中间表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-08-18
 */
@Service
public class GreatReviewMiddleServiceImpl extends ServiceImpl<GreatReviewMiddleMapper, GreatReviewMiddle> implements GreatReviewMiddleService {

    @Override
    public void add(GreatReviewMiddleParam param){
        GreatReviewMiddle entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(GreatReviewMiddleParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(GreatReviewMiddleParam param){
        GreatReviewMiddle oldEntity = getOldEntity(param);
        GreatReviewMiddle newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public GreatReviewMiddleResult findBySpec(GreatReviewMiddleParam param){
        return null;
    }

    @Override
    public List<GreatReviewMiddleResult> findListBySpec(GreatReviewMiddleParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(GreatReviewMiddleParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(GreatReviewMiddleParam param){
        return param.getMiddleId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private GreatReviewMiddle getOldEntity(GreatReviewMiddleParam param) {
        return this.getById(getKey(param));
    }

    private GreatReviewMiddle getEntity(GreatReviewMiddleParam param) {
        GreatReviewMiddle entity = new GreatReviewMiddle();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
