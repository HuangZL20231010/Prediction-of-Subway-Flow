package com.example.demo.pojo.entity;

import com.example.demo.pojo.table.StationDetail;
import wniemiec.util.data.Pair;

import java.util.List;

// 该类存储有关特定地铁线路的信息
public class LineInformation
{
    private Integer lineName;    // 该线路的名称
    private String time; // 时间
    private List<StationDetail> stationDetailList;  // 该线路上的站点信息
    private List<Pair<String, String>> ll;  // 链表，每个节点存储pair类型的经纬度
    private Integer inNum;   // 该线路在该时间节点上的总进入流量
    private Integer outNum;  // 该线路在该时间节点上的总出站流量

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Pair<String, String>> getLl() {
        return ll;
    }

    public List<StationDetail> getStationDetailList() {
        return stationDetailList;
    }

    public void setLl(List<Pair<String, String>> ll) {
        this.ll = ll;
    }

    public void setStationDetailList(List<StationDetail> stationDetailList) {
        this.stationDetailList = stationDetailList;
    }

    public Integer getInNum() {
        return inNum;
    }

    public Integer getLineName() {
        return lineName;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public void setInNum(Integer inNum) {
        this.inNum = inNum;
    }

    public void setLineName(Integer lineName) {
        this.lineName = lineName;
    }

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

}