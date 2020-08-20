package cn.stylefeng.guns.meetRegister.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.model.result.ThesisResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议注册成员表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
public interface MeetMemberService extends IService<MeetMember> {

    /**
     * 新增
     * @author wucy
     * @Date 2020-05-20
     */
    void add(MeetMemberParam param);

    /**
     * 删除
     * @author wucy
     * @Date 2020-05-20
     */
    void delete(MeetMemberParam param);

    /**
     * 更新
     * @author wucy
     * @Date 2020-05-20
     */
    void update(MeetMemberParam param);

    /**
     * 查询单条数据，Specification模式
     * @author wucy
     * @Date 2020-05-20
     */
    MeetMemberResult findBySpec(MeetMemberParam param);

    /**
     * 有条件的查询
     * @param paramCondition
     * @return
     */
    List<MeetMemberResult> customList(@Param("paramCondition") MeetMemberParam paramCondition);

    /**
     * 查询列表，Specification模式
     * @author wucy
     * @Date 2020-05-20
     */
    List<MeetMemberResult> findListBySpec();

    List<MeetMemberResult> findListByUserId(Long userId);

    /**
     * 查询分页数据，Specification模式
     * @author wucy
     * @Date 2020-05-20
     */
     LayuiPageInfo findPageBySpec(MeetMemberParam param);

    /**
     * 拼接字段用
     * @param param
     * @return
     */
    Page<Map<String, Object>> findPageWrap(MeetMemberParam param,List<Long> list,String listStatus);

    /**
     * 更新
     * @author wucy
     * @Date 2020-05-20
     */
    void updateWord(Long userId, String wordName, String wordPath);
}
