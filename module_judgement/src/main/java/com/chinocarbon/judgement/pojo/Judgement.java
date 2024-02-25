package com.chinocarbon.judgement.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ChinoCarbon
 * @since 2022/5/8-7:48 PM
 */
@Getter
@Setter
@ToString
public class Judgement implements Serializable
{
    private int judgementId;
    private int userId;
    private int problemId;
    private int languageType;
    private String code;
    private String judgementTime;

    private Result result;

    public Judgement()
    {
        judgementTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).toString();
    }
}
