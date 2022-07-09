package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationflow")
@Data
public class StationFlow
{
    int stationid;
    int innum;
    int outnum;
    String time;
}
