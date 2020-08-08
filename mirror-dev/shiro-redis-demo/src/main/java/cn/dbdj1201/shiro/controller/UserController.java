package cn.dbdj1201.shiro.controller;


import cn.dbdj1201.shiro.entity.CommonResult;
import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.service.UserService;
import cn.hutool.core.text.Simhash;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
    public CommonResult<User> register(@RequestBody User user) {
        String password = user.getPassword();

        Sha256Hash sha256Hash = new Sha256Hash(password, "dbdj1201&bjtamgc", 1024);
        user.setPassword(sha256Hash.toHex());
        user.setAvatar("test.jpg");
        user.setCreated(LocalDateTime.now());
        user.setEmail("15957121194@163.com");
        user.setLastLogin(LocalDateTime.now());
        user.setStatus(0);
         this.userService.save(user);
        return new CommonResult<>(200,"注册成功",user);
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return this.userService.list();
    }

}
