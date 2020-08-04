package cn.dbdj1201.user.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 9:22
 * JWT 实体
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
