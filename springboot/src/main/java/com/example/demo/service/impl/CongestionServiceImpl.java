package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.CongestionMapper;
import com.example.demo.mapper.StationJudgeMapper;
import com.example.demo.pojo.table.Congestion;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.pojo.table.StationJudge;
import com.example.demo.service.CongestionService;
import com.example.demo.service.StationDetailService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.util.data.Pair;

import java.util.ArrayList;
import java.util.List;

@Service
public class CongestionServiceImpl implements CongestionService
{

    @Autowired
    private StationJudgeMapper stationJudgeMapper;

    @Autowired
    private CongestionMapper congestionMapper;

    @Autowired
    private StationDetailService stationDetailService;

    @Override
    public double calculateCongestionFactor(Integer stationID, String time)
    {
        Integer timePeriod; // 所属时间段
        Integer runInterval;    // 运行间隔
        Integer carCapacity;    // 列车容量
        double carryingCapacity;   // 列车承载能力
        Integer allFlow;    // 该时间点该站点的总流量

        /* 对时间进行处理 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        /* 得到当前属于哪个时间段 */
        timePeriod = timeUtil.judgeTimePeriod(time);

        /* 根据所属时间段获得列车容量，运行间隔，并计算列车承载能力 */
        QueryWrapper<StationJudge> stationJudgeQueryWrapper = new QueryWrapper<>();
        stationJudgeQueryWrapper.eq("stationID", stationID);
        StationJudge stationJudge = stationJudgeMapper.selectOne(stationJudgeQueryWrapper);
        carCapacity = stationJudge.getCarcapacity();

        if (timePeriod == 1)
            runInterval = stationJudge.getMorningtime();
        else if (timePeriod == 2)
            runInterval = stationJudge.getEveningtime();
        else
            runInterval = stationJudge.getOthertime();

        carryingCapacity = 600.0 / (double) runInterval * (double) carCapacity;

        /* 获得该时间点该站点的总流量 */
        QueryWrapper<Congestion> congestionQueryWrapper = new QueryWrapper<>();
        congestionQueryWrapper.eq("stationID", stationID).eq("time", time);
        Congestion congestion = congestionMapper.selectOne(congestionQueryWrapper);
        allFlow = congestion.getAllflow();

        return (double) allFlow / carryingCapacity;
    }

    @Override
    public List<Pair<Integer, Double>> getStationsCongestionInLine(Integer lineID, String time)
    {
        List<StationDetail> stationDetails = stationDetailService.selectByLineName(lineID); // 该线路上所有站点的信息
        List<Pair<Integer, Double>> stationCongestionList = new ArrayList<>();

        /* 对时间进行处理 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        for (StationDetail stationDetail : stationDetails)
        {
            Pair<Integer, Double> stationCongestion = new Pair<>(stationDetail.getStationid(), calculateCongestionFactor(stationDetail.getStationid(), time));
            stationCongestionList.add(stationCongestion);
        }

        return stationCongestionList;
    }
}
