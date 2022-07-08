package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.StationFlowService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/StationFlow")
public class StationFlowController
{

    @Autowired
    private StationFlowService stationFlowService;

    // 传入线路名称，返回当前时间该线路上所有站点的经纬度和入站量，出站量等信息
    @GetMapping("/StationByLine")
    public Result<?> getStationInformationByLineName(@RequestBody String lineName)
    {
        TimeUtil timeUtil = new TimeUtil();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String time = timeUtil.toApproachTime(dateFormat.format(date)); // 当前时间转换后的结果

        return Result.success();
    }
}
