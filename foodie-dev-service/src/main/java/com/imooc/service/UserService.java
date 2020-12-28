package com.imooc.service;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    public void saveUser();

    Object getUser(String id);

    public void updateUser(String id);
    public void saveParent();
    public void saveChildren();
    public boolean queryUsernameIsExist(String username);
    public Users createUser(UserBO userBO) throws Exception;

    Users queryUserForLogin(String username, String md5Str);
}
