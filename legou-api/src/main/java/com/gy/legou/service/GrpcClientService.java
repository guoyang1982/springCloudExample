package com.gy.legou.service;

import io.grpc.Channel;
import net.devh.examples.grpc.lib.HelloReply;
import net.devh.examples.grpc.lib.HelloRequest;
import net.devh.examples.grpc.lib.SimpleGrpc;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 2016/11/8
 */
@RefreshScope
@Service
public class GrpcClientService {

    @GrpcClient("legou-server")
    private Channel serverChannel;
    @Value("${foo}")
    String foo;
    @Autowired
    private Environment env;
    public String sendMessage(String name) {
        System.out.println("grpc:"+foo);
        System.out.println("grpc:"+env.getProperty("foo", "undefined"));

        SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(serverChannel);
        HelloReply response = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }
}
