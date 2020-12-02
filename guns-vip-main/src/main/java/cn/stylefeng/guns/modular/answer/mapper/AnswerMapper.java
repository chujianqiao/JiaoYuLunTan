package cn.stylefeng.guns.modular.answer.mapper;

import cn.stylefeng.guns.modular.answer.entity.Answer;
import cn.stylefeng.guns.modular.answer.model.params.AnswerParam;
import cn.stylefeng.guns.modular.answer.model.result.AnswerResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自动回复表 Mapper 接口
 * </p>
 *
 * @author CHU
 * @since 2020-11-09
 */
public interface AnswerMapper extends BaseMapper<Answer> {

    /**
     * 获取列表
     *
     * @author CHU
     * @Date 2020-11-09
     */
    List<AnswerResult> customList(@Param("paramCondition") AnswerParam paramCondition);

    /**
     * 获取map列表
     *
     * @author CHU
     * @Date 2020-11-09
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") AnswerParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author CHU
     * @Date 2020-11-09
     */
    Page<AnswerResult> customPageList(@Param("page") Page page, @Param("paramCondition") AnswerParam paramCondition);

    Page<AnswerResult> customPageListAll(@Param("page") Page page, @Param("paramCondition") AnswerParam paramCondition);
    /**
     * 获取分页map列表
     *
     * @author CHU
     * @Date 2020-11-09
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") AnswerParam paramCondition);
    Integer setStatus(@Param("answerId") Long answerId, @Param("status") String status);
}
