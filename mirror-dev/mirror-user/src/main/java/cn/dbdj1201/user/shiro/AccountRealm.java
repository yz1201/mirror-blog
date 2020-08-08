package cn.dbdj1201.user.shiro;

import cn.dbdj1201.user.entity.User;
import cn.dbdj1201.user.service.UserService;
import cn.dbdj1201.user.util.JwtUtils;
import cn.dbdj1201.user.util.SpringBeanFactoryUtils;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 9:19
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {

    /*
        AccountRealm是shiro进行登录或者权限校验的逻辑所在

        supports：为了让realm支持jwt的凭证校验
        doGetAuthorizationInfo：权限校验
        doGetAuthenticationInfo：登录认证校验
    */

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 主要就是doGetAuthenticationInfo登录认证这个方法，可以看到我们通过jwt获取到用户信息，
     * 判断用户的状态，最后异常就抛出对应的异常信息，否者封装成SimpleAuthenticationInfo返回给shiro
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        log.info("jwt -------------------------> {}", token);
        //对token进行解析，对user信息进行校验
        String userId = jwtUtils.getClaimByToken(token.getPrincipal().toString()).getSubject();

//        Object bean = SpringBeanFactoryUtils.getBean(UserService.class);
        User user = userService.getById(Long.parseLong(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        if (user.getStatus() == -1) {
            throw new UnknownAccountException("账户被锁，出问题了。(⊙o⊙)？");
        }

        //封装成SimpleAuthenticationInfo返回给shiro
        AccountProfile accountProfile = new AccountProfile();
        BeanUtil.copyProperties(user, accountProfile);
        log.info("profile-------->{}", accountProfile);
        return new SimpleAuthenticationInfo(accountProfile,token.getCredentials(),getName());
    }
}
