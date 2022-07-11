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
    // 例如：传入的time是2022/07/07 18:07:00,就需要转换成2022/07/07 18:10:00,同理18:03转换成18:00
    public boolean toApproachTime()
    {

        String HH = time.substring(11, 13);
        String MM;

        int M = Integer.parseInt(time.substring(14, 16)) / 10 * 10;

        if (M < 10)
        {
            MM = "0" + String.valueOf(M);
        }
        else
        {
            MM = String.valueOf(M);
        }

        time = "2015/04/29 " + HH + ":" + MM + ":00";


        return true;
    }
}
