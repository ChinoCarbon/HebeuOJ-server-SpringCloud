package com.chinocarbon.account.service.impl;

import com.chinocarbon.account.dao.UserDao;
import com.chinocarbon.account.enums.AccountStatus;
import com.chinocarbon.account.pojo.User;
import com.chinocarbon.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChinoCarbon
 * @since 2022/5/12-4:35 PM
 */
@Service
public class AccountServiceImpl implements AccountService
{
    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public AccountStatus checkLogIn(User user)
    {

        System.out.println(user);
        User returnUser = userDao.selectUserByUserName(user.getUserName());
        if(returnUser == null)
        {
            return AccountStatus.USERNAME_NOT_EXIST;
        } else
        {
            returnUser = userDao.selectUserByUserNameAndPassword(user);
            System.out.println(returnUser);
            if(returnUser == null)
            {
                return AccountStatus.WRONG_PASSWORD;
            } else
            {
                user.setUserId(returnUser.getUserId());
                return AccountStatus.SUCCESS;
            }
        }
    }

    @Override
    public AccountStatus checkRegister(User user)
    {
        User returnUser = userDao.selectUserByEmail(user.getUserEmail());
        if(returnUser != null)
        {
            return AccountStatus.EMAIL_EXIST;
        }

        returnUser = userDao.selectUserByUserName(user.getUserName());
        if(returnUser != null)
        {
            return AccountStatus.USERNAME_EXIST;
        }

        System.out.println(user);
        if(userDao.insertAUser(user) != 0)
        {
            return AccountStatus.SUCCESS;
        } else return AccountStatus.FAILED;
    }
}
