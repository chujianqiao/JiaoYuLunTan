package cn.stylefeng.guns.modular.material.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.material.entity.MeetMaterial;
import cn.stylefeng.guns.modular.material.model.params.MeetMaterialParam;
import cn.stylefeng.guns.modular.material.model.result.MeetMaterialResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议材料表 服务类
 * </p>
 *
 * @author wucy
 * @since 2020-07-22
 */
public interface MeetMaterialService extends IService<MeetMaterial> {

    /**
     * 新增
     *
     * @author wucy
     * @Date 2020-07-22
     */
    void add(MeetMaterialParam param);

    /**
     * 删除
     *
     * @author wucy
     * @Date 2020-07-22
     */
    void delete(MeetMaterialParam param);

    /**
     * 更新
     *
     * @author wucy
     * @Date 2020-07-22
     */
    void update(MeetMaterialParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-22
     */
    MeetMaterialResult findBySpec(MeetMaterialParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author wucy
     * @Date 2020-07-22
     */
    List<MeetMaterialResult> findListBySpec(MeetMaterialParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author wucy
     * @Date 2020-07-22
     */
     LayuiPageInfo findPageBySpec(MeetMaterialParam param);

     Page<Map<String, Object>> findPageWrap(MeetMaterialParam param);

}
