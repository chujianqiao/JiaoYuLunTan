package cn.stylefeng.guns.modular.weixin.util;

import cn.stylefeng.guns.modular.weixin.pojo.Token;
import cn.stylefeng.guns.sys.modular.consts.model.params.SysConfigParam;
import cn.stylefeng.guns.sys.modular.consts.model.result.SysConfigResult;
import cn.stylefeng.guns.sys.modular.consts.service.SysConfigService;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名: TokenUtil </br>
 * 描述: Token </br>
 * 开发人员： souvc </br>
 * 创建时间：  2015-10-5 </br>
 * 发布版本：V1.0  </br>
 */
public class TokenUtil {

    private static SysConfigService sysConfigService = SpringContextHolder.getBean(SysConfigService.class);;
    /**
     * 方法名：getToken</br>
     * 详述：从数据库里面获取token</br>
     * 开发人员：souvc </br>
     * 创建时间：2015-10-5  </br>
     * @return
     * @throws
     */
    public static Map<String, Object> getToken(){
        Map<String, Object> map=new HashMap<String, Object>();
        SysConfigParam param = new SysConfigParam();
        param.setCode("ACCESS_TOKEN");
        SysConfigResult sysConfigResult = sysConfigService.findByCode(param);

        map.put("access_token", sysConfigResult.getValue());
        map.put("expires_in", sysConfigResult.getRemark());

        return map;
    }

    /**
     * 方法名：saveToken</br>
     * 详述：保存token</br>
     * 开发人员：souvc </br>
     * 创建时间：2015-10-5  </br>
     * @return
     * @throws
     */
    public static void saveToken(Token token){
        String remark = token.getExpiresIn() + "";
        SysConfigParam param1 = new SysConfigParam();
        param1.setCode("ACCESS_TOKEN");
        SysConfigResult sysConfigResult = sysConfigService.findByCode(param1);
        SysConfigParam param2 = new SysConfigParam();
        param2.setId(sysConfigResult.getId());
        param2.setCode("ACCESS_TOKEN");
        param2.setValue(token.getAccessToken());
        param2.setRemark(remark);
        sysConfigService.update(param2);

    }
}
