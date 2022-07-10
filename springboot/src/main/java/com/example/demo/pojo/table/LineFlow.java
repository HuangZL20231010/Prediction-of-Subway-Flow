package com.example.demo.pojo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("lineflow")
@Data
public class LineFlow
{
    Integer lineid;
    String time;
    Integer innum;
    Integer outnum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getInnum() {
        return innum;
    }

    public Integer getLineid() {
        return lineid;
    }

    public Integer getOutnum() {
        return outnum;
    }

    public void setInnum(Integer innum) {
        this.innum = innum;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public void setOutnum(Integer outnum) {
        this.outnum = outnum;
    }

}
