package com.example.demo.service.impl;

import com.example.demo.pojo.entity.LineInformation;
import com.example.demo.service.StationFlowService;
import com.sun.tools.javac.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationFlowServiceImpl implements StationFlowService
{
    @Override
    public Pair<String, String> selectStationForNum(String stationID, String time)
    {

        return null;
    }

    @Override
    public Pair<String, String> getLineInOutNum(String lineName, String time) {
        return null;
    }

    @Override
    public List<LineInformation> getLineInOutNumAllDay(String lineName, String time) {
        return null;
    }
}
