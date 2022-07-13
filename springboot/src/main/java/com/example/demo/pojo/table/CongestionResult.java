package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("congestionresult")
@Data
public class CongestionResult
{

    Integer stationid;
    String time;
    Double congestionlevel;
    Double congestionnormalization;

    public Integer getStationid() {
        return stationid;
    }

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Double getCongestionlevel() {
        return congestionlevel;
    }

    public void setCongestionlevel(Double congestionlevel) {
        this.congestionlevel = congestionlevel;
    }

    public Double getCongestionnormalization() {
        return congestionnormalization;
    }

    public void setCongestionnormalization(Double congestionnormalization) {
        this.congestionnormalization = congestionnormalization;
    }


}
