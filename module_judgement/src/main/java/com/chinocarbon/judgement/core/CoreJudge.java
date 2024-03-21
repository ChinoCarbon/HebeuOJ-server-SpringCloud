package com.chinocarbon.judgement.core;

import com.chinocarbon.judgement.pojo.PointMessage;
import com.chinocarbon.judgement.enums.PointStatement;
import com.chinocarbon.judgement.pojo.PointMessage;
import com.chinocarbon.judgement.pojo.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author ChinoCarbon
 * @since 2022/4/30-4:32 PM
 */
public class CoreJudge
{
    private LanguageType languageType;
    private  int maxTime;
    private int maxMemory;
    private int problemNum;
    private int judgementId;
    private Map<String, String> judgeInfo;

    public CoreJudge(LanguageType languageType, Map<String, String> judgeInfo,
                     int maxTime, int maxMemory, int problemNum, int judgementId)
    {
        this.languageType = languageType;
        this.maxTime = maxTime;
        this.maxMemory = maxMemory;
        this.problemNum = problemNum;
        this.judgementId = judgementId;
        this.judgeInfo = judgeInfo;
    }

    private void coreJudge(Result result, ProcessBuilder processBuilder) throws IOException
    {
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String singleLine = null;
        int tot = 0;

        StringBuilder message = new StringBuilder();
        while((singleLine = br.readLine()) != null)
        {
            System.out.println(singleLine);
            if(singleLine.equals("start judge")) continue;
            if(singleLine.length() < 3 || !"#&#".equals(singleLine.substring(0, 3))) {
                message.append(singleLine);
                continue;
            }
            String[] results = singleLine.split(" ");
            result.addAMessage(
                    new PointMessage(
                            ++tot,
                            PointStatement.getStatementByString(results[1]),
                            Long.parseLong(results[2]),
                            123,
                            message.toString()
                    )
            );
            message = new StringBuilder();
        }
    }
    private void judgeJava(Result result) throws IOException
    {
        System.out.println("start judge");
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                judgeInfo.get("absoluteJudgeMachinePath") + File.separator + "JudgeJava.py",
                String.valueOf(maxTime), String.valueOf(problemNum), String.valueOf(judgementId),
                judgeInfo.get("absoluteFilePath"),
                judgeInfo.get("absoluteJudgementPath")
        );
        coreJudge(result, processBuilder);
    }

    private void judgeC(Result result) throws IOException
    {
        System.out.println("start judge");
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                judgeInfo.get("absoluteJudgeMachinePath") + File.separator + "JudgeC.py",
                String.valueOf(maxTime), String.valueOf(problemNum), String.valueOf(judgementId),
                judgeInfo.get("absoluteFilePath"),
                judgeInfo.get("absoluteJudgementPath")
        );
        coreJudge(result, processBuilder);
    }

    private void judgeCPP(Result result) throws IOException
    {
        System.out.println("start judge");
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                judgeInfo.get("absoluteJudgeMachinePath") + File.separator + "JudgeCPP.py",
                String.valueOf(maxTime), String.valueOf(problemNum), String.valueOf(judgementId),
                judgeInfo.get("absoluteFilePath"),
                judgeInfo.get("absoluteJudgementPath")
        );
        coreJudge(result, processBuilder);
    }

    private void judgePython(Result result) throws IOException
    {
        System.out.println("start judge");
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                judgeInfo.get("absoluteJudgeMachinePath") + File.separator + "JudgePython.py",
                String.valueOf(maxTime), String.valueOf(problemNum), String.valueOf(judgementId),
                judgeInfo.get("absoluteFilePath"),
                judgeInfo.get("absoluteJudgementPath")
        );
        coreJudge(result, processBuilder);
    }

    public void judge(Result result) throws IOException
    {
        switch (this.languageType)
        {
            case C -> judgeC(result);
            case CPP -> judgeCPP(result);
            case Java -> judgeJava(result);
            case Py -> judgePython(result);
        }
    }

}
