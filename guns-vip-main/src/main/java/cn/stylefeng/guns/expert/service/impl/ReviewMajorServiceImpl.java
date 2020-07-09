package cn.stylefeng.guns.expert.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.expert.entity.ReviewMajor;
import cn.stylefeng.guns.expert.mapper.ReviewMajorMapper;
import cn.stylefeng.guns.expert.model.params.ReviewMajorParam;
import cn.stylefeng.guns.expert.model.result.ReviewMajorResult;
import  cn.stylefeng.guns.expert.service.ReviewMajorService;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评审专家表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-05-11
 */
@Service
public class ReviewMajorServiceImpl extends ServiceImpl<ReviewMajorMapper, ReviewMajor> implements ReviewMajorService {

    @Override
    public void add(ReviewMajorParam param){
        ReviewMajor entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ReviewMajorParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ReviewMajorParam param){
        ReviewMajor oldEntity = getOldEntity(param);
        ReviewMajor newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ReviewMajorResult findBySpec(ReviewMajorParam param){
        return null;
    }

    @Override
    public List<ReviewMajorResult> findListBySpec(ReviewMajorParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ReviewMajorParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(ReviewMajorParam param ,String paramIds) {
        Page page = LayuiPageFactory.defaultPage();
        //普通用户查看自己，管理员查看所有
        LoginUser user = LoginContextHolder.getContext().getUser();
        long userId = user.getId();
        List roleIds = user.getRoleList();
        long adminRole = 1;
        if(roleIds.contains(adminRole)){
            //xml查询条件会忽略0的情况，返回所有
            userId = 0;
        }
        return this.baseMapper.customPageMapList(page,param,userId,paramIds);
    }

    private Serializable getKey(ReviewMajorParam param){
        return param.getReviewId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ReviewMajor getOldEntity(ReviewMajorParam param) {
        return this.getById(getKey(param));
    }

    private ReviewMajor getEntity(ReviewMajorParam param) {
        ReviewMajor entity = new ReviewMajor();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    /**
     * 修改用户状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    @Override
    public Integer setStatus(Long reviewId, String status) {
        return this.baseMapper.setStatus(reviewId, status);
    }

    @Override
    public LayuiPageInfo majorMapList(String domain){
        String[] domainArr = domain.split(",");
        List<Map<String, Object>> listAll = new ArrayList<>();
        for (int i = 0;i < domainArr.length;i++){
            List<Map<String, Object>> list = this.baseMapper.majorMapList(domainArr[i]);
            if (i == 0){
                listAll.addAll(list);
            }else {
                listAll.retainAll(list);
            }

        }
        LayuiPageInfo layuiPageInfo = new LayuiPageInfo();
        layuiPageInfo.setData(listAll);
        return layuiPageInfo;
    }

}
