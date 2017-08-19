package com.demo.mvc.service;

import com.demo.mvc.entity.Role;
import com.demo.mvc.entity.User;
import com.demo.mvc.entity.UserRole;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
@Component
public class RoleService extends BaseService{


    public Role findRoleByName(String name){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(RoleMapper.class).findRoleByName(name);
        }finally {
            sqlsession.close();
        }
    }

    public Role findRoleByUid(int uid){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return sqlsession.getMapper(RoleMapper.class).findRoleByUid(uid);
        }finally {
            sqlsession.close();
        }
    }


    public void addUserRoleByUserRole(UserRole userRole){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
             sqlsession.getMapper(RoleMapper.class).addUserRoleByUserRole(userRole);
             sqlsession.commit();
        }finally {
            sqlsession.close();
        }
    }

    public List<Role> findAllRoles() throws Exception {
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
             return   sqlsession.getMapper(RoleMapper.class).findAllRoles();
        }catch (Exception e){
            throw e;
        }  finally{
            sqlsession.close();
        }
    }

    public void delUserRoleByUid(int uid){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            sqlsession.getMapper(RoleMapper.class).delUserRoleByUid(uid);
            sqlsession.commit();
        }finally {
            sqlsession.close();
        }
    }

    public void updateUserRoleByUid(UserRole userRole){
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            sqlsession.getMapper(RoleMapper.class).updateUserRoleByUid(userRole);
            sqlsession.commit();
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            sqlsession.close();
        }

    }
}
