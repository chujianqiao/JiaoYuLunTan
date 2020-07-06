package cn.stylefeng.guns.domain.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.domain.entity.Domain;
import cn.stylefeng.guns.domain.model.params.DomainParam;
import cn.stylefeng.guns.domain.model.result.DomainResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 论文领域表 服务类
 * </p>
 *
 * @author CHU
 * @since 2020-07-06
 */
public interface DomainService extends IService<Domain> {

    /**
     * 新增
     *
     * @author CHU
     * @Date 2020-07-06
     */
    void add(DomainParam param);

    /**
     * 删除
     *
     * @author CHU
     * @Date 2020-07-06
     */
    void delete(DomainParam param);

    /**
     * 更新
     *
     * @author CHU
     * @Date 2020-07-06
     */
    void update(DomainParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-06
     */
    DomainResult findBySpec(DomainParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHU
     * @Date 2020-07-06
     */
    List<DomainResult> findListBySpec(DomainParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHU
     * @Date 2020-07-06
     */
     LayuiPageInfo findPageBySpec(DomainParam param);

}
