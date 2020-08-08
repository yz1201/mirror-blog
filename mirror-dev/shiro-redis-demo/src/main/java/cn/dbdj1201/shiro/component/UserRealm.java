package cn.dbdj1201.shiro.component;

import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.service.UserService;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
//@Component
public class UserRealm extends AuthenticatingRealm {

    private static final String SALT = "dbdj1201&bjtamgc";

    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        User user = this.userService.getOne(new QueryWrapper<User>().eq("username", principal));

        if (user == null) {
            throw new UnknownAccountException("未知账户┭┮﹏┭┮");
        }
        if (user.getStatus() != 0) {
            throw new LockedAccountException("你账户被锁了哥w(ﾟДﾟ)w");
        }

        return new SimpleAuthenticationInfo(
                user, user.getPassword(), ByteSource.Util.bytes(SALT), getName());
    }

//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        // 角色
//        Set<String> roles = new HashSet<>();
//        // 权限
//        // 测试用权限
//        if ("admin".equals(user.getUsername())) {
//            roles.add("admin");
//            permissions.add("op:write");
//        } else {
//            roles.add("user");
//            permissions.add("op:read");
//        }
//        authorizationInfo.setRoles(roles);
//        authorizationInfo.setStringPermissions(permissions);
//        return authorizationInfo;
//    }


}
