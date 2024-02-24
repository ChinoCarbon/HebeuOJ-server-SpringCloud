package com.chinocarbon.account.enums;

import org.springframework.ui.context.ThemeSource;

/**
 * @author ChinoCarbon
 * @since 2022/2/28-9:26 AM
 */
public enum AccountStatus
{

    SUCCESS("成功"),

    USERNAME_NOT_EXIST("用户名不存在"),

    WRONG_PASSWORD("密码错误"),
    WRONG_CHECK_CODE("验证码错误"),

    EMAIL_EXIST("邮箱已存在"),
    USERNAME_EXIST("用户名已存在"),

    FAILED("未知失败原因");

    private String message;
    AccountStatus(String m) {
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }
}
