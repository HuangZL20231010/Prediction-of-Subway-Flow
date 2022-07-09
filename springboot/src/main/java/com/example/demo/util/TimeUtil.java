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
    // 例如：传入的time是2022/7/7 18:07:00,就需要转换成2022/7/7 18:10:00,同理18:03转换成18:00
    public boolean toApproachTime()
    {

        char[] t = time.toCharArray();
        char a=t[t.length-4];
        if (a >= '5') {
            t[t.length - 5] = (char) (t[t.length - 5] + 1);
        }
        t[t.length-4]='0';
        t[t.length-3]=':';
        t[t.length-2]='0';
        t[t.length-1]='0';
        time=new String(t);
        return true;
    }
}
