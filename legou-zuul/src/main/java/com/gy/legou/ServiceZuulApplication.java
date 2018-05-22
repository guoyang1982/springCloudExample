package com.gy.legou;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/17 下午12:09
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ServiceZuulApplication {
    public static void main(String[] args) {

        SpringApplication.run(ServiceZuulApplication.class, args);
    }

    @PostConstruct
    public void zuulInit() {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        // 读取配置，获取脚本根目录
        String scriptRoot = System.getProperty("zuul.filter.root", "/work/zuul/filter/groovy");
        // 获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
        if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(
                    Integer.parseInt(refreshInterval),
                    scriptRoot + "pre",
                    scriptRoot + "route", scriptRoot + "post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
