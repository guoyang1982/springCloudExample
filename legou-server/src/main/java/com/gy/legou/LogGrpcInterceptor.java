package com.gy.legou;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有grp方法调用前的拦截
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 2016/12/6
 */
public class LogGrpcInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("????????????????" + serverCall.getMethodDescriptor().getFullMethodName());
        System.out.println("????????????????" + serverCall.getMethodDescriptor().getFullMethodName());

        return serverCallHandler.startCall(serverCall, metadata);
    }
}
