package com.chinocarbon.judgement.pojo;

import com.chinocarbon.judgement.enums.PointStatement;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author ChinoCarbon
 * @since 2022/4/29-8:18 PM
 */

@Getter
public class PointMessage implements Serializable
{
    private int id;
    private final PointStatement statement;
    private final long timeConsumed;
    private final long memoryConsumed;
    private final String errorMessage;

    @Override
    public String toString()
    {
        return "PointMessage{" +
                "id=" + id +
                ", statement=" + statement +
                ", timeConsumed=" + timeConsumed +
                ", memoryConsumed=" + memoryConsumed +
                '}';
    }

    public PointMessage(int id, PointStatement statement, long timeConsumed, long memoryConsumed, String errorMessage)
    {
        this.id = id;
        this.errorMessage = errorMessage;
        this.statement = statement;
        this.timeConsumed = timeConsumed;
        this.memoryConsumed = memoryConsumed;
    }

}
