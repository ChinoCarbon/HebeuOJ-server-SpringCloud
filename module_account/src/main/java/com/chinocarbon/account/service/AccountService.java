package com.chinocarbon.account.service;

import com.chinocarbon.account.enums.AccountStatus;
import com.chinocarbon.account.pojo.User;

/**
 * @author ChinoCarbon
 * @since 2022/5/12-4:35 PM
 */
public interface AccountService
{
    AccountStatus checkLogIn(User user);
    AccountStatus checkRegister(User user);
}
