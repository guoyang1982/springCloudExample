package com.gy.legou.controller;

import com.gy.legou.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 2016/12/4
 */
@RefreshScope
@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;
    @Value("${foog}")
    String foo1;
    @RequestMapping("/sayHello")
    public String printMessage(@RequestParam(defaultValue = "Michael") String name) {
        System.out.println("grp1:"+foo1);

        return grpcClientService.sendMessage(name);
    }
}
