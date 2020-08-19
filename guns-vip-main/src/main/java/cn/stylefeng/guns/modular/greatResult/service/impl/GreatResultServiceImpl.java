package cn.stylefeng.guns.modular.greatResult.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.greatResult.entity.GreatResult;
import cn.stylefeng.guns.modular.greatResult.mapper.GreatResultMapper;
import cn.stylefeng.guns.modular.greatResult.model.params.GreatResultParam;
import cn.stylefeng.guns.modular.greatResult.model.result.GreatResultResult;
import  cn.stylefeng.guns.modular.greatResult.service.GreatResultService;
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
 * 优秀成果表 服务实现类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
@Service
public class GreatResultServiceImpl extends ServiceImpl<GreatResultMapper, GreatResult> implements GreatResultService {

    @Override
    public void add(GreatResultParam param){
        GreatResult entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(GreatResultParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(GreatResultParam param){
        GreatResult oldEntity = getOldEntity(param);
        GreatResult newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public GreatResultResult findBySpec(GreatResultParam param){
        return null;
    }

    @Override
    public List<GreatResultResult> findListBySpec(GreatResultParam param){
        return null;
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(GreatResultParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(GreatResultParam param){
        Page pageContext = getPageContext();
        if (param.getResultName()==null){
            param.setResultName("%%");
        }else {
            param.setResultName("%" + param.getResultName() + "%");
        }

        IPage page = null;
        LoginUser user = LoginContextHolder.getContext().getUser();
        List roleIds = user.getRoleList();
        long adminRole = 1;
        if(roleIds.contains(adminRole)){
            page = this.baseMapper.customPageListAdmin(pageContext, param);
        }else {
            page = this.baseMapper.customPageList(pageContext, param);
        }

        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(GreatResultParam param){
        return param.getResultId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private GreatResult getOldEntity(GreatResultParam param) {
        return this.getById(getKey(param));
    }

    private GreatResult getEntity(GreatResultParam param) {
        GreatResult entity = new GreatResult();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
