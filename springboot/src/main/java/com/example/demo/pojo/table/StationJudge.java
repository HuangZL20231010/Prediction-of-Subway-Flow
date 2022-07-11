package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationjudge")
@Data
public class StationJudge
{
    Integer stationid;
    String carcapcity;
    String morningtime;
    String eveningtime;
    String othertime;

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

    public Integer getStationid() {
        return stationid;
    }

    public String getCarcapcity() {
        return carcapcity;
    }

    public String getEveningtime() {
        return eveningtime;
    }

    public String getMorningtime() {
        return morningtime;
    }

    public String getOthertime() {
        return othertime;
    }

    public void setCarcapcity(String carcapcity) {
        this.carcapcity = carcapcity;
    }

    public void setEveningtime(String eveningtime) {
        this.eveningtime = eveningtime;
    }

    public void setMorningtime(String morningtime) {
        this.morningtime = morningtime;
    }

    public void setOthertime(String othertime) {
        this.othertime = othertime;
    }

}
