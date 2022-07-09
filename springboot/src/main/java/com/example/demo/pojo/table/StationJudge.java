package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationjudge")
@Data
public class StationJudge
{
    String stationid;
    String carcapcity;
    String morningtime;
    String eveningtime;
    String othertime;
}
