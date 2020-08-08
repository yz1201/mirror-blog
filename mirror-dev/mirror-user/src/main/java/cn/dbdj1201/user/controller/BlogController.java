package cn.dbdj1201.user.controller;


import cn.dbdj1201.user.entity.Blog;
import cn.dbdj1201.user.entity.CommonResult;
import cn.dbdj1201.user.service.BlogService;
import cn.dbdj1201.user.shiro.AccountProfile;
import cn.dbdj1201.user.util.ShiroUtil;
import cn.dbdj1201.user.util.SpringBeanFactoryUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dbdj1201
 * @since 2020-08-04
 */
@RestController
@Slf4j
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private SpringBeanFactoryUtils springBeanFactoryUtils;

    @GetMapping("/blogs")
    public CommonResult<IPage<Blog>> blogs(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page<Blog> page = new Page<>(currentPage, 5);
        IPage<Blog> blogIPage = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return new CommonResult<>(200, "查询成功，(●ˇ∀ˇ●)", blogIPage);
    }

    @GetMapping("/blog/{id}")
    public CommonResult<Blog> blog(@PathVariable Long id) {
        log.info("call me for what");
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除，/(ㄒoㄒ)/~~");
        return new CommonResult<>(200, "定位成功", blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public CommonResult<Object> edit(@Validated @RequestBody Blog blog) {

        Subject subject = SecurityUtils.getSubject();
        log.info("blog edit subject-{}", subject);
        log.info("new blog content: {}", blog);
        Blog temp;
        if (blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            //断言，登录对象必须持有编辑此博客的权限，否则无法编辑
            log.info("crazy -> {}", ShiroUtil.getProfile());
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有编辑权限，Σ(っ °Д °;)っ");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        //后边是要忽略的字段
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.save(temp);
        return new CommonResult<>(200, "编辑成功", blog);
    }

//    @PostMapping("/blog/add")
//    public CommonResult<Object> add(){
//        log.info("someone wanna add blog");
//
//
//        return new CommonResult<>(200,"有权限新增",null);
//    }


}
