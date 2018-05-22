package com.gy.legou;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/4/25 下午6:43
 */
@ApiModel(value = "JsonResult", description = "统一封装")
public class JsonResult<T> {
    @ApiModelProperty(value = "status",name="username",example="111")
    private String status = null;
    @ApiModelProperty(value = "result")
    private T result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    // Getter Setter
}
