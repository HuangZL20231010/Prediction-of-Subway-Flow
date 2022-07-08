package com.example.demo.pojo.entity;

public class StationInformation
{
    private String stationID;   // 站点的id
    private String name;    // 站点的名称
    private String time;    // 时间
    private String inNum;   // 入站量
    private String outNum;  // 出站量
    private String longitude;   // 经度
    private String latitude;    // 纬度

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }

    public void setInNum(String inNum) {
        this.inNum = inNum;
    }

    public String getOutNum() {
        return outNum;
    }

    public String getInNum() {
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

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
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
}
