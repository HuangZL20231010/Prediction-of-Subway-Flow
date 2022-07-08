package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.pojo.entity.StationInformation;
import com.example.demo.service.StationFlowService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/StationFlow")
public class StationFlowController
{

    @Autowired
    private StationFlowService stationFlowService;

    // 传入线路名称，返回当前时间该线路上所有站点的经纬度和入站量，出站量等信息
    @GetMapping("/StationByLine/{lineName}")
    @ResponseBody
    public Result<?> getStationInformationByLineName( @PathVariable("lineName") String lineName)
    {
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//        String time = dateFormat.format(date); // 当前时间
//
//        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);
        StationInformation stationInfoInLineByTime = new StationInformation();
        stationInfoInLineByTime.setStationID("1");
        stationInfoInLineByTime.setTime("2022/3/3 18:00");
        stationInfoInLineByTime.setName("信阳站");
        stationInfoInLineByTime.setInNum("400");
        stationInfoInLineByTime.setOutNum("300");
        stationInfoInLineByTime.setLatitude("33");
        stationInfoInLineByTime.setLongitude("44");
        Result<StationInformation> success = Result.success(stationInfoInLineByTime);

        return success;
    }

    // 传入线路名称和时间，返回该时间时间该线路上所有站点的经纬度和入站量，出站量等信息
    @GetMapping("/StationByLineTime")
    public Result<?> getStationInformationInLine(@RequestBody String lineName, @RequestBody String time)
    {
        List<StationInformation> stationInfoInLineByTime = stationFlowService.getStationInfoInLineByTime(lineName, time);
//        StationInformation stationInfoInLineByTime = new StationInformation();
//        stationInfoInLineByTime.setStationID("1");
//        stationInfoInLineByTime.setTime("2022/3/3 18:00");
//        stationInfoInLineByTime.setName("信阳站");
//        stationInfoInLineByTime.setInNum("400");
//        stationInfoInLineByTime.setOutNum("300");
//        stationInfoInLineByTime.setLatitude("33");
//        stationInfoInLineByTime.setLongitude("44");

        return Result.success(stationInfoInLineByTime);
    }
}
