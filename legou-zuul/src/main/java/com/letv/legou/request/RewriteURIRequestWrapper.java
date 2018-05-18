package com.letv.legou.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/17 下午8:21
 */
public class RewriteURIRequestWrapper extends HttpServletRequestWrapper {

    private String rewriteURI;

    public RewriteURIRequestWrapper(HttpServletRequest request, String rewriteURI) {
        super(request);
        this.rewriteURI = rewriteURI;
    }

    @Override
    public String getRequestURI() {
        return rewriteURI;
    }

}