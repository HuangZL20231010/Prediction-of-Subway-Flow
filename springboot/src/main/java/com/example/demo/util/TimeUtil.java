package com.example.demo.util;

public class TimeUtil
{
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // 该方法将自身的time设置为临近的能够查表的time并返回是否成功
    // 例如：传入的time是2022/7/7 18:07,就需要转换成2022/7/7 18:10,同理18:03转换成18:00
    public boolean toApproachTime()
    {
        return true;
    }
}
