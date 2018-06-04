package net.devh.springboot.autoconfigure.grpc.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.LoadBalancer;
import io.grpc.util.RoundRobinLoadBalancerFactory;

/**
 * User: Michael
 * Email: yidongnan@gmail.com
 * Date: 5/17/16
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnClass({GrpcChannelFactory.class})
public class GrpcClientAutoConfiguration {
    /**
     * 初始化配置项 ip端口等，和springcloud的application.yml绑定
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public GrpcChannelsProperties grpcChannelsProperties() {
        return new GrpcChannelsProperties();
    }
    /**
     * 实例化注册类，查找所有应用端定义的grpc的拦截器配置类，并把注册类注入到应用的拦截器的配置类里，
     * 由应用的拦截器配置类把应用定义的grpc拦截器放到框架注册类的List<ServerInterceptor>里
     * @return
     */
    @Bean
    public GlobalClientInterceptorRegistry globalClientInterceptorRegistry() {
        return new GlobalClientInterceptorRegistry();
    }

    /**
     * 初始化负载均衡实例
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public LoadBalancer.Factory grpcLoadBalancerFactory() {
        return RoundRobinLoadBalancerFactory.getInstance();
    }

    /**
     *
     * @param channels
     * @param loadBalancerFactory
     * @param globalClientInterceptorRegistry
     * @return
     */
    @ConditionalOnMissingBean(value = GrpcChannelFactory.class, type = "org.springframework.cloud.client.discovery.DiscoveryClient")
    @Bean
    public GrpcChannelFactory addressChannelFactory(GrpcChannelsProperties channels, LoadBalancer.Factory loadBalancerFactory, GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
        return new AddressChannelFactory(channels, loadBalancerFactory, globalClientInterceptorRegistry);
    }

    @Bean
    @ConditionalOnClass(GrpcClient.class)
    public GrpcClientBeanPostProcessor grpcClientBeanPostProcessor() {

        return new GrpcClientBeanPostProcessor();
    }

    /**
     * DiscoveryClient这个类是注册中心的类，可以从这里拿到注册中心的链接地址和端口（gRPC）
     * 当这个类实例化时才 实例化这个bean
     */
    @Configuration
    @ConditionalOnBean(DiscoveryClient.class)
    protected static class DiscoveryGrpcClientAutoConfiguration {

        @ConditionalOnMissingBean
        @Bean
        public GrpcChannelFactory discoveryClientChannelFactory(GrpcChannelsProperties channels, DiscoveryClient discoveryClient, LoadBalancer.Factory loadBalancerFactory,
            GlobalClientInterceptorRegistry globalClientInterceptorRegistry) {
            return new DiscoveryClientChannelFactory(channels, discoveryClient, loadBalancerFactory, globalClientInterceptorRegistry);
        }
    }

    /**
     * grpc拦截器 扩展点 依赖Tracer实例化且配置为true 然后实例化
     */
    @Configuration
    @ConditionalOnProperty(value = "spring.sleuth.scheduled.enabled", matchIfMissing = true)
    @ConditionalOnClass(Tracer.class)
    protected static class TraceClientAutoConfiguration {

        @Bean
        public GlobalClientInterceptorConfigurerAdapter globalTraceClientInterceptorConfigurerAdapter(final Tracer tracer) {
            return new GlobalClientInterceptorConfigurerAdapter() {

                @Override
                public void addClientInterceptors(GlobalClientInterceptorRegistry registry) {
                    registry.addClientInterceptors(new TraceClientInterceptor(tracer, new MetadataInjector()));
                }
            };
        }
    }

}
