package com.example.demo.service;

import com.example.demo.pojo.table.StationDetail;

import java.util.List;

public interface StationDetailService
{
    // 根据线路名称检索该条线路上的所有站点
    List<StationDetail> selectByLineName(String lineName);

    //
}
