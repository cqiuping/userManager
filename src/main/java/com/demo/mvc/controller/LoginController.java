package com.demo.mvc.controller;

import com.demo.mvc.entity.JsonResp;
import com.demo.mvc.entity.User;
import com.demo.mvc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/12.
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/views/login.html";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String success(){
        return "/views/main.html";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResp login(String username, String password) {
        System.err.println(username + ":" + password);
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            return JsonResp.fail("账号或密码不能为空");
//            return "失败";
//            result.put("status", "失败");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            Subject currentUser = SecurityUtils.getSubject();
            SecurityUtils.getSubject().login(token);
//            SecurityUtils.getSubject().getSession();
            System.err.println("登陆成功");
            token.clear();
            User user = userService.findUserByUsername(username);
            return JsonResp.succ(user);
        } catch (UnknownAccountException e) {
            return JsonResp.fail("账号或密码错误1");
        } catch (LockedAccountException e) {
            return JsonResp.fail("账号已被锁定");
        } catch (AuthenticationException e) {
            System.err.println("登陆失败");
            return JsonResp.fail("账号或密码错误2");
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResp.fail("错误");
        }
    }
}
