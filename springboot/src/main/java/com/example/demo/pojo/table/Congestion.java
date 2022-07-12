package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("congestion")
@Data
public class Congestion
{
    Integer stationid;
    String time;
    Integer innum;
    Integer outnum;
    Integer flow;
    Integer allflow;

    public Integer getStationid() {
        return stationid;
    }

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getInnum() {
        return innum;
    }

    public Integer getOutnum() {
        return outnum;
    }

    public void setInnum(Integer innum) {
        this.innum = innum;
    }

    public void setOutnum(Integer outnum) {
        this.outnum = outnum;
    }

    public Integer getAllflow() {
        return allflow;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setAllflow(Integer allflow) {
        this.allflow = allflow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }


}
