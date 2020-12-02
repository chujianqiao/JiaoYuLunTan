package cn.stylefeng.guns.modular.bill.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.bill.entity.Bill;
import cn.stylefeng.guns.modular.bill.mapper.BillMapper;
import cn.stylefeng.guns.modular.bill.model.params.BillParam;
import cn.stylefeng.guns.modular.bill.model.result.BillResult;
import  cn.stylefeng.guns.modular.bill.service.BillService;
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
 * @author CHU
 * @since 2020-11-05
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Override
    public void add(BillParam param){
        Bill entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(BillParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(BillParam param){
        Bill oldEntity = getOldEntity(param);
        Bill newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public BillResult findBySpec(BillParam param){
        return null;
    }

    @Override
    public List<BillResult> findListBySpec(BillParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(BillParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(BillParam param){
        return param.getBillId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Bill getOldEntity(BillParam param) {
        return this.getById(getKey(param));
    }

    private Bill getEntity(BillParam param) {
        Bill entity = new Bill();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public Bill findByUserAndMeetMember(Long userId, Long meetMemberId){
        return this.baseMapper.findByUserAndMeetMember(userId, meetMemberId);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(BillParam param, List<Long> userIds) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMap(page,param,userIds);
    }
}
