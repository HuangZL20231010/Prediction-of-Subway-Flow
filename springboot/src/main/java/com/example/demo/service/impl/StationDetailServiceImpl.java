package com.example.demo.service.impl;

import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.service.StationDetailService;
import com.sun.tools.javac.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationDetailServiceImpl implements StationDetailService {

    @Autowired
    private StationDetailMapper stationDetailMapper;

    @Override
    public List<StationDetail> selectByLineName(String lineName) {

        return null;
    }

    @Override
    public List<Pair<String, String>> selectLocationList(List<StationDetail> stationDetailList) {
        return null;
    }
}
