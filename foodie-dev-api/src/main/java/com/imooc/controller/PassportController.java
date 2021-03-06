package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    public UserService userService;
    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在notes",httpMethod = "GET")
    @GetMapping("usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        if(StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return  IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "用户注册",notes = "用户注册notes",httpMethod = "POST")
    @PostMapping("regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO) throws Exception {
        String username = userBO.getUsername();
        String password= userBO.getPassword();
        String confirmPassword= userBO.getConfirmPassword();
        if(StringUtils.isBlank(username)
                ||StringUtils.isBlank(username)
                ||StringUtils.isBlank(username)){
            return  IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if(isExist){
            return  IMOOCJSONResult.errorMsg("用户名已存在");
        }
        if(password.length()<6){
            return  IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }
        if(!password.equals(confirmPassword)){
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }
        userService.createUser(userBO);

        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        return IMOOCJSONResult.ok(userResult);
    }
}
