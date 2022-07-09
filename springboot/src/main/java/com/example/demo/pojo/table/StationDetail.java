package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationdetail")
@Data
public class StationDetail
{
    String stationid;
    String longitude;
    String latitude;
    String stationname;
    String linename;
}
