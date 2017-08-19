package com.demo.mvc.controller;

import com.demo.mvc.entity.JsonResp;
import com.demo.mvc.entity.Role;
import com.demo.mvc.entity.User;
import com.demo.mvc.service.RoleService;
import com.demo.mvc.service.UserService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 0427 on 2017/8/14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public JsonResp findAllUser(){
       List<User> users = userService.findAllUsers();
       return JsonResp.succ(users);
    }
    @ResponseBody
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public JsonResp delUser(String uid){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isPermitted("userInfo:delUser")){
            return JsonResp.fail("你没有权限");
        }
       try{
//                System.err.println("****uid****" + uid);
           userService.delRealUserInfo(Integer.parseInt(uid));
       }catch (Exception e){
           return JsonResp.fail("删除失败");
       }
        return JsonResp.succ("删除成功");
    }

    @ResponseBody
    @RequestMapping(value = "/getRealUserById", method = RequestMethod.POST)
    public JsonResp getRealUserById(String uid){
        try{
            int id = Integer.parseInt(uid);
            User user = userService.findUserById(id);
            Role role = roleService.findRoleByUid(id);
            List<Role> roles = roleService.findAllRoles();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("user", user);
            data.put("role", role);
            data.put("roles", roles);
            return JsonResp.succ(data);
        }catch (Exception e){
            return JsonResp.fail("查询用户信息失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public JsonResp editUser(String username, String password,
                            String age, String role, String id){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isPermitted("userInfo:editUser")){
            return JsonResp.fail("你没有权限");
        }
        try{
            int uid = Integer.parseInt(id);
//            role = role.trim();

            System.err.println("****role****" + role);
            User user = new User();
            user.setAge(Integer.parseInt(age));
            user.setPassword(password);
            user.setUsername(username);
            user.setId(uid);

            userService.updateRealUserInfo(user, role);
            return JsonResp.succ("更新成功");
        }catch (Exception e){
            return JsonResp.fail("更新失败");
        }

    }


    @ResponseBody
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public JsonResp addUser(String username, String password,
                            String age, String role){
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isPermitted("userInfo:addUser")){
            return JsonResp.fail("你没有权限");
        }
        try {
//            String str= new String(role.getBytes("ISO-8859-1"),"UTF-8");
//            httpServletRequest.setCharacterEncoding("UTF-8");
//            String newRole = URLDecoder.decode(role,"UTF-8");
            System.err.println("***role**" + role);
            role = role.trim();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAge(Integer.parseInt(age));

            userService.addRealUserInfo(user, role);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            return JsonResp.succ();
        }catch (PersistenceException e){
           return JsonResp.fail("系统已经存在这个用户名，请重新命名~");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResp.fail("添加失败");
        }

    }
}
