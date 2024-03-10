package com.chinocarbon.judgement.service;

import com.chinocarbon.judgement.pojo.Judgement;
import com.chinocarbon.judgement.pojo.Result;

import java.io.IOException;
import java.util.Map;

/**
 * @author ChinoCarbon
 * @since 2022/5/15-3:27 PM
 */
public interface JudgeService
{
    Result judge(Judgement judgement, Map<String, String> judgeInfo) throws IOException;

    int getJudgementId(Judgement judgement);
}
