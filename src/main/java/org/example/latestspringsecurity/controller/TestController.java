package org.example.latestspringsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * 具有 admin 角色的人才能访问；
     * @return
     */
    @GetMapping("/admin/hello")
    public String adminHello() {
        return "hello, admin";
    }

    /**
     * 具有 user 角色的人才能访问
     * @return
     */
    @GetMapping("/user/hello")
    public String userHello() {
        return "hello, user";
    }

    /**
     * 没有角色都可以访问
     * @return
     */
    @GetMapping("/visitor/hello")
    public String visitorHello() {
        return "hello, visitor";
    }


}
