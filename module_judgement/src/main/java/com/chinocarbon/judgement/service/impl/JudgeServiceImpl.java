package com.chinocarbon.judgement.service.impl;

import com.chinocarbon.judgement.Utils.MySerializeUtil;
import com.chinocarbon.judgement.core.CoreCompile;
import com.chinocarbon.judgement.core.CoreJudge;
import com.chinocarbon.judgement.core.LanguageType;
import com.chinocarbon.judgement.dao.JudgeDao;
import com.chinocarbon.judgement.dao.ProblemDao;
import com.chinocarbon.judgement.dao.UserDao;
import com.chinocarbon.judgement.enums.PointStatement;
import com.chinocarbon.judgement.pojo.Judgement;
import com.chinocarbon.judgement.pojo.PointMessage;
import com.chinocarbon.judgement.pojo.Result;
import com.chinocarbon.judgement.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author ChinoCarbon
 * @since 2022/5/15-3:27 PM
 */
@Service
public class JudgeServiceImpl implements JudgeService
{
    JudgeDao judgeDao;
    ProblemDao problemDao;
    UserDao userDao;

    @Autowired
    public void setJudgeDao(JudgeDao judgeDao)
    {
        this.judgeDao = judgeDao;
    }
    @Autowired
    public void setProblemDao(ProblemDao problemDao)
    {
        this.problemDao = problemDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public Result judge(Judgement judgement, Map<String, String> judgeInfo) throws IOException
    {

        File file = new File( judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId());
        System.out.println(judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId());
        System.out.println(file.mkdir());

        String fileBack = LanguageType.getType(judgement.getLanguageType()).toString().toLowerCase(Locale.ROOT);
        FileWriter fileWriter = new FileWriter(judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId() + File.separator + "Main." + fileBack);
        fileWriter.write(judgement.getCode());
        fileWriter.close();

        Result result = new Result(judgement);

        CoreCompile coreCompile = new CoreCompile(
                LanguageType.getType(judgement.getLanguageType()),
                judgeInfo.get("absoluteFilePath") + File.separator + judgement.getProblemId(),
                judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId(),
                judgeInfo.get("absoluteCompileMachinePath")
        );

        coreCompile.compile(result);
        problemDao.addOne(judgement.getProblemId());
        userDao.addOne(judgement.getUserId());
        if(result.isCE())
        {
            MySerializeUtil.mySerialize(result, judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId() + File.separator + "result.txt");
            System.out.println("序列化原始对象完成！OK！");
            return result;
        }
        CoreJudge coreJudge = new CoreJudge(
                LanguageType.getType(judgement.getLanguageType()),
                judgeInfo,
                2000,
                128,
                judgement.getProblemId(),
                judgement.getJudgementId()
        );
        coreJudge.judge(result);
        List<PointMessage> list = result.getMessages();
        boolean f = false;
        for (PointMessage pointMessage : list)
        {
            if (pointMessage.getStatement() != PointStatement.ACCEPTED)
            {
                f = true;
                break;
            }
        }
        if(!f)
        {
            problemDao.addPassedOne(judgement.getProblemId());
            userDao.addPassOne(judgement.getUserId());
            problemDao.addAPass(judgement.getUserId(), judgement.getProblemId());
        }
        MySerializeUtil.mySerialize(result, judgeInfo.get("absoluteJudgementPath") + File.separator + judgement.getJudgementId() + File.separator + "result.txt");
        System.out.println("序列化原始对象完成！OK！");
        return result;
    }

    @Override
    public int getJudgementId(Judgement judgement)
    {
        judgeDao.insertARecord(judgement);
        return judgement.getJudgementId();
    }
}
