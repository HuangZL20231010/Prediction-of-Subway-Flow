package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.pojo.entity.LineInformationAllTime;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.service.StationDetailService;
import com.example.demo.service.StationFlowService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wniemiec.util.data.Pair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/StationFlow")
public class StationFlowController
{

    @Autowired
    private StationFlowService stationFlowService;

    @Autowired
    private StationDetailService stationDetailService;

    // 传入线路名称，返回当前时间该线路上所有站点的经纬度和入站量，出站量等信息
    // 有time参数版,传进来的time格式为05:10，肯定为整数
    @GetMapping( value = {"/StationByline/{lineName}/{time}", "/StationByline/{lineName}"})
    @ResponseBody
    public Result<?> getStationInformationByLineName(@PathVariable(value = "lineName") Integer lineName, @PathVariable(value = "time", required = false) String time)
    {
        if (time != null)
        {
            time = "2015/04/29 " + time + ":00";
        }
        else
        {
            /* 获得当前时间 */
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            time = dateFormat.format(date); // 当前时间

            /* 处理当前时间 */
            TimeUtil timeUtil = new TimeUtil();
            timeUtil.setTime(time);
            timeUtil.toApproachTime();
            time = timeUtil.getTime();
        }

        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);

        return Result.success(stationInfoInLineByTime);
    }


    // 接受前端请求，返回该天所有线路的所有时间点的客运量
    @GetMapping("/AllStationsPFlowDay")
    @ResponseBody
    public Result<?> getAllStationsPFlowAllDay()
    {
        /* 获得当前时间 */
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date); // 当前时间

        /* 处理当前时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        List<Integer> linesNameList = stationDetailService.selectLinesName();    // 所有线路的名称
        List<LineInformationAllTime> lineInformationAllTimeList = new ArrayList<>();    // 最后返回的链表，存储结构体

        for (Integer lineName : linesNameList)
        {
            LineInformationAllTime lineInformationAllTime = new LineInformationAllTime();

            lineInformationAllTime.setLineName(lineName);   // 设置线路名
            lineInformationAllTime.setDate(time);   // 设置时间
            lineInformationAllTime.setInNumAllTime(stationFlowService.getLineInnumAllTime(lineName, time));  // 得到线路该天所有时间点的客运量

            lineInformationAllTimeList.add(lineInformationAllTime); // 添加到链表中
        }

        return Result.success(lineInformationAllTimeList);
    }

    @GetMapping("/getStationInNumRank")
    @ResponseBody
    public Result<?> getStationInNumRank()
    {
        /* 获得当前时间 */
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date); // 当前时间

        /* 处理时间 */
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.setTime(time);
        timeUtil.toApproachTime();
        time = timeUtil.getTime();

        List<StationInformation> stationInNumRank = stationFlowService.getStationInNumRank(time, 15);
        return Result.success(stationInNumRank);
    }

    @GetMapping(value = "/getLineInNumByID/{lineID}")
    @ResponseBody
    public Result<?> getLineInNumByID(@PathVariable("lineID") Integer lineID)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+ HMS;

        List<Pair<String, Integer>> lineInnumAllTime = stationFlowService.getLineInnumAllTime(lineID, time);

        return Result.success(lineInnumAllTime);
    }

    // 传入站点id，得到该站点在一整天总的入站量和出站量
    @GetMapping("/getStationInOutNumByID/{stationID}")
    @ResponseBody
    public Result<?> getStationInOutNumByID(@PathVariable("stationID") Integer stationID)
    {
        /* 处理时间 */
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+ HMS;

        return Result.success(stationFlowService.getStationInOutNum(stationID, time));
    }
}
