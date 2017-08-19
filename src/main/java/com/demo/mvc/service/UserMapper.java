package com.demo.mvc.service;

import com.demo.mvc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface UserMapper {
    @Transactional
    public User findUserByUsername(String username);

    @Transactional
    public void addUserInfo(User user);

    @Transactional
    public List<User> findAllUsers();

    @Transactional
    public User findLastRecord();

    @Transactional
    public void delUserById(int id);

    @Transactional
    public User findUserById(int id);

    @Transactional
    public void updateUserById(User user);
}
