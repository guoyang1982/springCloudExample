//package com.gy.legou.common.result;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.gy.legou.common.exception.BusinessException;
//import com.gy.legou.common.utils.JsonUtils;
//
//import java.io.Serializable;
//import java.lang.reflect.Method;
//
///**
// * @author guoyang
// * @Description: TODO
// * @date 2018/5/2 下午6:55
// */
//public class JsonResult implements Serializable {
//    private static final long serialVersionUID = 1L;
//    public static final int SUCCESS = 0;
//    public static final int FAILED = 1;
//    private int code = 0;
//    private String msg = "";
//    private ValidateResult errors = null;
//    private Object data = null;
//    private long cost = 0L;
//
//    public JsonResult() {
//    }
//
//    public JsonResult(int code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public JsonResult(int code, ValidateResult errors) {
//        this.code = code;
//        this.errors = errors;
//    }
//
//    public int getCode() {
//        return this.code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return this.msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public ValidateResult getErrors() {
//        return this.errors;
//    }
//
//    public void setErrors(ValidateResult errors) {
//        this.errors = errors;
//    }
//
//    public Object getData() {
//        return this.data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public long getCost() {
//        return this.cost;
//    }
//
//    public void setCost(long cost) {
//        this.cost = cost;
//    }
//
//    public <T> T getData(Class<T> clazz) {
//        return this.getData() == null ? null : JsonUtils.json2Object(JsonUtils.object2Json(this.getData()), clazz);
//    }
//
//    public <T> T getData(TypeReference<T> typeReference) {
//        return this.getData() == null ? null : JsonUtils.json2Object(JsonUtils.object2Json(this.getData()), typeReference);
//    }
//
//    public boolean isSuccess() {
//        return this.getCode() == 0;
//    }
//
//    public static JsonResult success(Object data) {
//        JsonResult ret = new JsonResult(0, "成功");
//        ret.setData(data);
//        return ret;
//    }
//
//    public static JsonResult success() {
//        return new JsonResult(0, "成功");
//    }
//
//    public static JsonResult error(String msg) {
//        return new JsonResult(1, msg);
//    }
//
//    public static JsonResult error(int code, String msg) {
//        return new JsonResult(code, msg);
//    }
//
//    public static JsonResult fail(Enum<?> errorEnum) {
//        try {
//            Class<?> clazz = errorEnum.getDeclaringClass();
//            Method getStatus = clazz.getDeclaredMethod("getStatus");
//            Method getMsg = clazz.getDeclaredMethod("getMsg");
//            int status = ((Integer)getStatus.invoke(errorEnum)).intValue();
//            String msg = (String)getMsg.invoke(errorEnum);
//            return new JsonResult(status, msg);
//        } catch (Exception var6) {
//            throw new BusinessException(-9998);
//        }
//    }
//
//    public static JsonResult fail(Enum<?> errorEnum, Object attachInfo) {
//        try {
//            JsonResult fail = fail(errorEnum);
//            fail.setMsg(fail.getMsg() + "\n" + JsonUtils.object2Json(attachInfo));
//            return fail;
//        } catch (Exception var3) {
//            throw new BusinessException(-9998);
//        }
//    }
//
//    public static JsonResult fail(ValidateResult errors) {
//        return new JsonResult(-9998, errors);
//    }
//}
//
