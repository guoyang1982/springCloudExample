package com.letv.legou.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/4/27 下午9:15
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplicaion {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApplicaion.class, args);
    }
}