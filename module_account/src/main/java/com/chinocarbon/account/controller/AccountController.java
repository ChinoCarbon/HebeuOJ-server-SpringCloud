package com.chinocarbon.account.controller;

import com.chinocarbon.account.Utils.*;
import com.chinocarbon.account.enums.AccountStatus;
import com.chinocarbon.account.service.AccountService;
import com.chinocarbon.account.Utils.EncodeUtils;
import com.chinocarbon.account.pojo.User;
import com.chinocarbon.account.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ChinoCarbon
 * @since 2022/5/12-4:34 PM
 */

@RestController
@CrossOrigin
@RequestMapping("auth")
public class AccountController {
    private AccountService accountService;

    private AuthService authService;

    @Autowired
    public void setLogInService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/checkLogIn")
    public Result checkLogIn(@RequestBody User user, HttpServletRequest request) {
        //todo 暂时不启用验证码
//        System.out.println(user.getCheckCode());
//        System.out.println(request.getSession().getAttribute("LogInCheckCode"));
//        if ("".equals(user.getCheckCode()) || !user.getCheckCode().equals(request.getSession().getAttribute("LogInCheckCode"))) {
//            return Result.error().message(AccountStatus.WRONG_CHECK_CODE.getMessage());
//        }
        AccountStatus accountStatus = accountService.checkLogIn(user);
        if (accountStatus == AccountStatus.SUCCESS) {
            return Result.succeed().message(authService.createToken(String.valueOf(user.getUserId())));
        }
        return Result.error().message(accountStatus.getMessage());
    }

    @RequestMapping("/checkRegister")
    public Result checkRegister(@RequestBody User user, HttpServletRequest request) {
        //todo 暂时不启用验证码
//        if ("".equals(user.getCheckCode()) || !user.getCheckCode().equals(request.getSession().getAttribute("RegisterCheckCode"))) {
//            return Result.error().message(AccountStatus.WRONG_CHECK_CODE.getMessage());
//        }
        AccountStatus accountStatus = accountService.checkRegister(user);
        if (accountStatus != AccountStatus.SUCCESS) {
            return Result.error().message(accountStatus.getMessage());
        } else return Result.succeed().message(accountStatus.getMessage());
    }
}
