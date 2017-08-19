package com.demo.mvc.service;

import com.demo.mvc.entity.Role;
import com.demo.mvc.entity.User;
import com.demo.mvc.entity.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/8/13.
 */
public interface RoleMapper {
    @Transactional
    public Role findRoleByName(String name);

    @Transactional
    public void addUserRoleByUserRole(UserRole userRole);

    @Transactional
    public List<Role> findAllRoles();

    @Transactional
    public void delUserRoleByUid(int uid);

    @Transactional
    public Role findRoleByUid(int uid);

    @Transactional
    public void updateUserRoleByUid(UserRole userRole);
}
