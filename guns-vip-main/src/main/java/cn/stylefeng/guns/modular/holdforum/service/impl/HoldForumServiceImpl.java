package cn.stylefeng.guns.modular.holdforum.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.holdforum.entity.HoldForum;
import cn.stylefeng.guns.modular.holdforum.mapper.HoldForumMapper;
import cn.stylefeng.guns.modular.holdforum.model.params.HoldForumParam;
import cn.stylefeng.guns.modular.holdforum.model.result.HoldForumResult;
import  cn.stylefeng.guns.modular.holdforum.service.HoldForumService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 承办方论坛表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-05-13
 */
@Service
public class HoldForumServiceImpl extends ServiceImpl<HoldForumMapper, HoldForum> implements HoldForumService {

    @Override
    public void add(HoldForumParam param){
        HoldForum entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(HoldForumParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(HoldForumParam param){
        HoldForum oldEntity = getOldEntity(param);
        HoldForum newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public HoldForumResult findBySpec(HoldForumParam param){
        return null;
    }

    @Override
    public List<HoldForumResult> findListBySpec(HoldForumParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(HoldForumParam param){
        Page pageContext = getPageContext();
        if (param.getForumName()==null){
            param.setForumName("%%");
        }else {
            param.setForumName("%" + param.getForumName() + "%");
        }

        IPage page = null;
        //LoginUser user = LoginContextHolder.getContext().getUser();
        //List roleIds = user.getRoleList();
        //long adminRole = 1;
        boolean isAdmin = cn.stylefeng.guns.util.ToolUtil.isAdminRole();
        if(isAdmin){
            page = this.baseMapper.customPageListAdmin(pageContext, param);
        }else {
            page = this.baseMapper.customPageList(pageContext, param);
        }

        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(HoldForumParam param){
        return param.getForumId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private HoldForum getOldEntity(HoldForumParam param) {
        return this.getById(getKey(param));
    }

    private HoldForum getEntity(HoldForumParam param) {
        HoldForum entity = new HoldForum();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
