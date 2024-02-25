package com.chinocarbon.judgement.Utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class R {
    @Getter
    private int code;
    @Getter
    private String message;
    private Map<String, Object> data;

    public R(int code) {
        this.code = code;
        this.message = message;
        this.data = new HashMap<>();
    }

    public Object getData() {
        return data;
    }

    public static R success() {
        return new R(0);
    }

    public static R failure() {
        return new R(1);
    }

    public R message(String msg) {
        this.message = msg;
        return this;
    }

    public R singleData(String key, Object value)
    {
        this.data.put(key, value);
        return this;
    }
}
