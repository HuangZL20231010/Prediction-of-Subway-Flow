package com.example.demo.service;

import com.example.demo.pojo.table.StationDetail;
import wniemiec.util.data.Pair;

import java.util.List;

public interface StationDetailService
{
    // 检索数据库中所有的线路名称
    List<Integer> selectLinesName();
    // 根据线路名称检索该条线路上的所有站点
    List<StationDetail> selectByLineName(Integer lineName);

    // 根据站点信息链表中的站点id，查询对应的每一个站点的经纬度并返回对应链表
    List<Pair<String, String>> selectLocationList(List<StationDetail> stationDetailList);
}
