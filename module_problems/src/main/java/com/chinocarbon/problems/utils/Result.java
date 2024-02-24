package com.chinocarbon.problems.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

interface ResultCode {
    Integer SUCCESS = 20000; //成功
    Integer ERROR = 20001;   //失败
}

@Data
@Accessors(chain = true)  //链式编程注解
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<String,Object>();

    //构造方法私有化,使其他类不能new 只能使用类中固定的方法
    private Result(){}

    //成功静态方法
    public static Result succeed(){
        Result resultReturn = new Result();
        resultReturn
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS) //直接引用状态码接口
                .setMessage("执行成功");
        return resultReturn;
    }

    //失败静态方法
    public static Result error(){
        Result resultReturn = new Result();
        resultReturn.setSuccess(false);
        resultReturn.setCode(ResultCode.ERROR); //直接引用状态码接口
        resultReturn.setMessage("执行失败");
        return resultReturn;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result Data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public Result Data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
