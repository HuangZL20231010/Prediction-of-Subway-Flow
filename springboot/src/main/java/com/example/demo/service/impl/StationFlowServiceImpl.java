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

@Service
public class StationFlowServiceImpl implements StationFlowService
{
    @Autowired
    private StationDetailMapper stationDetailMapper;
    @Autowired
    private StationFlowMapper  stationFlowMapper;

    @Override
     public Pair<Integer, Integer> selectStationForNum(Integer stationID, String time)
    {
        TimeUtil t=new TimeUtil();
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("stationID", stationID).eq("time",  time);
        StationFlow st=stationFlowMapper.selectOne(sectionQueryWrapper);
        Pair<Integer,Integer>  pa=new Pair<>(st.getInnum(),st.getOutnum());
        return pa;
    }



    @Override
    public Pair<Integer, Integer> getLineInOutNum(Integer lineName, String time) {
        TimeUtil t=new TimeUtil();
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        QueryWrapper<StationDetail> sectionQueryWrapper2 = new QueryWrapper<>();
        List<StationDetail> StationDetails;
        Integer inNum = 0;
        Integer  outNum = 0;
        sectionQueryWrapper2.eq("lineName", lineName);
        StationDetails=stationDetailMapper.selectList(sectionQueryWrapper2);
        for (StationDetail item : StationDetails) {
            QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
            List<StationFlow> stationFlowList= new ArrayList<>();
            Integer Stationid =0;
            Stationid=item.getStationid();
            sectionQueryWrapper.eq("time", time).eq("stationid",Stationid);
            StationFlow stationFlow= stationFlowMapper.selectOne(sectionQueryWrapper);
            if (stationFlow!= null) {
                inNum += stationFlow.getInnum();
                outNum += stationFlow.getOutnum();
            }

        }

        Pair<Integer , Integer > pa=new Pair<>(inNum, outNum);
        return pa;
    }
    // 传入特定线路的名称和时间，获取该线路在该天（注意不是该时间，应该取时间中的天作为判断）所有时间点的入站量和出站量
    // 返回类型是一个结构链表，该结构定义在pojo/entity中，包含线路名称，时间点，该时间点上的入站量和出站量
    // 该方法中，只需要设置结构中的名称，时间点，该时间点上的入站量和出站量，无需设置另外两个链表
    @Override
    public List<LineInformation> getLineInOutNumAllDay(Integer  lineName, String time) {
        TimeUtil t=new TimeUtil();
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        List<LineInformation> li=new ArrayList<>();
        QueryWrapper<StationDetail> sectionQueryWrapper2 = new QueryWrapper<>();
        List<StationDetail> StationDetails;
        sectionQueryWrapper2.eq("lineName", lineName);
        StationDetails=stationDetailMapper.selectList(sectionQueryWrapper2);
        for (StationDetail item : StationDetails) {
            QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
            sectionQueryWrapper.eq("stationID", item.getStationid()).likeRight("time", time.split(" "));
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
    // 传入特定线路的名称和时间，获取该线路在该时间点的所有站点的：id，名称，入站量，出站量，经纬度，其它变量不存
    // 返回链表，元素是站点信息的实体类，注意传入的时间time需要转换
    @Override
    public List<StationInformation> getStationInfoInLineByTime(Integer lineName, String time) {
        TimeUtil t=new TimeUtil();
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();

        return null;
    }

    @Override
    public Integer getLineInnumByNameTime(Integer lineName, String time)
    {
        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        /* 获得该线路在该时间的总入站量和出站量 */
        Pair<Integer, Integer> lineInOutNum = getLineInOutNum(lineName, time);

        /* 返回其中的客流量 */
        return lineInOutNum.getFirst();
    }

    @Override
    public List<Pair<String, Integer>> getLineInnumAllTime(Integer lineName, String time)
    {
        QueryWrapper<StationFlow> queryWrapper = new QueryWrapper<>();
        return null;
    }
}
