package cn.stylefeng.guns.collTopic.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.collTopic.entity.CollectTopic;
import cn.stylefeng.guns.collTopic.mapper.CollectTopicMapper;
import cn.stylefeng.guns.collTopic.model.params.CollectTopicParam;
import cn.stylefeng.guns.collTopic.model.result.CollectTopicResult;
import  cn.stylefeng.guns.collTopic.service.CollectTopicService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 论坛主题征集表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-05-18
 */
@Service
public class CollectTopicServiceImpl extends ServiceImpl<CollectTopicMapper, CollectTopic> implements CollectTopicService {

    @Override
    public void add(CollectTopicParam param){
        CollectTopic entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CollectTopicParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(CollectTopicParam param){
        CollectTopic oldEntity = getOldEntity(param);
        CollectTopic newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CollectTopicResult findBySpec(CollectTopicParam param){
        return null;
    }

    @Override
    public List<CollectTopicResult> findListBySpec(CollectTopicParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CollectTopicParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(CollectTopicParam param){
        return param.getTopicId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CollectTopic getOldEntity(CollectTopicParam param) {
        return this.getById(getKey(param));
    }

    private CollectTopic getEntity(CollectTopicParam param) {
        CollectTopic entity = new CollectTopic();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
