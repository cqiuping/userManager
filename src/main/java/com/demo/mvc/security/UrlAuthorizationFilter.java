/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. 
 *             All rights reserved                         
 */
package com.demo.mvc.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:liqihui@wxchina.com">Qihui.Li</a>
 * @version 1.0.0
 * @date Aug 1, 2016
 */
public class UrlAuthorizationFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse, Object mappedValue)  {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            resp.setHeader("Access-State", "login");//这个可以重定向到login页面
            return false;
        }

        String url = req.getServletPath();
//        subject.hasRole()

        return subject.isPermitted(url);
    }

}
