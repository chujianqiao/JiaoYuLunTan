package cn.stylefeng.guns.modular.seat.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.seat.entity.Seat;
import cn.stylefeng.guns.modular.seat.mapper.SeatMapper;
import cn.stylefeng.guns.modular.seat.model.params.SeatParam;
import cn.stylefeng.guns.modular.seat.model.result.SeatResult;
import  cn.stylefeng.guns.modular.seat.service.SeatService;
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
 *  服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-09-10
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {

    @Override
    public void add(SeatParam param){
        Seat entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SeatParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SeatParam param){
        Seat oldEntity = getOldEntity(param);
        Seat newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SeatResult findBySpec(SeatParam param){
        return null;
    }

    @Override
    public List<SeatResult> findListBySpec(SeatParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SeatParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(SeatParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(SeatParam param){
        return param.getSeatId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Seat getOldEntity(SeatParam param) {
        return this.getById(getKey(param));
    }

    private Seat getEntity(SeatParam param) {
        Seat entity = new Seat();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
