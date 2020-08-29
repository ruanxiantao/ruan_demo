package com.beijin.ruangateway.controller;

import com.beijin.ruangateway.config.A;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: ruanxiantao
 * @Description:
 * @Date: 2020/8/1 15:46
 */
@RestController
@RequestMapping("/he")
public class MyController {
    @RequestMapping("/hello")
    public String hello(@RequestBody A a) {
        return "123";
    }
}
