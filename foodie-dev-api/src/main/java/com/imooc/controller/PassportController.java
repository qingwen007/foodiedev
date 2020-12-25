package com.imooc.controller;

import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    public UserService userService;
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
    @PostMapping("regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO) throws Exception {
        String username= userBO.getUsername();
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
}
