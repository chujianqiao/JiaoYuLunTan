package cn.stylefeng.guns.pay.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.pay.entity.VipPay;
import cn.stylefeng.guns.pay.mapper.VipPayMapper;
import cn.stylefeng.guns.pay.model.params.VipPayParam;
import cn.stylefeng.guns.pay.model.result.VipPayResult;
import  cn.stylefeng.guns.pay.service.VipPayService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 会员缴费表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-07-15
 */
@Service
public class VipPayServiceImpl extends ServiceImpl<VipPayMapper, VipPay> implements VipPayService {

    @Override
    public void add(VipPayParam param){
        VipPay entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(VipPayParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(VipPayParam param){
        VipPay oldEntity = getOldEntity(param);
        VipPay newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public VipPayResult findBySpec(VipPayParam param){
        return null;
    }

    @Override
    public List<VipPayResult> customList(VipPayParam paramCondition) {
        return baseMapper.customList(paramCondition);
    }

    @Override
    public List<VipPayResult> findListBySpec(VipPayParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(VipPayParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(VipPayParam param){
        return param.getPayId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private VipPay getOldEntity(VipPayParam param) {
        return this.getById(getKey(param));
    }

    private VipPay getEntity(VipPayParam param) {
        VipPay entity = new VipPay();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
