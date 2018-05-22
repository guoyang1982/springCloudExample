package com.gy.legou.common.exception;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/2 下午7:03
 */
public class BusinessException extends AbsFrameworkException {

    private static final long serialVersionUID = 1L;

    public BusinessException(int code) {
        super(code);
    }

    public BusinessException(int code, String msg) {
        super(code, msg);
    }

    public BusinessException(int code, Exception e) {
        super(code, e);
    }

}