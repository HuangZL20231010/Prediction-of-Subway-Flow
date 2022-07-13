package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.CongestionMapper;
import com.example.demo.mapper.CongestionResultMapper;
import com.example.demo.pojo.table.Congestion;
import com.example.demo.pojo.table.CongestionResult;
import com.example.demo.service.CongestionResultService;
import com.example.demo.service.CongestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongestionResultServiceImpl implements CongestionResultService
{
    @Autowired
    private CongestionService congestionService;

    @Autowired
    private CongestionMapper congestionMapper;

    @Autowired
    private CongestionResultMapper congestionResultMapper;

    @Override
    public void insertCongestionResult()
    {
        List<Congestion> congestionList = congestionMapper.selectList(new QueryWrapper<Congestion>());
        for (Congestion congestion : congestionList)
        {
            CongestionResult congestionResult = new CongestionResult();

            congestionResult.setStationid(congestion.getStationid());
            congestionResult.setTime(congestion.getTime());
            congestionResult.setCongestionlevel(congestionService.calculateCongestionFactor(congestion.getStationid(), congestion.getTime()));

            congestionResultMapper.insert(congestionResult);
        }

    }
}
