package cn.dbdj1201.shiro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yz1201
 * @date 2020-08-07 23:26
 * 用户DTO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    //用户名
    private String username;
    //密码
    private String password;
}
