package com.example.demo.pojo.entity;

import com.example.demo.pojo.table.StationDetail;
import com.sun.tools.javac.util.Pair;

import java.util.List;

// 该类存储有关特定地铁线路的信息
public class LineInformation
{
    private String time; // 时间
    private List<StationDetail> stationDetailList;  // 该线路上的站点信息
    private List<Pair<String, String>> ll;  // 链表，每个节点存储pair类型的经纬度
}
