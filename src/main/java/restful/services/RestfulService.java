package main.java.restful.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Pentru a apela din browser  http://localhost:4200/spring-example-web-app-0.0.1-SNAPSHOT/myService
 *
 */

@RestController
@RequestMapping("/myService")
public class RestfulService {

    @GetMapping
    private String hello() {
        return "hello";
    }
}