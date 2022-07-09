package com.example.demo.service;

import com.example.demo.pojo.entity.LineInformation;
import com.example.demo.pojo.entity.StationInformation;
import wniemiec.util.data.Pair;

import java.util.List;

public interface StationFlowService
{
    // 根据stationID和time查询stationflow表单条记录，并返回该条记录的入站量和出站量
    // 注意：这里传入的时间可能是表中没有的时间，如2022/3/3 18：37，需要用TimeUtil类中的时间转换转换成2022/3/3 18:40并加入到检索条件中才能检索到
    Pair<Integer, Integer> selectStationForNum(Integer stationID, String time);

    // 传入特定线路的名称和时间，查询该线路在该时间时总的入站量和出站量
    // 线路总的入站量是线路上所有站点的入站量，线路总的出站量是线路上所有站点的出站量
    // 注意：这里传入的时间可能是表中没有的时间，请注意处理
    Pair<Integer, Integer> getLineInOutNum(Integer lineName, String time);

    // 传入特定线路的名称和时间，获取该线路在该天（注意不是该时间，应该取时间中的天作为判断）所有时间点的入站量和出站量
    // 返回类型是一个结构链表，该结构定义在pojo/entity中，包含线路名称，时间点，该时间点上的入站量和出站量
    // 该方法中，只需要设置结构中的名称，时间点，该时间点上的入站量和出站量，无需设置另外两个链表
    List<LineInformation> getLineInOutNumAllDay(Integer lineName, String time);

    // 传入特定线路的名称和时间，获取该线路在该时间点的所有站点的：id，名称，入站量，出站量，经纬度，其它变量不存
    // 返回链表，元素是站点信息的实体类，注意传入的时间time需要转换
    List<StationInformation> getStationInfoInLineByTime(Integer lineName, String time);

    // 传入特定线路的名称和时间，获取该线路在该时间点的客流量
    // 注意处理时间
    Integer getLineInnumByNameTime(Integer lineName, String time);

    // 传入特定线路的名称和时间，获取该线路在这个时间所属的一天中，所有时间点的客流量
    // 链表，每一个元素是一个pair，pair中的第一个为时间，第二个为该时间的客流量
    List<Pair<String, Integer>> getLineInnumAllTime(Integer lineName, String time);

}
