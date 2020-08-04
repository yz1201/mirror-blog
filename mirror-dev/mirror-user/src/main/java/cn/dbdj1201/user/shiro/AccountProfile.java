package cn.dbdj1201.user.shiro;

import lombok.Data;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 9:34
 */
@Data
public class AccountProfile {
    //登录成功后返回给用户的信息
    private Long id;
    //用户名
    private String username;
    //越南语，头像
    private String avatar;

    private String email;
}
