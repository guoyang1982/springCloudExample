package com.gy.legou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import zipkin.server.EnableZipkinServer;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/23 上午10:34
 */
@SpringBootApplication
//@EnableZipkinServer //接口方式http
@EnableZipkinStreamServer //使用Stream方式启动ZipkinServer kafka rabitmq
public class ServerZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerZipkinApplication.class, args);
    }
}
