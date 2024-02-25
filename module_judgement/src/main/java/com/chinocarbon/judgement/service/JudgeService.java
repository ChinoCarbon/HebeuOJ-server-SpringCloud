package com.chinocarbon.judgement.service;

import com.chinocarbon.judgement.pojo.Judgement;
import com.chinocarbon.judgement.pojo.Result;

import java.io.IOException;

/**
 * @author ChinoCarbon
 * @since 2022/5/15-3:27 PM
 */
public interface JudgeService
{
    Result judge(Judgement judgement, String absoluteConfigFilePath, String absoluteFilePath,
                 String absoluteJudgementPath, String absoluteCompileMachinePath, String absoluteJudgeMachinePath) throws IOException;

    int getJudgementId(Judgement judgement);
}
