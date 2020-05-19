package cn.stylefeng.guns.collTopic.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.collTopic.entity.CollectTopic;
import cn.stylefeng.guns.collTopic.model.params.CollectTopicParam;
import cn.stylefeng.guns.collTopic.model.result.CollectTopicResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 论坛主题征集表 服务类
 * @author wucy
 * @since 2020-05-18
 */
public interface CollectTopicService extends IService<CollectTopic> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-05-18
     */
    void add(CollectTopicParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-05-18
     */
    void delete(CollectTopicParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-05-18
     */
    void update(CollectTopicParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-05-18
     */
    CollectTopicResult findBySpec(CollectTopicParam param);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-05-18
     */
    List<CollectTopicResult> findListBySpec(CollectTopicParam param);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-05-18
     */
     LayuiPageInfo findPageBySpec(CollectTopicParam param);

    /**
     * 拼接字段用
     * @param param
     * @return
     */
     Page<Map<String, Object>> findPageWrap(CollectTopicParam param);

}
