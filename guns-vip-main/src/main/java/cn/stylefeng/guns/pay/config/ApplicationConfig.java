package cn.stylefeng.guns.pay.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 注册所有映射属性类  { }中用逗号分隔即可注册多个属性类
 */
@Configuration
@EnableConfigurationProperties({AlipayConfigProperties.class})
public class ApplicationConfig {

}
