package cn.stylefeng.guns.modular.educationResult.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.mapper.EducationResultMapper;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.model.result.EducationResultResult;
import  cn.stylefeng.guns.modular.educationResult.service.EducationResultService;
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
public class EducationResultServiceImpl extends ServiceImpl<EducationResultMapper, EducationResult> implements EducationResultService {

    @Override
    public void add(EducationResultParam param){
        EducationResult entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EducationResultParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(EducationResultParam param){
        EducationResult oldEntity = getOldEntity(param);
        EducationResult newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EducationResultResult findBySpec(EducationResultParam param){
        return null;
    }

    @Override
    public List<EducationResultResult> findListBySpec(EducationResultParam param){
        return null;
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(EducationResultParam param,List<Long> list,String listStatus) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param,list,listStatus);
    }

    @Override
    public LayuiPageInfo findPageBySpec(EducationResultParam param){
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

    private Serializable getKey(EducationResultParam param){
        return param.getResultId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private EducationResult getOldEntity(EducationResultParam param) {
        return this.getById(getKey(param));
    }

    private EducationResult getEntity(EducationResultParam param) {
        EducationResult entity = new EducationResult();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
