package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.mapper.StationFlowMapper;
import com.example.demo.mapper.StationJudgeMapper;
import com.example.demo.pojo.entity.LineInformation;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.pojo.table.StationFlow;
import com.example.demo.service.StationFlowService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.util.data.Pair;

import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class StationFlowServiceImpl implements StationFlowService
{
    @Autowired
    private StationDetailMapper stationDetailMapper;
    @Autowired
    private StationFlowMapper  stationFlowMapper;

    @Override
    public Pair<String, String> selectStationForNum(String stationID, String time)
    {
        QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("stationID", stationID).eq("time",  time);
        StationFlow st=stationFlowMapper.selectOne(sectionQueryWrapper);
        Pair<String, String>  pa=new Pair<>(st.getInnum(),st.getOutnum());
        return pa;
    }

    @Override
    public Pair<String, String> getLineInOutNum(String lineName, String time) {
        QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
        QueryWrapper<StationDetail> sectionQueryWrapper2 = new QueryWrapper<>();
        List<StationDetail> StationDetails;
        int inNum = 0;
        int outNum = 0;
        sectionQueryWrapper2.eq("lineName", lineName);
        StationDetails=stationDetailMapper.selectList(sectionQueryWrapper2);
        for (StationDetail item : StationDetails) {
            sectionQueryWrapper.eq("stationID", item.getStationid()).eq("time", time);
            StationFlow st= stationFlowMapper.selectOne(sectionQueryWrapper);
            inNum+=parseInt(st.getInnum(),10);
            outNum+=parseInt(st.getOutnum(),10);
        }

        Pair<String, String> pa=new Pair<>(String.valueOf(inNum), String.valueOf(outNum));
        return pa;
    }

    @Override
    public List<LineInformation> getLineInOutNumAllDay(String lineName, String time) {
        return null;
    }

    @Override
    public List<StationInformation> getStationInfoInLineByTime(String lineName, String time) {

        return null;
    }

    @Override
    public String getLineInnumByNameTime(String lineName, String time)
    {
        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        /* 获得该线路在该时间的总入站量和出站量 */
        Pair<String, String> lineInOutNum = getLineInOutNum(lineName, time);

        /* 返回其中的客流量 */
        return lineInOutNum.getFirst();
    }

    @Override
    public List<Pair<String, String>> getLineInnumAllTime(String lineName, String time)
    {
        QueryWrapper<StationFlow> queryWrapper = new QueryWrapper<>();
        return null;
    }
}
