package cn.stylefeng.guns.meet.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meet.entity.Meet;
import cn.stylefeng.guns.meet.mapper.MeetMapper;
import cn.stylefeng.guns.meet.model.params.MeetParam;
import cn.stylefeng.guns.meet.model.result.MeetResult;
import  cn.stylefeng.guns.meet.service.MeetService;
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
 * 会议表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-08-05
 */
@Service
public class MeetServiceImpl extends ServiceImpl<MeetMapper, Meet> implements MeetService {

    @Override
    public void add(MeetParam param){
        Meet entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(MeetParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(MeetParam param){
        Meet oldEntity = getOldEntity(param);
        Meet newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public MeetResult findBySpec(MeetParam param){
        return null;
    }
    @Override
    public Meet getByStatus(Integer status){
        return this.baseMapper.getByStatus(status);
    }

    @Override
    public List<MeetResult> findListBySpec(MeetParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(MeetParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(MeetParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(MeetParam param){
        return param.getMeetId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Meet getOldEntity(MeetParam param) {
        return this.getById(getKey(param));
    }

    private Meet getEntity(MeetParam param) {
        Meet entity = new Meet();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
