package cn.dbdj1201.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-03 15:15
 */
@Controller
@Slf4j
public class UserController {

    @RequestMapping("test")
    public String testHello(ModelMap map) {
        log.info("i am in hello controller");
        map.addAttribute("name", "dbdj1201");
        return "hello";
    }
}
