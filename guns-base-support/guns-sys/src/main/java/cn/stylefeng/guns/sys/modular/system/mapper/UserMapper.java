package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    Page<Map<String, Object>> selectUsers(@Param("page") Page page,
                                          @Param("dataScope") DataScope dataScope,
                                          @Param("name") String name,
                                          @Param("beginTime") String beginTime,
                                          @Param("endTime") String endTime,
                                          @Param("deptId") Long deptId);
    Page<Map<String, Object>> selectUsersRole(@Param("page") Page page,
                                          @Param("dataScope") DataScope dataScope,
                                          @Param("name") String name,
                                          @Param("beginTime") String beginTime,
                                          @Param("endTime") String endTime,
                                          @Param("deptId") Long deptId,
                                          @Param("roleId") Long roleId);

    /**
     * 根据条件查询用户列表（根据角色）
     */
    Page<Map<String, Object>> selectUsersByRole(@Param("page") Page page,
                                                @Param("name") String name,
                                                @Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime,
                                                @Param("roleId") String roleId);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);

    int cancelBand(@Param("userId") Long userId);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 通过手机号获取用户
     */
    User getByPhone(@Param("phone") String phone);
    User getUserByUnionId(@Param("unionId") String unionId);

    /**
     * 选择办理人
     */
    IPage<Map<String, Object>> listUserAndRoleExpectAdmin(Page page);


    List<Map<String, Object>> majorMapList(@Param("roleIds") String roleIds);
    List<User> listByRole(@Param("roleId") String roleId, @Param("name") String name);
    List<Long> listByName(@Param("name") String name);
    List<User> getByCanDownloadFile();

    /**
     * 修改用户状态
     */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    int editUserByWrong(@Param("userId") Long userId, @Param("status") String status, @Param("wrongTimes") Integer wrongTimes);

    /**
     * 通过账号获取用户
     */
    User getById(@Param("userId") Long userId);
}
