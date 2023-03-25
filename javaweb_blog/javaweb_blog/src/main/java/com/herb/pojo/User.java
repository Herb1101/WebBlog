package com.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId; // 用户ID
    private String uname; // 用户名称
    private String upwd; // 用户密码
    private String nick; // 用户昵称
    private String head; // 用户头像
    private String mood; // 用户签名

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
