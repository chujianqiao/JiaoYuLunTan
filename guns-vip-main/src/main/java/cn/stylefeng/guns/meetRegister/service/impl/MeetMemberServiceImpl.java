package cn.stylefeng.guns.meetRegister.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.meetRegister.entity.MeetMember;
import cn.stylefeng.guns.meetRegister.mapper.MeetMemberMapper;
import cn.stylefeng.guns.meetRegister.model.params.MeetMemberParam;
import cn.stylefeng.guns.meetRegister.model.result.MeetMemberResult;
import  cn.stylefeng.guns.meetRegister.service.MeetMemberService;
import cn.stylefeng.guns.thesis.model.params.ThesisParam;
import cn.stylefeng.guns.thesis.model.result.ThesisResult;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会议注册成员表 服务实现类
 * </p>
 *
 * @author wucy
 * @since 2020-05-20
 */
@Service
public class MeetMemberServiceImpl extends ServiceImpl<MeetMemberMapper, MeetMember> implements MeetMemberService {

    @Override
    public void add(MeetMemberParam param){
        MeetMember entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(MeetMemberParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(MeetMemberParam param){
        MeetMember oldEntity = getOldEntity(param);
        MeetMember newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public MeetMemberResult findBySpec(MeetMemberParam param){
        return null;
    }

    @Override
    public List<MeetMemberResult> customList(MeetMemberParam paramCondition) {
        return this.baseMapper.customList(paramCondition);
    }

    @Override
    public List<MeetMemberResult> findListBySpec(){
        return this.baseMapper.customListAll();
    }

    @Override
    public List<MeetMemberResult> findListByUserId(Long userId){
        return this.baseMapper.customListByUserId(userId);
    }
    @Override
    public List<MeetMemberResult> customListIfExist(Long meetId, Long userId){
        return this.baseMapper.customListIfExist(meetId, userId);
    }

    @Override
    public LayuiPageInfo findPageBySpec(MeetMemberParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Page<Map<String, Object>> findPageWrap(MeetMemberParam param,List<Long> list,String listStatus) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.customPageMapList(page,param,list,listStatus);
    }

    private Serializable getKey(MeetMemberParam param){
        return param.getMemberId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private MeetMember getOldEntity(MeetMemberParam param) {
        return this.getById(getKey(param));
    }

    private MeetMember getEntity(MeetMemberParam param) {
        MeetMember entity = new MeetMember();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public void updateWord(Long userId, String wordName, String wordPath){
        this.baseMapper.updateWord(userId,wordName,wordPath);
    }

    @Override
    public List<Long> sortMember(Long meetId) {
        return baseMapper.sortMember(meetId);
    }

    @Override
    public List<Long> generalMember(Long meetId) {
        return baseMapper.generalMember(meetId);
    }

}
