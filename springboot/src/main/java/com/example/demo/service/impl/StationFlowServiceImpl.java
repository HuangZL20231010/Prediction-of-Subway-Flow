package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.CongestionResultMapper;
import com.example.demo.mapper.LineFlowMapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.mapper.StationFlowMapper;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.pojo.table.CongestionResult;
import com.example.demo.pojo.table.LineFlow;
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
    private LineFlowMapper lineFlowMapper;
    @Autowired
    private StationDetailService stationDetailService;

    @Autowired
    private CongestionResultMapper congestionResultMapper;


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
        QueryWrapper<LineFlow> sectionQueryWrapper2 = new QueryWrapper<>();
        LineFlow lineFlows;
        Integer inNum = 0;
        Integer  outNum = 0;
        sectionQueryWrapper2.eq("lineID", lineName).eq("time",time);
        lineFlows=lineFlowMapper.selectOne(sectionQueryWrapper2);
            if ( lineFlows!= null) {
                inNum =  lineFlows.getInnum();
                outNum =  lineFlows.getOutnum();
            }

        Pair<Integer , Integer > pa=new Pair<>(inNum, outNum);
        return pa;
    }
    // 传入特定线路的名称和时间，获取该线路在该天（注意不是该时间，应该取时间中的天作为判断）所有时间点的入站量和出站量
    // 返回类型是一个结构链表，该结构定义在pojo/entity中，包含线路名称，时间点，该时间点上的入站量和出站量
    // 该方法中，只需要设置结构中的名称，时间点，该时间点上的入站量和出站量，无需设置另外两个链表
    @Override
    public List<LineFlow> getLineInOutNumAllDay(Integer  lineName, String time)
    {
        TimeUtil t=new TimeUtil();
        t.setTime(time);
        t.toApproachTime();
        time=t.getTime();
        List<LineFlow> li=new ArrayList<>();
        QueryWrapper<LineFlow> queryWrapperTime = new QueryWrapper<>();
        queryWrapperTime.select("DISTINCT time").likeRight("time", time.substring(0,9));
        List<Object> timeList = lineFlowMapper.selectObjs(queryWrapperTime);
        /* 遍历时间点，得到该线路在所有时间点的客流量,并存到链表中 */
        for (Object Time : timeList)
        {
          LineFlow lineFlows=new LineFlow();
            lineFlows.setTime((String) Time);
            Pair<Integer,Integer> pair;
            pair=getLineInOutNum(lineName, (String) Time);
            lineFlows.setInnum(pair.getFirst());
            lineFlows.setOutnum(pair.getSecond());
            lineFlows.setLineid(lineName);
            li.add(lineFlows);
        }
         return li;
    }
    // 传入特定线路的名称和时间，获取该线路在该时间点的所有站点的：id，名称，入站量，出站量，经纬度，其它变量不存
    // 返回链表，元素是站点信息的实体类，注意传入的时间time需要转换
    @Override
    public List<StationInformation> getStationInfoInLineByTime(Integer lineName, String time)
    {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();
        QueryWrapper<StationDetail> sectionQueryWrapper2 = new QueryWrapper<>();
        List<StationDetail> StationDetails;
        List<StationInformation>  StationInformations=new ArrayList<>();
        sectionQueryWrapper2.eq("lineName", lineName);
        StationDetails=stationDetailMapper.selectList(sectionQueryWrapper2);
        for (StationDetail item : StationDetails)
        {
            QueryWrapper<StationFlow> sectionQueryWrapper = new QueryWrapper<>();
            StationInformation stationInformation=new StationInformation();
            Integer Stationid =0;
            Stationid=item.getStationid();
            sectionQueryWrapper.eq("time", time).eq("stationID",Stationid);
            StationFlow stationFlow= stationFlowMapper.selectOne(sectionQueryWrapper);
            QueryWrapper<CongestionResult> congestionResultQueryWrapper = new QueryWrapper<>();
            congestionResultQueryWrapper.eq("stationID", item.getStationid()).eq("time", time);
            CongestionResult congestionResult = congestionResultMapper.selectOne(congestionResultQueryWrapper);


            stationInformation.setStationID(Stationid);
            stationInformation.setName(item.getStationname());
            stationInformation.setLongitude(item.getLongitude());
            stationInformation.setLatitude(item.getLatitude());
            stationInformation.setInNum(stationFlow.getInnum());
            stationInformation.setOutNum(stationFlow.getOutnum());
            stationInformation.setTime(time);
            stationInformation.setCongestion(congestionResult.getCongestionnormalization());
            StationInformations.add(stationInformation);
        }

        return StationInformations;
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

        /* 查询该时间点所有站的flow信息,并按inNum + outNum排序 */
        QueryWrapper<StationFlow> stationFlowQueryWrapper = new QueryWrapper<>();
        stationFlowQueryWrapper.eq("time", time).orderByDesc("inNum + outNum");
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
            stationInformation.setLineID(stationDetail.getLinename());  // 设置线路id
            stationInformationList.add(stationInformation);
        }

        return stationInformationList;
    }

    @Override
    public List<StationFlow> getStationInOutNumAllTime(Integer stationID, String time)
    {
        Integer inNumSum = 0;   // 一整天的入站量之和
        Integer outNumSum = 0;  // 一整天的出站量之和
        String YMD; // 年月日

        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();
        YMD = time.substring(0, 10);

        /* 查询该站点在一整天的flow信息 */
        QueryWrapper<StationFlow> stationFlowQueryWrapper = new QueryWrapper<>();
        stationFlowQueryWrapper.like("time", YMD).eq("stationID", stationID);
        List<StationFlow> stationFlowList = stationFlowMapper.selectList(stationFlowQueryWrapper);

        return stationFlowList;

    }
}
