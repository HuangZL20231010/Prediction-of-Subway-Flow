package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.pojo.entity.LineInformationAllTime;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.service.StationDetailService;
import com.example.demo.service.StationFlowService;
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
    @PostMapping("/StationByLine")
    @ResponseBody
    public Result<?> getStationInformationByLineName( @RequestBody Integer lineName)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+HMS;
        System.out.println(time);
        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);

        return Result.success(stationInfoInLineByTime);
    }

    // 传入线路名称和时间，返回该时间时间该线路上所有站点的经纬度和入站量，出站量等信息
    @PostMapping ("/StationByLineTime")
    @ResponseBody
    public Result<?> getStationInformationInLine(@RequestBody Integer lineName, @RequestBody String time)
    {
        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);

        return Result.success(stationInfoInLineByTime);
    }

    // 接受前端请求，返回该天所有线路的所有时间点的客运量
    @GetMapping("/AllStationsPFlowDay")
    @ResponseBody
    public Result<?> getAllStationsPFlowAllDay()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+HMS;
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
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+HMS;

        List<StationInformation> stationInNumRank = stationFlowService.getStationInNumRank(time, 15);
        return Result.success(stationInNumRank);
    }

    @PostMapping("/getLineInNumByID")
    @ResponseBody
    public Result<?> getLineInNumByID(@RequestBody Integer lineID)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String time = dateFormat.format(date); // 当前时间
        String HMS = time.substring(11,19);
        time="2015/04/29 "+ HMS;

        List<Pair<String, Integer>> lineInnumAllTime = stationFlowService.getLineInnumAllTime(lineID, time);

        return Result.success(lineInnumAllTime);
    }
}
