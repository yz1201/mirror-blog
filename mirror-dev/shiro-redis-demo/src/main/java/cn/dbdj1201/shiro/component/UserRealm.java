package cn.dbdj1201.shiro.component;

import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.service.UserService;
import cn.dbdj1201.shiro.util.JwtUtils;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;


/**
 * @author yz1201
 * @date 2020-08-07 23:06
 **/
@Component
@Slf4j
public class UserRealm extends AuthenticatingRealm {

    private static final String SALT = "dbdj1201&bjtamgc";

    @Autowired
    UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        log.info("jwt -------------------------> {}", token);
        //对token进行解析，对user信息进行校验
        String userId = jwtUtils.getClaimByToken(token.getPrincipal().toString()).getSubject();

        User user = userService.getById(Long.parseLong(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        if (user.getStatus() == -1) {
            throw new UnknownAccountException("账户被锁，出问题了。(⊙o⊙)？");
        }

        //封装成SimpleAuthenticationInfo返回给shiro
        return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
    }


}
