package com.demo.mvc.service;

import com.demo.mvc.entity.Permission;
import com.demo.mvc.entity.RolePermission;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0427 on 2017/8/15.
 */
@Component
public class PermissionService extends BaseService{

    public List<RolePermission> findRolePermissionByRid(int rid) throws Exception {
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return  sqlsession.getMapper(PermissionMapper.class).findRolePermissionByRid(rid);
        }catch (Exception e){
            throw e;
        }  finally{
            sqlsession.close();
        }
    }

    public Permission findPermissionById(int id) throws Exception {
        SqlSession sqlsession = sqlSessionFactory.openSession();
        try{
            return  sqlsession.getMapper(PermissionMapper.class).findPermissionById(id);
        }catch (Exception e){
            throw e;
        }  finally{
            sqlsession.close();
        }
    }

    public List<Permission> findPermissionsByRid(int rid) throws Exception {
        try {
            List<Permission> permissions = new ArrayList<Permission>();
            List<RolePermission> rolePermissions = findRolePermissionByRid(rid);
            if(rolePermissions != null){
                for( RolePermission rp : rolePermissions )
                {
                    permissions.add(findPermissionById(rp.getPermission_id()));
                }
            }
            return  permissions;
        } catch (Exception e) {
             throw e;
        }
    }
}
