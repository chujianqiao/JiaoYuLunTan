package cn.stylefeng.guns.domain.mapper;

import cn.stylefeng.guns.domain.entity.Domain;
import cn.stylefeng.guns.domain.model.params.DomainParam;
import cn.stylefeng.guns.domain.model.result.DomainResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 论文领域表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-07-06
 */
public interface DomainMapper extends BaseMapper<Domain> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-07-06
     */
    List<DomainResult> customList(@Param("paramCondition") DomainParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-07-06
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") DomainParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-07-06
     */
    Page<DomainResult> customPageList(@Param("page") Page page, @Param("paramCondition") DomainParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-07-06
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") DomainParam paramCondition);

}
