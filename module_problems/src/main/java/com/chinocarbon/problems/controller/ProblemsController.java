package com.chinocarbon.problems.controller;

import com.chinocarbon.problems.dao.ProblemDao;
import com.chinocarbon.problems.enums.AccountStatus;
import com.chinocarbon.problems.pojo.Problem;
import com.chinocarbon.problems.pojo.SinglePage;
import com.chinocarbon.problems.service.ProblemService;
import com.chinocarbon.problems.utils.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("problem")
public class ProblemsController
{
    private ProblemService problemService;

    private ProblemDao problemDao;

    @Autowired
    @Qualifier("problemServiceImpl")
    public void setProblemService(ProblemService problemService)
    {
        this.problemService = problemService;
    }

    @Autowired
    public void setProblemDao(ProblemDao problemDao)
    {
        this.problemDao = problemDao;
    }

    @PostMapping("/ProblemsH")
    public String addAProblem(@RequestBody Problem problem, HttpServletRequest request) throws IOException
    {
        try
        {
            problemService.createAProblem(problem);
            File file = new File(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + problem.getProblemId());
            System.out.println(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + problem.getProblemId());
            System.out.println(file.mkdir());
            FileWriter fileWriter = new FileWriter(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + problem.getProblemId() + File.separator + "content.md");
            System.out.println(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + problem.getProblemId() + File.separator + "content.md");
            fileWriter.write(problem.getProblemContent());
            fileWriter.close();
            return AccountStatus.SUCCESS.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return AccountStatus.FAILED.toString();
        }
    }

    @GetMapping("/problemContent/{id}")
    public String getProblemContent(@PathVariable int id, HttpServletRequest request) throws IOException
    {
        try
        {
            FileReader reader = new FileReader(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + id + File.separator + "content.md");
            System.out.println(request.getServletContext().getAttribute("absoluteFilePath") + File.separator + id + File.separator + "content.md");
            StringBuilder stringBuilder = new StringBuilder();
            char[] buf = new char[1024];
            int len;
            while((len = reader.read(buf)) != -1)
            {
                stringBuilder.append(buf, 0, len);
            }
            System.out.println(stringBuilder);
            return stringBuilder.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/problemsH")
    public Problem findProblemById(@RequestBody Problem problem)
    {
        System.out.println(problem);
        return problemDao.selectById(problem.getProblemId());
        //  return problemDao.getProblemInformation(problem);
    }

    @PostMapping("/abcde")
    public List<Problem> findProblemByIdff(@RequestBody Problem problem)
    {
        return problemDao.selectList(null);
        //  return problemDao.getProblemInformation(problem);
    }

    @PostMapping("/findAllByPages")
    public Result getAllProblems(@RequestBody SinglePage singlePage)
    {
        return Result.succeed().Data("listInformation", problemService.findAllByPages(singlePage));
    }

    @PostMapping("/findLikeByPages")
    public Result getSomeProblems(@RequestBody SinglePage singlePage)
    {
        return Result.succeed().Data("listInformation", problemService.findSomeByKeyWord(singlePage));
    }

    @PostMapping("/getProblemInformation")
    public Problem getProblemInformation(@RequestBody Problem problem)
    {
        System.out.println(problem);
        //return problemDao.getProblemInformation(problem);
        return problemDao.selectById(problem.getProblemId());
    }

    @PostMapping("/uploadInOut")
    public String uploadInOut(@RequestParam String problemId, MultipartFile file, HttpServletRequest request) throws IOException
    {
        String uploadPath = request.getServletContext().getRealPath("/problems/" + problemId + "/data");

        File saveFile = new File(uploadPath);
        if(!saveFile.exists()) {
            saveFile.mkdir();
        }
        File[] files = saveFile.listFiles();
        for(File file1: files)
        {
            file1.delete();
        }

        if(!saveFile.exists()) {
            saveFile.mkdir();
        }
        String dateTime = String.valueOf(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        String saveName = file.getOriginalFilename();
        String savePathName = uploadPath + "/" + saveName;
        File result = new File(savePathName);
        file.transferTo(result);
        return AccountStatus.SUCCESS.toString();
    }

    @PostMapping("/uploadImage")
    public String uploadImage(MultipartFile file, HttpServletRequest request) throws IOException
    {
        String uploadPath = request.getServletContext().getRealPath("/img");
        File saveFile = new File(uploadPath);
        if(!saveFile.exists()) {
            saveFile.mkdir();
        }
        String dateTime = String.valueOf(LocalDateTime.now());
        String uuid = UUID.randomUUID().toString();
        String saveName = dateTime + "-" + uuid + "-" + file.getOriginalFilename();
        String savePathName = uploadPath + "/" + saveName;
        File result = new File(savePathName);
        file.transferTo(result);
        return request.getServletContext().getContextPath() + "/img/" + saveName;
    }

    @GetMapping("isPass")
    public boolean isPass(@RequestParam int userId, @RequestParam int problemId)
    {
        return problemService.isPass(userId, problemId);
    }
}
