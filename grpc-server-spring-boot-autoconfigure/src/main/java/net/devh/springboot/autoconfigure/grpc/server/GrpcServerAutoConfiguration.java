package net.devh.springboot.autoconfigure.grpc.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.Server;
import io.grpc.services.HealthStatusManager;
import io.netty.channel.Channel;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 5/17/16
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({Server.class, GrpcServerFactory.class})
public class GrpcServerAutoConfiguration {

    /**
     * 初始化配置项 ip端口等，和springcloud的application.yml绑定
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public GrpcServerProperties defaultGrpcServerProperties() {
        return new GrpcServerProperties();
    }

    /**
     * 实例化注册类，查找所有应用端定义的grpc的拦截器配置类，并把注册类注入到应用的拦截器的配置类里，
     * 由应用的拦截器配置类把应用定义的grpc拦截器放到框架注册类的List<ServerInterceptor>里
     * @return
     */
    @Bean
    public GlobalServerInterceptorRegistry globalServerInterceptorRegistry() {
        return new GlobalServerInterceptorRegistry();
    }

    /**
     * 实例化grpc注解查找类（主要功能是注入拦截器和查找@GrpcService爆露的bean并把这些对象都放在List<GrpcServiceDefinition>里)
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public AnnotationGrpcServiceDiscoverer defaultGrpcServiceFinder() {
        return new AnnotationGrpcServiceDiscoverer();
    }

    /**
     * 实例化grpc的健康检查
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public HealthStatusManager healthStatusManager() {
        return new HealthStatusManager();
    }

    /**
     * 创建grpc的server 并调用上面实例化的GrpcServiceDiscoverer对像查找@GrpcService爆露的bean
     * 和ServerInterceptor的拦截器类（包含trace和自定义的拦截器），并注入爆露
     * @param properties
     * @param discoverer
     * @return
     */
    @ConditionalOnMissingBean
    @ConditionalOnClass(Channel.class)
    @Bean
    public NettyGrpcServerFactory nettyGrpcServiceFactory(GrpcServerProperties properties, GrpcServiceDiscoverer discoverer) {
        NettyGrpcServerFactory factory = new NettyGrpcServerFactory(properties);
        for (GrpcServiceDefinition service : discoverer.findGrpcServices()) {
            factory.addService(service);
        }

        return factory;
    }

    /**
     * 调用上面实例化的GrpcServerFactory 开启grpc服务
     * @param factory
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public GrpcServerLifecycle grpcServerLifecycle(GrpcServerFactory factory) {
        return new GrpcServerLifecycle(factory);
    }

    /**
     * 实例化跟踪链路连接器，并把跟踪链路拦截器放到List<ServerInterceptor>里，和上面的拦截器一样
     */
    @Configuration
    @ConditionalOnProperty(value = "spring.sleuth.scheduled.enabled", matchIfMissing = true)
    @ConditionalOnClass(Tracer.class)
    protected static class TraceServerAutoConfiguration {

        @Bean
        public GlobalServerInterceptorConfigurerAdapter globalTraceServerInterceptorConfigurerAdapter(final Tracer tracer) {
            return new GlobalServerInterceptorConfigurerAdapter() {
                @Override
                public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
                    registry.addServerInterceptors(new TraceServerInterceptor(tracer, new MetadataExtractor()));
                }
            };
        }

    }
}
