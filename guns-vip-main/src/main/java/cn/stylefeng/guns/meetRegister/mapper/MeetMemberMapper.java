package cn.stylefeng.guns.meetRegister.mapper;

import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议注册成员表 Mapper 接口
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
public interface MeetMemberMapper extends BaseMapper<MeetMember> {

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-20
     */
    List<MeetMemberResult> customList(@Param("paramCondition") MeetMemberParam paramCondition);

    /**
     * 获取列表
     *
     * @author wucy
     * @Date 2020-05-20
     */
    List<MeetMemberResult> customListAll();

    List<MeetMemberResult> customListByUserId(@Param("userId") Long userId);


    /**
     * 获取map列表
     *
     * @author wucy
     * @Date 2020-05-20
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") MeetMemberParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author wucy
     * @Date 2020-05-20
     */
    Page<MeetMemberResult> customPageList(@Param("page") Page page, @Param("paramCondition") MeetMemberParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author wucy
     * @Date 2020-05-20
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page,
                                                @Param("paramCondition") MeetMemberParam paramCondition,
                                                @Param("list") List<Long> list,
                                                @Param("listStatus") String listStatus);

    void updateWord(@Param("userId") Long userId, @Param("wordName") String wordName, @Param("wordPath") String wordPath);
}
