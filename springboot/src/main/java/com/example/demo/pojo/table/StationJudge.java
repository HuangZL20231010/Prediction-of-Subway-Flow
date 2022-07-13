package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("stationjudge")
@Data
public class StationJudge
{
    Integer stationid;
    Integer carcapacity;
    Integer morningtime;
    Integer eveningtime;
    Integer othertime;

    public Integer getCarcapacity() {
        return carcapacity;
    }

    public void setCarcapacity(Integer carcapacity) {
        this.carcapacity = carcapacity;
    }

    public void setStationid(Integer stationid) {
        this.stationid = stationid;
    }

    public Integer getStationid() {
        return stationid;
    }

    public void setOthertime(Integer othertime) {
        this.othertime = othertime;
    }

    public void setMorningtime(Integer morningtime) {
        this.morningtime = morningtime;
    }

    public void setEveningtime(Integer eveningtime) {
        this.eveningtime = eveningtime;
    }

    public Integer getEveningtime() {
        return eveningtime;
    }

    public Integer getMorningtime() {
        return morningtime;
    }

    public Integer getOthertime() {
        return othertime;
    }
}

