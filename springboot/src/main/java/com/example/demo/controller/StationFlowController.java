package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.service.StationDetailService;
import com.example.demo.service.StationFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import wniemiec.util.data.Pair;

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
    public Result<?> getStationInformationByLineName( @RequestBody String lineName)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String time = dateFormat.format(date); // 当前时间

        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);

        return Result.success(stationInfoInLineByTime);
    }

    // 传入线路名称和时间，返回该时间时间该线路上所有站点的经纬度和入站量，出站量等信息
    @PostMapping ("/StationByLineTime")
    @ResponseBody
    public Result<?> getStationInformationInLine(@RequestBody String lineName, @RequestBody String time)
    {
        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);

        return Result.success(stationInfoInLineByTime);
    }

//    @GetMapping("/AllStationsPFlowDay")
//    @ResponseBody
//    public Result<?> getAllStationsPFlowAllDay()
//    {
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//        String time = dateFormat.format(date); // 当前时间
//        List<String> linesNameList = stationDetailService.selectLinesName();    // 所有线路的名称
//        List<Pair<String, List<String, String>>>
//
//        for (String lineName : linesNameList)
//        {
//
//        }
//
//    }
}
