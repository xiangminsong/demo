package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class ControllerDemo {


    @RequestMapping("/v1")
    public String test1() {

        return "user";
    }


}
