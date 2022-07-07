package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationflow")
@Data
public class StationFlow
{
    @TableId(value = "stationID")
    String stationID;
    String inNum;
    String outNum;
    String time;
}
