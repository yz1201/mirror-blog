package cn.dbdj1201.user.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yz1201
 * @date 2020-08-03 22:26
 **/
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
