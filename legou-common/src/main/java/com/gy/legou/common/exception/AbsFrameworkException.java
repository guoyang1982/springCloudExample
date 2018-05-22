package com.gy.legou.common.exception;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/5/2 下午7:04
 */
public abstract class AbsFrameworkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = 0;

    private String msg = null;

    public AbsFrameworkException(int code) {
        super();
        this.code = code;
    }

    public AbsFrameworkException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AbsFrameworkException(int code, Exception e) {
        super(e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

