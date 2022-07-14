import pandas as pd
import xlwt
import csv



col=["stationid",'startTime','inNums','outNums']
i=0
day='2022/7/15'
num=1
datalist=[]
for i in range(313):
    j=0
    k=0
    while j<24:
        data=[]
        data.append(i+1)
        if k==0:
            time=day+' '+str(j)+":00"+':00'
        else:
            time=day+' '+str(j)+':'+str(k*10)+':00'
        data.append(time)
        datalist.append(data)
        num=num+1
        if k<5:
            k=k+1
        else:
            k=0
            j=j+1
with open ('_10mins/realsubmit.csv','w',encoding='utf-8',newline='') as fp:
    # 写
    writer =csv.writer(fp)
    # 设置第一行标题头
    writer.writerow(col)
    # 将数据写入
    writer.writerows(datalist)
