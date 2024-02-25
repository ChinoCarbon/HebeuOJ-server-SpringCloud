package com.chinocarbon.judgement.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author ChinoCarbon
 * @since 2022/5/8-7:47 PM
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Problem
{
    @TableId
    private Integer problemId;
    private String problemTitle;
    private int problemSubmitted;
    private int problemPassed;
    private double problemTimeLimit;
    private String problemContent;
}
