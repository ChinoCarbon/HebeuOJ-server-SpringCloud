package com.chinocarbon.judgement.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import com.chinocarbon.judgement.pojo.Judgement;

/**
 * @author ChinoCarbon
 * @since 2022/5/15-3:39 PM
 */
@Mapper
public interface JudgeDao
{
    @Insert("insert into HebeuOJ.tbl_judgement (judgement_time, judgement_user_id, judgement_problem_id)\n" +
            "        value (#{judgementTime}, #{userId}, #{problemId})")
    @Options(useGeneratedKeys = true, keyProperty = "judgementId")
    void insertARecord(Judgement judgement);
}
