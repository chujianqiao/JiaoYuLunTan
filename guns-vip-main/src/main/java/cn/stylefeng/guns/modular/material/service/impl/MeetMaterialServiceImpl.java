package cn.stylefeng.guns.modular.material.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.material.entity.MeetMaterial;
import cn.stylefeng.guns.modular.material.mapper.MeetMaterialMapper;
import cn.stylefeng.guns.modular.material.model.params.MeetMaterialParam;
import cn.stylefeng.guns.modular.material.model.result.MeetMaterialResult;
import  cn.stylefeng.guns.modular.material.service.MeetMaterialService;
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
 * 会议材料表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-07-22
 */
@Service
public class MeetMaterialServiceImpl extends ServiceImpl<MeetMaterialMapper, MeetMaterial> implements MeetMaterialService {

    @Override
    public void add(MeetMaterialParam param){
        MeetMaterial entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(MeetMaterialParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(MeetMaterialParam param){
        MeetMaterial oldEntity = getOldEntity(param);
        MeetMaterial newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public MeetMaterialResult findBySpec(MeetMaterialParam param){
        return null;
    }

    @Override
    public List<MeetMaterialResult> findListBySpec(MeetMaterialParam param){
        return this.baseMapper.customList(param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(MeetMaterialParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(MeetMaterialParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(MeetMaterialParam param){
        return param.getMaterialId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private MeetMaterial getOldEntity(MeetMaterialParam param) {
        return this.getById(getKey(param));
    }

    private MeetMaterial getEntity(MeetMaterialParam param) {
        MeetMaterial entity = new MeetMaterial();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
