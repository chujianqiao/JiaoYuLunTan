package cn.stylefeng.guns.modular.greatResult.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.greatResult.entity.GreatResult;
import cn.stylefeng.guns.modular.greatResult.model.params.GreatResultParam;
import cn.stylefeng.guns.modular.greatResult.model.result.GreatResultResult;
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
public interface GreatResultService extends IService<GreatResult> {

    /**
     * 新增
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void add(GreatResultParam param);

    /**
     * 删除
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void delete(GreatResultParam param);

    /**
     * 更新
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    void update(GreatResultParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    GreatResultResult findBySpec(GreatResultParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
    List<GreatResultResult> findListBySpec(GreatResultParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author CHUJIANQIAO
     * @Date 2020-05-19
     */
     LayuiPageInfo findPageBySpec(GreatResultParam param);

}
