package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.service.StationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.util.data.Pair;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationDetailServiceImpl implements StationDetailService {

    @Autowired
    private StationDetailMapper stationDetailMapper;

    @Override
    public List<Integer> selectLinesName() {
        List<Integer> lineNameList = new ArrayList<>();
        QueryWrapper<StationDetail> queryMapper = new QueryWrapper<>();
        for (StationDetail stationDetail : stationDetailMapper.selectList(queryMapper.isNotNull("lineName").orderByAsc("lineName")))
        {
            if (lineNameList.contains(stationDetail.getLinename()))
                continue;
            lineNameList.add(stationDetail.getLinename());
        }

        return lineNameList;
    }

    @Override
    public List<StationDetail> selectByLineName(Integer lineName) {

        QueryWrapper<StationDetail> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("lineName", lineName);
        List<StationDetail> StationDetails=stationDetailMapper.selectList( sectionQueryWrapper);

        return StationDetails;
    }

    @Override
    public List<Pair<String, String>> selectLocationList(List<StationDetail> stationDetailList) {
        List<Pair<String, String>> li=new ArrayList<>();

        for (StationDetail item : stationDetailList) {
            Pair<String, String> pa=new Pair<>(item.getLongitude(),item.getLatitude());
             li.add(pa);
        }
        return li;
    }
}
