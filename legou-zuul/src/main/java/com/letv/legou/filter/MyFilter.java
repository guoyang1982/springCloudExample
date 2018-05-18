package com.letv.legou.filter;

import com.letv.legou.request.RewriteURIRequestWrapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/17 下午1:14
 */
@Component
public class MyFilter extends ZuulFilter {

    //private static Logger log = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println("?"+request.getRequestURL());

        if(request.getRequestURI().indexOf("api-b")>0 || request.getRequestURI().indexOf("api-c")>0){
            System.out.println(request.getRequestURI());
            return true;
        }
        System.out.println("!"+request.getRequestURI());

        return true;
    }


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //requestURI
        HttpServletRequest request = ctx.getRequest();

        request.getRequestURL();

        //修改uri映射路径，因为已有接口地址都已经是固定的，当没法根据接口uri区分路由后端服务时可以修改uri增加固定前缀，然后根据这个前缀进行yml里的path路由
        //@TODO 可以根据域名进行分组 例子是根据path这样好模拟
        //怎么区分这样的路径  http://a.com/hi   http://b.com/hi
//        if(request.getRequestURI().indexOf("hi")>0){
//            ctx.setRequest(new RewriteURIRequestWrapper(request,"/api-b"+request.getRequestURI()));
//        }


        //log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            //log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        //log.info("ok");
        return null;
    }
}
