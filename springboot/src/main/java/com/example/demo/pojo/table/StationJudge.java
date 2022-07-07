package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationjudge")
@Data
public class StationJudge
{
    @TableId(value = "stationID")
    String stationID;
    String carCapcity;
    String morningTime;
    String eveningTime;
    String otherTime;
}
