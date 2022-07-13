package com.example.demo.pojo.entity;


public class StationInformation
{
    private Integer stationID;   // 站点的id
    private Integer lineID; // 所属线路的id
    private String name;    // 站点的名称
    private String time;    // 时间
    private Integer inNum;   // 入站量
    private Integer outNum;  // 出站量
    private String longitude;   // 经度
    private String latitude;    // 纬度
    private Double congestion;  // 拥堵指数

    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

    public void setInNum(Integer inNum) {
        this.inNum = inNum;
    }

    public Integer getOutNum() {
        return outNum;
    }

    public Integer getInNum() {
        return inNum;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getLineID() {
        return lineID;
    }

    public void setLineID(Integer lineID) {
        this.lineID = lineID;
    }

    public Double getCongestion() {
        return congestion;
    }

    public void setCongestion(Double congestion) {
        this.congestion = congestion;
    }
}
