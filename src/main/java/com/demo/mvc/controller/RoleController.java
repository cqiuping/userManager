package com.demo.mvc.controller;

import com.demo.mvc.entity.JsonResp;
import com.demo.mvc.entity.Role;
import com.demo.mvc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0427 on 2017/8/14.
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/allRoles", method = RequestMethod.GET)
    @ResponseBody
    public JsonResp allRolenames(){
        List<String> roleNames = new ArrayList<String>();
       try {
           List<Role> roles = roleService.findAllRoles();
           for(int i = 0; i<roles.size(); i++){
               roleNames.add(roles.get(i).getName());
           }
           return JsonResp.succ(roleNames);
       }catch (Exception e){
           return JsonResp.fail("查询角色失败");
       }
    }
}
