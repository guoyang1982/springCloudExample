package com.gy.legou.controller;

import com.gy.legou.JsonResult;
import com.gy.legou.User;
import com.gy.legou.service.HelloService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guoyang
 * @Description: TODO
 * @date 2018/4/27 下午10:40
 */
@RestController
public class HelloControler {

    @Autowired
    HelloService helloService;
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name,HttpServletRequest req){

        System.out.println((String)(req.getParameter("name")));
        return helloService.hiService(name);
    }

    @RequestMapping("/hh")
    @ResponseBody
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    //@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    JsonResult<User> home() {
        JsonResult r = new JsonResult();
        try {
            User u = new User();
            u.setUsername("gggg");
            u.setAge(111);

            r.setResult(u);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");

            e.printStackTrace();
        }
        return r;
    }

}