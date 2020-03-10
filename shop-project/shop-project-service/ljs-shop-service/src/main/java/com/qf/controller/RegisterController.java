package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author li
 * @date 2020/3/10 0010 13:53
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

    @ResponseBody
    @RequestMapping("/hi")
    public String sayHi(String message){
        return message;
    }

}
