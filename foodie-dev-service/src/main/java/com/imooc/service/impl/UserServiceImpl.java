package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    Sid sid;
    @Transactional(propagation =Propagation.REQUIRED)
    @Override
    public void saveUser() {
        Users user=new Users();
        user.setId("1");
        user.setUsername("jeff");
        user.setNickname("文文");
        user.setPassword("123");
        usersMapper.insert(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Object getUser(String id) {
        return usersMapper.selectByPrimaryKey(id);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUser(String id) {
        Users user=new Users();
        user.setId("1");
        user.setUsername("jeff");
        user.setNickname("文文哟哟");
        user.setPassword("123");
        usersMapper.updateByPrimaryKey(user);
    }
    public void saveParent() {
        Users user=new Users();
        user.setId("1");
        user.setUsername("Parent");
        user.setNickname("文文哟哟");
        user.setPassword("123");
        usersMapper.insert(user);
    }
    @Transactional(propagation = Propagation.NESTED)
    public void saveChildren() {
        saveChild1();
        int a = 1 / 0;
        saveChild2();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example userExample=new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        Users users = (Users) usersMapper.selectOneByExample(userExample);

        return users==null?false:true;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) throws Exception {
        Users user=new Users();
        user.setId(sid.nextShort());
        user.setUsername(userBO.getUsername());
        user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        user.setNickname(userBO.getUsername());
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setSex(Sex.man.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }

    public void saveChild1() {
        Users user=new Users();
        user.setId("2");
        user.setUsername("Child1");
        user.setNickname("文文哟哟");
        user.setPassword("123");
        usersMapper.insert(user);
    }
    public void saveChild2() {
        Users user=new Users();
        user.setId("3");
        user.setUsername("Child2");
        user.setNickname("文文哟哟");
        user.setPassword("123");
        usersMapper.insert(user);
    }
}
