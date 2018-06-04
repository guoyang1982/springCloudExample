package com.gy.legou.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/4/27 下午10:39
 */
@RefreshScope
@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${foo}")
    String foo;

    //@HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        System.out.println("server:"+foo);
        return restTemplate.getForObject("http://legou-server/hi?name="+name,String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
