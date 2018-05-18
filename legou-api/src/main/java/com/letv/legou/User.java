package com.letv.legou;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/4/25 下午6:44
 */
@ApiModel(value = "User", description = "用户对象")
public class User {
    @ApiModelProperty(value = "ID",name="username",example="111")
    private int id;
    @ApiModelProperty(value = "username")
    private String username;
    @ApiModelProperty(value = "age")

    private int age;
    @ApiModelProperty(value = "ctm")

    private Date ctm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCtm() {
        return ctm;
    }

    public void setCtm(Date ctm) {
        this.ctm = ctm;
    }

    // Getter Setter
}