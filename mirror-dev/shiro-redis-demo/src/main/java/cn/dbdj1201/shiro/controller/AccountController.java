package cn.dbdj1201.shiro.controller;

import cn.dbdj1201.shiro.entity.CommonResult;
import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.entity.UserInfo;
import cn.dbdj1201.shiro.service.UserService;
import cn.dbdj1201.shiro.util.JwtUtils;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yz1201
 * @date 2020-08-07 23:27
 **/
@RestController
@Slf4j
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public CommonResult<Object> login(@RequestBody UserInfo userInfo) {
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(),
//                userInfo.getPassword());
//        subject.login(token);
//        if (subject.isAuthenticated()) {
//            return new CommonResult<>(200, "登录成功", this.userService.getOne(
//                    new QueryWrapper<User>().eq("username", userInfo.getUsername())
//            ));
//        }
//        return new CommonResult<>(444, "none", "┭┮﹏┭┮");
//    }

    @PostMapping("/login")
    public CommonResult<Object> login(@RequestBody UserInfo userInfo, HttpServletResponse response) {

        User user = userService.getOne(new QueryWrapper<User>().eq("username", userInfo.getUsername()));
        Assert.notNull(user, "用户不存在");

        if (!user.getPassword().equals(SecureUtil.md5(userInfo.getPassword()))) {
            return new CommonResult<>(444, "Wrong password", null);
        }
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return new CommonResult<>(200, "login success", user);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public CommonResult<Object> logout() {

        log.info("current subject-{}", SecurityUtils.getSubject().getPrincipal());

        return new CommonResult<>(200, "成功登出(●ˇ∀ˇ●)", null);
    }
}
