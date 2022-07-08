package com.example.demo.util;


import com.sun.tools.javac.util.Pair;

import java.util.List;

// 该文档定义一些全局变量，如所有线路的名称，线路对应的所有站点的ID
public class Global
{
    public static final int STATION_NUM = 0;    // 站点数量
    public static final int LINE_NUM = 0;   // 线路数量
    public static final List<String> All_LINE_NAME = null;   // 所有线路的名称
    public static final List<String> All_STATION_ID = null; // 所有站点的ID
    public static final Pair<String, List<String>> STATION_ID_LINE = null;  // 存储线路名称和该线路上所有站点id
}
