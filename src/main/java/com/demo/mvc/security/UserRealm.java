package com.demo.mvc.security;

import com.demo.mvc.entity.Permission;
import com.demo.mvc.entity.Role;
import com.demo.mvc.entity.User;
import com.demo.mvc.service.PermissionService;
import com.demo.mvc.service.RoleService;
import com.demo.mvc.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/13.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    private static final Logger LOG = LoggerFactory.getLogger(UserRealm.class);

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("权限配置---》doGetAuthenticationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        String  username  = (String)principalCollection.getAvailablePrincipal();
        String username = (String) getAvailablePrincipal(principalCollection);
        User user = null;
        try {
            user = userService.findUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Role role = roleService.findRoleByUid(user.getId());

        System.err.println("*****shiro username***" + user.getUsername());
        System.err.println("userRole******"+ role.getId());
        try {
            System.err.println("permissions" + permissionService.findPermissionsByRid(role.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> permissions = null;
        try {
            permissions = new HashSet<String>();
//            info.addRole(role.getName());
            for (Permission permission : permissionService.findPermissionsByRid(role.getId())) {
                    if(StringUtil.isNotBlank(permission.getUrl())){
                        permissions.add(permission.getUrl());
//                        info.addStringPermission(permission.getUrl());
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        info.setStringPermissions(permissions);
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        if (username == null) {
            throw new AccountException("用户名不能为空!!!");
        }
        try {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                throw new UnknownAccountException();
            }
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        } catch (UnknownAccountException e) {
            throw e;
        } catch (RuntimeException e) {
            LOG.error("User login error, username: [{}], message: {}", username, e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
