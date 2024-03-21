package com.chinocarbon.judgement.controller;

import com.chinocarbon.judgement.Utils.MySerializeUtil;
import com.chinocarbon.judgement.core.LanguageType;
import com.chinocarbon.judgement.pojo.Judgement;
import com.chinocarbon.judgement.pojo.Result;
import com.chinocarbon.judgement.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author ChinoCarbon
 * @since 2022/5/8-7:37 PM
 */
@RestController
@CrossOrigin
@RequestMapping("judge")
public class JudgeController {
    private JudgeService judgeService;

    @Autowired
    public void setJudgeService(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @RequestMapping("getJudgementId")
    public Integer getJudgementId(@RequestBody Judgement submitMessage, HttpServletResponse response, HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("judgement", submitMessage);
        int p = judgeService.getJudgementId(submitMessage);
        System.out.println(p);
        return p;
    }

    @PostMapping("judgement")
    public Result readyToJudge(@RequestBody Judgement submitMessage, HttpServletRequest request) throws IOException, ClassNotFoundException {
            submitMessage.setJudgementId(Integer.parseInt(request.getParameter("id")));
        //        if (new File(request.getServletContext().getRealPath(File.separator + "judgements" + File.separator + id + File.separator + "result.txt")).isFile()) {
//            Object obj = MySerializeUtil.myDeserialize(request.getServletContext().getRealPath(File.separator + "judgements" + File.separator + id + File.separator + "result.txt"));
//            if (obj instanceof Result) {
//                Result result = (Result) obj;
//                System.out.println("反序列化之后的对象：" + request);
//                return result;
//            }
//        }
        Map<String, String> judgeInfo = (Map<String, String>) request.getServletContext().getAttribute("judgeInfo");
      //  Judgement submitMessage = (Judgement) request.getSession().getAttribute("judgement");
        if (LanguageType.getType(submitMessage.getLanguageType()) == null) return null;
   //     if (id == submitMessage.getJudgementId()) {
            return judgeService.judge(submitMessage, judgeInfo);
   //     } else return null;
    }

}
