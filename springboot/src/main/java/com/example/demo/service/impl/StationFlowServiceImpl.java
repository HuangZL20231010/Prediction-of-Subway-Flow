package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.mapper.StationFlowMapper;
import com.example.demo.pojo.entity.LineInformation;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.pojo.table.StationFlow;
import com.example.demo.service.StationFlowService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.util.data.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class StationFlowServiceImpl implements StationFlowService
{
    @Autowired
    private StationDetailMapper stationDetailMapper;
    @Autowired
    private StationFlowMapper  stationFlowMapper;
    private TimeUtil t;
    @Override
    public Pair<String, String> selectStationForNum(String stationID, String time)
    {
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("stationID", stationID).eq("time",  time);
        StationFlow st=stationFlowMapper.selectOne(sectionQueryWrapper);
        Pair<String, String>  pa=new Pair<>(st.getInnum(),st.getOutnum());
        return pa;
    }

    @Override
    public Pair<String, String> getLineInOutNum(String lineName, String time) {
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
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
    // 传入特定线路的名称和时间，获取该线路在该天（注意不是该时间，应该取时间中的天作为判断）所有时间点的入站量和出站量
    // 返回类型是一个结构链表，该结构定义在pojo/entity中，包含线路名称，时间点，该时间点上的入站量和出站量
    // 该方法中，只需要设置结构中的名称，时间点，该时间点上的入站量和出站量，无需设置另外两个链表
    @Override
    public List<LineInformation> getLineInOutNumAllDay(String lineName, String time) {
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        List<LineInformation> li=new ArrayList<>();
        QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
        QueryWrapper<StationDetail> sectionQueryWrapper2 = new QueryWrapper<>();
        List<StationDetail> StationDetails;
        sectionQueryWrapper2.eq("lineName", lineName);
        StationDetails=stationDetailMapper.selectList(sectionQueryWrapper2);
        for (StationDetail item : StationDetails) {
            sectionQueryWrapper.eq("stationID", item.getStationid()).likeRight("time", time.substring(0,7));
            List<StationFlow > StationFlows = stationFlowMapper.selectList(sectionQueryWrapper);
            for (StationFlow item1 : StationFlows) {
                LineInformation in=new  LineInformation() ;
                in.setLineName(item.getLinename());
                in.setTime(item1.getTime());
                in.setInNum(item1.getInnum());
                in.setOutNum(item1.getOutnum());
                li.add(in);
            }
        }
        return li;
    }

    @Override
    public List<StationInformation> getStationInfoInLineByTime(String lineName, String time) {

        return null;
    }

    @Override
    public String getLineInnumByNameTime(String lineName, String time) {
        return null;
    }

    @Override
    public List<Pair<String, String>> getLineInnumAllDay(String lineName, String time) {
        return null;
    }
}
