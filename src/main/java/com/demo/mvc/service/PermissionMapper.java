package com.demo.mvc.service;

import com.demo.mvc.entity.Permission;
import com.demo.mvc.entity.Role;
import com.demo.mvc.entity.RolePermission;
import com.demo.mvc.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface PermissionMapper {

    @Transactional
    public List<RolePermission> findRolePermissionByRid(int rid);

    @Transactional
    public Permission findPermissionById(int id);

}
