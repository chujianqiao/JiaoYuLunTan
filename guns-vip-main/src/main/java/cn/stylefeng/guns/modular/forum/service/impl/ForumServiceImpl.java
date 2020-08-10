package cn.stylefeng.guns.modular.forum.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.forum.entity.Forum;
import cn.stylefeng.guns.modular.forum.mapper.ForumMapper;
import cn.stylefeng.guns.modular.forum.model.params.ForumParam;
import cn.stylefeng.guns.modular.forum.model.result.ForumResult;
import  cn.stylefeng.guns.modular.forum.service.ForumService;
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
 * 分论坛表 服务实现类
 * </p>
 *
 * @author CHU
 * @since 2020-08-10
 */
@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {

    @Override
    public void add(ForumParam param){
        Forum entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ForumParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ForumParam param){
        Forum oldEntity = getOldEntity(param);
        Forum newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ForumResult findBySpec(ForumParam param){
        return null;
    }

    @Override
    public List<ForumResult> findListBySpec(ForumParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ForumParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(ForumParam param) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param);
    }

    private Serializable getKey(ForumParam param){
        return param.getForumId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Forum getOldEntity(ForumParam param) {
        return this.getById(getKey(param));
    }

    private Forum getEntity(ForumParam param) {
        Forum entity = new Forum();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public Integer setStatus(Long forumId, Integer status) {
        return this.baseMapper.setStatus(forumId, status);
    }

}
