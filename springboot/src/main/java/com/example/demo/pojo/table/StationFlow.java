package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationflow")
@Data
public class StationFlow
{
    Integer stationid;
    Integer innum;
    Integer outnum;
    String time;
}
