package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationflow")
@Data
public class StationFlow
{
    String stationid;
    String innum;
    String outnum;
    String time;
}
