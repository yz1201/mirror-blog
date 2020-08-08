package cn.dbdj1201.shiro.controller;


import cn.dbdj1201.shiro.entity.Blog;
import cn.dbdj1201.shiro.entity.CommonResult;
import cn.dbdj1201.shiro.entity.User;
import cn.dbdj1201.shiro.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-07
 */
@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    @Autowired
    BlogService blogService;

    @RequiresAuthentication
    @PostMapping("/add")
    public CommonResult<Object> generateBlog(@RequestBody Blog blog) {
        blog.setCreated(LocalDateTime.now());
        blog.setStatus(0);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        log.info("subject - {}",user);
        blog.setUserId(user.getId());
        this.blogService.save(blog);
        return new CommonResult<>(200, "add successful", blog);
    }

}
