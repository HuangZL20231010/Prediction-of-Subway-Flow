package com.example.demo.service;

import wniemiec.util.data.Pair;

import java.util.List;

public interface CongestionService
{
    // 传入站点id和时间，返回计算得到的拥堵系数
    double calculateCongestionFactor(Integer stationID, String time);

    // 传入线路id和时间，返回该线路所有站点的拥堵系数
    List<Pair<Integer, Double>> getStationsCongestionInLine(Integer lineID, String time);
}
