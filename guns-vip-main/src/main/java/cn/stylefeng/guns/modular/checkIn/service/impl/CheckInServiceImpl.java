package cn.stylefeng.guns.modular.checkIn.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.checkIn.entity.CheckIn;
import cn.stylefeng.guns.modular.checkIn.mapper.CheckInMapper;
import cn.stylefeng.guns.modular.checkIn.model.params.CheckInParam;
import cn.stylefeng.guns.modular.checkIn.model.result.CheckInResult;
import  cn.stylefeng.guns.modular.checkIn.service.CheckInService;
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
 * 报到签到表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-07-30
 */
@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements CheckInService {

    @Override
    public void add(CheckInParam param){
        CheckIn entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CheckInParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(CheckInParam param){
        CheckIn oldEntity = getOldEntity(param);
        CheckIn newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CheckInResult findBySpec(CheckInParam param){
        return null;
    }

    @Override
    public List<CheckInResult> findListBySpec(CheckInParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CheckInParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(CheckInParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(CheckInParam param){
        return param.getCheckId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CheckIn getOldEntity(CheckInParam param) {
        return this.getById(getKey(param));
    }

    private CheckIn getEntity(CheckInParam param) {
        CheckIn entity = new CheckIn();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
