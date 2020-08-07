package cn.dbdj1201.user.controller;

import cn.dbdj1201.user.common.dto.LoginDto;
import cn.dbdj1201.user.entity.CommonResult;
import cn.dbdj1201.user.entity.User;
import cn.dbdj1201.user.service.UserService;
import cn.dbdj1201.user.util.JwtUtils;
import cn.dbdj1201.user.util.ShiroUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 11:29
 */
@RestController
@Slf4j
public class AccountController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    /*接受账号密码，然后把用户的id生成jwt，返回给前段，为了后续的jwt的延期，所以我们把jwt放在header上*/

    @CrossOrigin
    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public CommonResult<Object> login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User loginUser = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(loginUser, "用户不存在");

        if (!loginUser.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return new CommonResult<>(444, "密码错误", null);
        }

//        log.info("someone is login - {}", loginDto);
        String jwt = jwtUtils.generateToken(loginUser.getId());
        log.info("login jwt generate- {}", jwt);
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

//        log.info("shiro util profile -> {}", ShiroUtil.getProfile());


        return new CommonResult<>(200, "登录成功<(￣︶￣)↗[GO!]",
                MapUtil.builder()
                        .put("id", loginUser.getId())
                        .put("username", loginUser.getUsername())
                        .put("avatar", loginUser.getAvatar())
                        .put("email", loginUser.getEmail())
                        .map()
        );

    }

    @RequiresAuthentication
    @GetMapping(value = "/logout", produces = "application/json;charset=UTF-8")
    public CommonResult<User> logout() {
//        log.info("shiro util profile -> {}", ShiroUtil.getProfile());

        Subject subject = SecurityUtils.getSubject();
        log.info("logout subject-{}", subject.getPrincipal());

        subject.logout();


        return new CommonResult<>(200, "走咯O(∩_∩)O", null);
    }

}
