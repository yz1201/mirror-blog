package cn.dbdj1201.shiro.controller;

import cn.dbdj1201.shiro.entity.CommonResult;
import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.entity.UserInfo;
import cn.dbdj1201.shiro.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yz1201
 * @date 2020-08-07 23:27
 **/
@RestController
@Slf4j
public class AccountController {

    @Autowired
    private UserService userService;

    public CommonResult<Object> login(@RequestBody UserInfo userInfo) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(),
                userInfo.getPassword());
        subject.login(token);
        if (subject.isAuthenticated()) {
            return new CommonResult<>(200, "登录成功", this.userService.getOne(
                    new QueryWrapper<User>().eq("username", userInfo.getUsername())
            ));
        }
        return new CommonResult<>(444,"none","┭┮﹏┭┮");
    }

}
