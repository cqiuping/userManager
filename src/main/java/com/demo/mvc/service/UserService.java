package com.demo.mvc.service;

import com.demo.mvc.entity.User;
import com.demo.mvc.entity.UserRole;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
@Component
public class UserService extends BaseService{

    @Autowired
    private RoleService roleService;

    public User findUserByUsername(String username) throws Exception {
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(UserMapper.class).findUserByUsername(username);
        }catch (Exception e){
            throw e;
        } finally{
            sqlsession.close();
        }
    }

    public User findLastRecord(){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(UserMapper.class).findLastRecord();
        }finally {
            sqlsession.close();
        }
    }

    public User findUserById(int id){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(UserMapper.class).findUserById(id);
        }finally {
            sqlsession.close();
        }
    }


    public List<User> findAllUsers(){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(UserMapper.class).findAllUsers();
        }finally {
            sqlsession.close();
        }
    }

    //删除用户信息，包括删除UserRole的记录
    @Transactional
    public void delRealUserInfo(int uid){
        try{
            //删除user表
            delUserById(uid);
            //删除user_role表
            roleService.delUserRoleByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delUserById(int id){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            sqlsession.getMapper(UserMapper.class).delUserById(id);
            sqlsession.commit();
        }finally {
            sqlsession.close();
        }
    }


    public void addUser(User user) throws Exception {
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            sqlsession.getMapper(UserMapper.class).addUserInfo(user);
            sqlsession.commit();
        }catch (PersistenceException e){
            throw e;
        } finally{
            sqlsession.close();
        }
    }

    //插入用户信息，包括更新user_role表
    @Transactional
    public void addRealUserInfo(User user, String role_name) throws Exception {
        try{
            //addUser
            addUser(user);
            //把刚刚插入的用户找出来
            int uid = findLastRecord().getId();
            //查找rid
            int rid = roleService.findRoleByName(role_name).getId();
            System.err.println("***rid***" + rid + "---" + uid);
            //更新user_role表
            UserRole userRole = new UserRole();
            userRole.setRid(rid);
            userRole.setUid(uid);
            roleService.addUserRoleByUserRole(userRole);
        }catch (PersistenceException e){
            throw e;
        }
    }

    public void updateUserById(User user){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            sqlsession.getMapper(UserMapper.class).updateUserById(user);
            sqlsession.commit();
        }finally {
            sqlsession.close();
        }
    }

    @Transactional
    public void updateRealUserInfo(User user, String role){
        try{
                //更新用户表
                updateUserById(user);
                int uid = user.getId();
                int rid = roleService.findRoleByName(role).getId();
                UserRole userRole= new UserRole();
                userRole.setRid(rid);
                userRole.setUid(uid);
                //更新user_role表
                roleService.updateUserRoleByUid(userRole);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
