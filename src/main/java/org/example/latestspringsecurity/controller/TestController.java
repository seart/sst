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
    @GetMapping("/admin/hello")
    public String helloAdmin() {

        return "hello, admin";
    }

    @GetMapping("/user/hello")
    public String helloUser() {

        return "hello, user";
    }

    @GetMapping("/visitor/hello")
    public String helloVisitor() {

        return "hello, visitor";
    }


}
