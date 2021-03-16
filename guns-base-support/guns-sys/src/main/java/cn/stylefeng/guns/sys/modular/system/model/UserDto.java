/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.sys.modular.system.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户传输bean
 *
 * @author stylefeng
 * @Date 2017/5/5 22:40
 */
@Data
public class UserDto {

    private Long userId;

    private String account;

    private String password;


    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String sex;

    private String email;

    private String phone;

    private String roleId;

    private Long deptId;

    private String status;

    private String avatar;

    private Integer vip;

    private String workUnit;

    private String title;

    private String post;

    private String education;

    private String direction;

    private Integer wrongTimes;

    private Integer canDownloadWord;

    private Integer canDownloadPpt;

    /**
     * 发言稿路径
     */
    private String wordPath;

    /**
     * 发言稿名称
     */
    private String wordName;

    /**
     * ppt路径
     */
    private String pptPath;

    /**
     * ppt名称
     */
    private String pptName;
    /**
     * 嘉宾简介
     */
    private String introduction;
    /**
     * 微信昵称
     */
    private String wechatName;
    /**
     * 微信公众平台openid
     */
    private String wechatId;

    /**
     * 微信唯一unionId
     */
    private String unionId;
    /**
     * 微信开放平台openid
     */
    private String wechatOpenId;

    /*@NotBlank
    private String position;*/
    private String country;
    private String province;
    private String city;

}
