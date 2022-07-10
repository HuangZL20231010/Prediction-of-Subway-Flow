package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.mapper.StationFlowMapper;
import com.example.demo.pojo.entity.LineInformation;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.pojo.table.StationDetail;
import com.example.demo.pojo.table.StationFlow;
import com.example.demo.service.StationDetailService;
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

    @Autowired
    private StationDetailService stationDetailService;


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
    public List<LineInformation> getLineInOutNumAllDay(Integer  lineName, String time)
    {
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
            QueryWrapper<StationFlow> queryWrapperTime = new QueryWrapper<>();
            queryWrapperTime.select("time").like("time", time.split(" ")).eq("stationID", 1);
            List<Object> timeList = stationFlowMapper.selectObjs(queryWrapperTime);
            /* 遍历时间点，得到该线路在所有时间点的客流量,并存到链表中 */
            for (Object Time : timeList)
            {
                LineInformation lineInformation=new  LineInformation();
                lineInformation.setTime((String) Time);
                Pair<Integer,Integer> pair;
                pair=getLineInOutNum(lineName, (String) Time);
                lineInformation.setInNum(pair.getFirst());
                lineInformation.setOutNum(pair.getSecond());
                li.add(lineInformation);
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

    // 传入特定线路的名称和时间，获取该线路在这个时间所属的一天中，所有时间点的客流量
    // 链表，每一个元素是一个pair，pair中的第一个为时间，第二个为该时间的客流量
    @Override
    public List<Pair<String, Integer>> getLineInnumAllTime(Integer lineName, String time)
    {
        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        /* 获取所有的时间点 */
        QueryWrapper<StationFlow> queryWrapperTime = new QueryWrapper<>();
        queryWrapperTime.select("time").like("time", time.substring(0, 10)).eq("stationID", 1);
        List<Object> timeList = stationFlowMapper.selectObjs(queryWrapperTime);

        /* 遍历时间点，得到该线路在所有时间点的客流量,并存到链表中 */
        List<Pair<String, Integer>> lineInNumAllTimeList = new ArrayList<>();
        for (Object Time : timeList)
        {
            Pair<String, Integer> lineInNum = new Pair<>("", 0);

            lineInNum.setFirst((String) Time);  // 设置时间点
            lineInNum.setSecond(getLineInnumByNameTime(lineName, (String) Time));   // 设置客流量

            /* 加入到链表中 */
            lineInNumAllTimeList.add(lineInNum);
        }

        return lineInNumAllTimeList;
    }

    // 传入时间和排行数目，获取全部地铁站的人流量排行
    @Override
    public List<StationInformation> getStationInNumRank(String time, Integer number)
    {
        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        /* 查询该时间点所有站的flow信息,并按inNum【排序 */
        QueryWrapper<StationFlow> stationFlowQueryWrapper = new QueryWrapper<>();
        stationFlowQueryWrapper.eq("time", time).orderByDesc("inNum");
        List<StationFlow> stationFlowList = stationFlowMapper.selectList(stationFlowQueryWrapper);

        List<StationInformation> stationInformationList = new ArrayList<>();
        for (int i = 0; i < 331 && i < number; i++)
        {
            StationInformation stationInformation = new StationInformation();
            StationFlow stationFlow = stationFlowList.get(i);   // 得到排名i + 1的站点的flow表信息
            StationDetail stationDetail = stationDetailMapper.selectOne(new QueryWrapper<StationDetail>().eq("stationID", stationFlow.getStationid())); // 得到detail表信息

            stationInformation.setStationID(i + 1); // 设置站点id
            stationInformation.setName(stationDetail.getStationname()); // 设置站点名称
            stationInformation.setInNum(stationFlow.getInnum());    // 设置入站量
            stationInformation.setOutNum(stationFlow.getOutnum());  // 设置出站量
            stationInformation.setTime(time);   // 设置时间
            stationInformation.setLatitude(stationDetail.getLatitude());    // 设置纬度
            stationInformation.setLongitude(stationDetail.getLongitude());  // 设置经度

            stationInformationList.add(stationInformation);
        }

        return stationInformationList;
    }
}
