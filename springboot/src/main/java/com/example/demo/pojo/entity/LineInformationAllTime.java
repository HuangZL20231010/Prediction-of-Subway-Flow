package com.example.demo.pojo.entity;

import wniemiec.util.data.Pair;
import java.util.List;

// 本实体用于区分LineInformation，存储线路一整天所有时间点的数据
public class LineInformationAllTime
{
    String lineName;    // 线路名称
    String Date;    // 时间，主要存储年月日
    List<Pair<String, String>> inNumAllTime;    // 存储该天所有时间点的客运量

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineName() {
        return lineName;
    }

    public List<Pair<String, String>> getInNumAllTime() {
        return inNumAllTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setInNumAllTime(List<Pair<String, String>> inNumAllTime) {
        this.inNumAllTime = inNumAllTime;
    }

}
