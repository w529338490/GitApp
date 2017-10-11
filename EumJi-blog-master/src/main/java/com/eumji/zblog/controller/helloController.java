package com.eumji.zblog.controller;

import com.eumji.zblog.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class helloController {

    @RequestMapping(value = "/logins",method = RequestMethod.POST)
    public String hellos(User user ){
        System.out.println("login"+user.getUsername());
        return "blog/index";
    }
}
