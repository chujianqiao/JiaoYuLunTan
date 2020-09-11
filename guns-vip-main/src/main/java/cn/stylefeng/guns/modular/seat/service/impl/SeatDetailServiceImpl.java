package cn.stylefeng.guns.modular.seat.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.seat.entity.SeatDetail;
import cn.stylefeng.guns.modular.seat.mapper.SeatDetailMapper;
import cn.stylefeng.guns.modular.seat.model.params.SeatDetailParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatDetailResult;
import  cn.stylefeng.guns.modular.seat.service.SeatDetailService;
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
 * 座位详情表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-09-11
 */
@Service
public class SeatDetailServiceImpl extends ServiceImpl<SeatDetailMapper, SeatDetail> implements SeatDetailService {

    @Override
    public void add(SeatDetailParam param){
        SeatDetail entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SeatDetailParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SeatDetailParam param){
        SeatDetail oldEntity = getOldEntity(param);
        SeatDetail newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SeatDetailResult findBySpec(SeatDetailParam param){
        return null;
    }

    @Override
    public List<SeatDetailResult> findListBySpec(SeatDetailParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SeatDetailParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(SeatDetailParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(SeatDetailParam param){
        return param.getSeatDetailId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SeatDetail getOldEntity(SeatDetailParam param) {
        return this.getById(getKey(param));
    }

    private SeatDetail getEntity(SeatDetailParam param) {
        SeatDetail entity = new SeatDetail();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
