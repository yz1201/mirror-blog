package cn.dbdj1201.shiro.controller;


import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.service.UserService;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-07
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User findUser(@PathVariable Long id) {
        User user = this.userService.getById(id);
        log.info("call the user service - {}", user);
//        if (user==null){
//            return null;
//        }
        return user;
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setAvatar("test.jpg");
        user.setCreated(LocalDateTime.now());
        user.setEmail("15957121194@163.com");
        user.setLastLogin(LocalDateTime.now());
        user.setStatus(0);
        return this.userService.save(user);
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return this.userService.list();
    }

}
