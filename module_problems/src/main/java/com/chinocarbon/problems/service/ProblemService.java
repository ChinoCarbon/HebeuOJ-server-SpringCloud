package com.chinocarbon.problems.service;

import com.chinocarbon.problems.pojo.SinglePage;
import com.github.pagehelper.PageInfo;
import com.chinocarbon.problems.pojo.Problem;
import com.chinocarbon.problems.pojo.SinglePage;

/**
 * @author ChinoCarbon
 * @since 2022/5/13-2:48 PM
 */
public interface ProblemService
{
    PageInfo<Problem> findAllByPages(SinglePage singlePage);
    PageInfo<Problem> findSomeByKeyWord(SinglePage singlePage);

    int createAProblem(Problem problem);
    boolean isPass(int userId, int problemId);
}
