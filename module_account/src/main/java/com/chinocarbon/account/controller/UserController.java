package com.chinocarbon.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinocarbon.account.Utils.EncodeUtils;
import com.chinocarbon.account.dao.UserDao;
import com.chinocarbon.account.pojo.Classes;
import com.chinocarbon.account.pojo.User;
import com.chinocarbon.account.Utils.EncodeUtils;
import com.chinocarbon.account.dao.UserDao;
import com.chinocarbon.account.pojo.Classes;
import com.chinocarbon.account.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

/**
 * @author ChinoCarbon
 * @since 2022/5/11-9:22 AM
 */
@CrossOrigin
@RestController
public class UserController
{
    UserDao userDao;
    AuthService authService;

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/getIco")
    public void getIco(@RequestParam int id, HttpServletResponse response, HttpServletRequest request)
    {
        System.out.println(request.getServletContext().getAttribute("absoluteIcoPath") + "/" + id);
        File parentFile = new File(request.getServletContext().getAttribute("absoluteIcoPath") + "/" + id);
        System.out.println(parentFile.exists());
        System.out.println(parentFile.getAbsolutePath());
        File ico = Objects.requireNonNull(parentFile.listFiles())[0];
        String[] names = ico.getName().split(" ");
        if("png".equalsIgnoreCase(names[names.length - 1]))
        {
            response.setContentType("image/png");
        } else
        {
            response.setContentType("image/jpeg");
        }
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(ico);
            byte[] b = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(b)) != -1)
            {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if(fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("selectUserById")
    public User getSingleUser(@RequestBody User user)
    {
        return userDao.selectUserById(user.getUserId());
    }

    @RequestMapping("selectUserByClassId")
    public List<User> getSingleUserByClassId(@RequestBody Classes aclass)
    {
        return userDao.selectUserByClassId(aclass.getClassId());
    }
}
