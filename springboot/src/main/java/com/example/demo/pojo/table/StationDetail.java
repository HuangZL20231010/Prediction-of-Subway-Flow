package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationdetail")
@Data
public class StationDetail
{
    @TableId(value = "stationID")
    String stationid;
    String longitude;
    String latitude;
    String stationname;
    String linename;
}
