package cn.stylefeng.guns.modular.educationResult.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.educationResult.entity.EducationResult;
import cn.stylefeng.guns.modular.educationResult.model.params.EducationResultParam;
import cn.stylefeng.guns.modular.educationResult.model.result.EducationResultResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 优秀成果表 服务类
 * </p>
 *
 * @author CHUJIANQIAO
 * @since 2020-05-19
 */
public interface EducationResultService extends IService<EducationResult> {

    /**
     * 新增
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void add(EducationResultParam param);

    /**
     * 删除
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void delete(EducationResultParam param);

    /**
     * 更新
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void update(EducationResultParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    EducationResultResult findBySpec(EducationResultParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    List<EducationResultResult> findListBySpec(EducationResultParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
     LayuiPageInfo findPageBySpec(EducationResultParam param);

}
