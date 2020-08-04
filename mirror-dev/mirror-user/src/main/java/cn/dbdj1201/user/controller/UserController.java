package cn.dbdj1201.user.controller;


import cn.dbdj1201.user.entity.CommonResult;
import cn.dbdj1201.user.entity.User;
import cn.dbdj1201.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User testHello(@PathVariable("id") Long id) {
        return userService.getById(id);
    }


    @PostMapping("/save")
    public CommonResult<User> testUser(@Validated @RequestBody User user) {
        return new CommonResult<>(200, "", user);
    }

}
