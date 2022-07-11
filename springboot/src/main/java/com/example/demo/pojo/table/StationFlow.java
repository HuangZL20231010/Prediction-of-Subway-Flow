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

    public void setOutnum(Integer outnum) {
        this.outnum = outnum;
    }

    public void setInnum(Integer innum) {
        this.innum = innum;
    }

    public Integer getOutnum() {
        return outnum;
    }

    public Integer getInnum() {
        return innum;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Integer getStationid() {
        return stationid;
    }

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

}
