package cn.stylefeng.guns.reviewunit.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.reviewunit.entity.ReviewUnit;
import cn.stylefeng.guns.reviewunit.mapper.ReviewUnitMapper;
import cn.stylefeng.guns.reviewunit.model.params.ReviewUnitParam;
import cn.stylefeng.guns.reviewunit.model.result.ReviewUnitResult;
import  cn.stylefeng.guns.reviewunit.service.ReviewUnitService;
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
 * 理事单位表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-05-14
 */
@Service
public class ReviewUnitServiceImpl extends ServiceImpl<ReviewUnitMapper, ReviewUnit> implements ReviewUnitService {

    @Override
    public void add(ReviewUnitParam param){
        ReviewUnit entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ReviewUnitParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ReviewUnitParam param){
        ReviewUnit oldEntity = getOldEntity(param);
        ReviewUnit newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ReviewUnitResult findBySpec(ReviewUnitParam param){
        return null;
    }

    @Override
    public List<ReviewUnitResult> findListBySpec(ReviewUnitParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ReviewUnitParam param){
        Page pageContext = getPageContext();

        boolean isAdmin = cn.stylefeng.guns.util.ToolUtil.isAdminRole();
        LoginUser user = LoginContextHolder.getContext().getUser();
        long userId = user.getId();
        if(isAdmin){
            //管理员返回全部数据
            userId = 0;
        }

        IPage page = this.baseMapper.customPageList(pageContext, param ,userId);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(ReviewUnitParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(ReviewUnitParam param){
        return param.getReviewId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ReviewUnit getOldEntity(ReviewUnitParam param) {
        return this.getById(getKey(param));
    }

    private ReviewUnit getEntity(ReviewUnitParam param) {
        ReviewUnit entity = new ReviewUnit();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
