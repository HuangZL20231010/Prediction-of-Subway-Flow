# -*- coding: utf-8 -*-
import pandas as pd
from datetime import datetime
import os
import matplotlib.pyplot as plt
# clear memory
import gc

dict={'10号线国权路': 1, '10号线海伦路': 2, '10号线航中路': 3, '10号线虹桥1号航站楼': 4, '10号线虹桥路': 5, '10号线江湾体育场': 6, '10号线交通大学': 7, '10号线龙柏新村': 8, '10号线龙溪路': 9, '10号线南京东路': 10, '10号线三门路': 11, '10号线陕西南路': 12, '10号线上海动物园': 13, '10号线上海图书馆': 14, '10号线水城路': 15, '10号线四川北路': 16, '10号线宋园路': 17, '10号线天潼路': 18, '10号线同济大学': 19, '10号线五角场': 20, '10号线新江湾城': 21, '10号线新天地': 22, '10号线伊犁路': 23, '10号线殷高东路': 24, '10号线邮电新村': 25, '10号线豫园': 26, '10号线紫藤路': 27, '11号线安亭': 28, '11号线白银路': 29, '11号线曹杨路': 30, '11号线昌吉东路': 31, '11号线枫桥路': 32, '11号线光明路': 33, '11号线花桥': 34, '11号线嘉定北': 35, '11号线嘉定西': 36, '11号线嘉定新城': 37, '11号线江苏路': 38, '11号线李子园路': 39, '11号线龙华': 40, '11号线龙耀路': 41, '11号线隆德路': 42, '11号线罗山路': 43, '11号线马陆': 44, '11号线南翔': 45, '11号线浦三路': 46, '11号线祁连山路': 47, '11号线三林': 48, '11号线三林东': 49, '11号线上海汽车城': 50, '11号线上海赛车场': 51, '11号线上海西站': 52, '11号线上海游泳馆': 53, '11号线桃浦新村': 54, '11号线武威路': 55, '11号线御桥': 56, '11号线云锦路': 57, '11号线兆丰路': 58, '11号线真如': 59, '12号线爱国路': 60, '12号线东陆路': 61, '12号线复兴岛': 62, '12号线国际客运中心': 63, '12号线江浦公园': 64, '12号线金海路': 65, '12号线金京路': 66, '12号线巨峰路': 67, '12号线隆昌路': 68, '12号线宁国路': 69, '12号线申江路': 70, '12号线提篮桥': 71, '12号线杨高北路': 72, '13号线大渡河路': 73, '13号线丰庄': 74, '13号线金沙江路': 75, '13号线金沙江西路': 76, '13号线金运路': 77, '13号线祁连山南路': 78, '13号线武宁路': 79, '13号线真北路': 80, '16号线滴水湖': 81, '16号线航头东': 82, '16号线鹤沙航城': 83, '16号线华夏中路': 84, '16号线惠南': 85, '16号线惠南东': 86, '16号线临港大道': 87, '16号线龙阳路': 88, '16号线上海野生动物园': 89, '16号线书院': 90, '16号线新场': 91, '16号线周浦东': 92, '1号线宝安公路': 93, '1号线漕宝路': 94, '1号线常熟路': 95, '1号线富锦路': 96, '1号线共富新村': 97, '1号线共康路': 98, '1号线汉中路': 99, '1号线衡山路': 100, '1号线呼兰路': 101, '1号线黄陂南路': 102, '1号线锦江乐园': 103, '1号线莲花路': 104, '1号线彭浦新村': 105, '1号线人民广场': 106, '1号线陕西南路': 107, '1号线上海火车站': 108, '1号线上海马戏城': 109, '1号线上海南站': 110, '1号线上海体育馆': 111, '1号线莘庄': 112, '1号线通河新村': 113, '1号线外环路': 114, '1号线汶水路': 115, '1号线新闸路': 116, '1号线徐家汇': 117, '1号线延长路': 118, '1号线友谊西路': 119, '1号线中山北路': 120, '2号线北新泾': 121, '2号线川沙': 122, '2号线创新中路': 123, '2号线东昌路': 124, '2号线广兰路': 125, '2号线海天三路': 126, '2号线虹桥2号航站楼': 127, '2号线虹桥火车站': 128, '2号线华夏东路': 129, '2号线江苏路': 130, '2号线金科路': 131, '2号线静安寺': 132, '2号线凌空路': 133, '2号线龙阳路': 134, '2号线娄山关路': 135, '2号线陆家嘴': 136, '2号线南京东路': 137, '2号线南京西路': 138, '2号线浦东国际机场': 139, '2号线上海科技馆': 140, '2号线世纪公园': 141, '2号线淞虹路': 142, '2号线唐镇': 143, '2号线威宁路': 144, '2号线徐泾东': 145, '2号线远东大道': 146, '2号线张江高科': 147, '2号线中山公园': 148, '3号线宝山路': 149, '3号线宝杨路': 150, '3号线曹杨路': 151, '3号线漕溪路': 152, '3号线赤峰路': 153, '3号线大柏树': 154, '3号线东宝兴路': 155, '3号线虹口足球场': 156, '3号线虹桥路': 157, '3号线江湾镇': 158, '3号线江杨北路': 159, '3号线金沙江路': 160, '3号线龙漕路': 161, '3号线上海火车站': 162, '3号线上海南站': 163, '3号线石龙路': 164, '3号线水产路': 165, '3号线淞浜路': 166, '3号线淞发路': 167, '3号线铁力路': 168, '3号线延安西路': 169, '3号线宜山路': 170, '3号线殷高西路': 171, '3号线友谊路': 172, '3号线张华浜': 173, '3号线长江南路': 174, '3号线镇坪路': 175, '3号线中山公园': 176, '3号线中潭路': 177, '4号线大连路': 178, '4号线大木桥路 ': 179, '4号线东安路': 180, '4号线海伦路': 181, '4号线蓝村路': 182, '4号线临平路': 183, '4号线鲁班路': 184, '4号线南浦大桥': 185, '4号线浦电路': 186, '4号线浦东大道': 187, '4号线上海体育场': 188, '4号线上海体育馆': 189, '4号线世纪大道': 190, '4号线塘桥': 191, '4号线杨树浦路': 192, '4号线宜山路': 193, '5号线北桥': 194, '5号线春申路': 195, '5号线东川路': 196, '5号线华宁路': 197, '5号线剑川路': 198, '5号线金平路': 199, '5号线闵行开发区': 200, '5号线文井路': 201, '5号线银都路': 202, '5号线颛桥': 203, '6号线北洋泾路': 204, '6号线博兴路': 205, '6号线德平路': 206, '6号线东方体育中心': 207, '6号线东靖路': 208, '6号线东明路': 209, '6号线港城路': 210, '6号线高科西路': 211, '6号线高青路': 212, '6号线航津路': 213, '6号线华夏西路': 214, '6号线金桥路': 215, '6号线巨峰路': 216, '6号线蓝村路': 217, '6号线临沂新村': 218, '6号线灵岩南路': 219, '6号线民生路': 220, '6号线浦电路': 221, '6号线上海儿童医学中心': 222, '6号线上南路': 223, '6号线外高桥保税区北': 224, '6号线外高桥保税区南': 225, '6号线五莲路': 226, '6号线五洲大道': 227, '6号线源深体育中心': 228, '6号线云山路': 229, '6号线洲海路': 230, '7号线昌平路': 231, '7号线常熟路': 232, '7号线场中路': 233, '7号线大场镇': 234, '7号线大华三路': 235, '7号线东安路': 236, '7号线芳华路': 237, '7号线顾村公园': 238, '7号线后滩': 239, '7号线花木路': 240, '7号线锦绣路': 241, '7号线静安寺': 242, '7号线岚皋路': 243, '7号线刘行': 244, '7号线龙华中路': 245, '7号线龙阳路': 246, '7号线罗南新村': 247, '7号线美兰湖': 248, '7号线南陈路': 249, '7号线潘广路': 250, '7号线祁华路': 251, '7号线上大路': 252, '7号线上海大学站': 253, '7号线新村路': 254, '7号线行知路': 255, '7号线杨高南路': 256, '7号线云台路': 257, '7号线长清路': 258, '7号线长寿路': 259, '7号线肇嘉浜路': 260, '7号线镇坪路': 261, '8号线鞍山新村': 262, '8号线成山路': 263, '8号线大世界': 264, '8号线虹口足球场': 265, '8号线黄兴公园': 266, '8号线黄兴路': 267, '8号线江浦路': 268, '8号线江月路': 269, '8号线老西门': 270, '8号线联航路': 271, '8号线凌兆新村': 272, '8号线芦恒路': 273, '8号线陆家浜路': 274, '8号线嫩江路': 275, '8号线浦江镇': 276, '8号线曲阜路': 277, '8号线曲阳路': 278, '8号线人民广场': 279, '8号线沈杜公路': 280, '8号线市光路': 281, '8号线四平路': 282, '8号线西藏北路': 283, '8号线西藏南路': 284, '8号线翔殷路': 285, '8号线延吉中路': 286, '8号线杨思': 287, '8号线耀华路': 288, '8号线中华艺术宫': 289, '8号线中兴路': 290, '9号线漕河泾开发区': 291, '9号线打浦桥': 292, '9号线洞泾': 293, '9号线桂林路': 294, '9号线合川路': 295, '9号线嘉善路': 296, '9号线九亭': 297, '9号线马当路': 298, '9号线七宝': 299, '9号线商城路': 300, '9号线佘山': 301, '9号线泗泾': 302, '9号线松江大学城': 303, '9号线松江南站': 304, '9号线松江体育中心': 305, '9号线松江新城': 306, '9号线小南门': 307, '9号线星中路': 308, '9号线徐家汇': 309, '9号线杨高中路': 310, '9号线宜山路': 311, '9号线中春路': 312, '9号线醉白池': 313}

def open_data(i):
    f = open(i)
    data = pd.read_csv(f, header=0, names=["cardnum", "date", "time", "stationid", "business", "status", "attribute"])
    return data


def railway_data(data):
    RailWay = data[data["business"] == "地铁"]
    return RailWay

def fix_linename(d):
    return dict[d]

def fix_status(d):
    if int(d)>0:
        return 1
    else:
        return 0

test_28 = pd.read_csv('Metro_testA/testA_record_2019-01-28.csv')
print(isinstance(test_28['time'], str))
print(type(test_28['time'].values))
print(type(test_28['time'][0]))
# # 计算“时间戳”
# def timespan(data):
#     data["timespan"] = data["time"].apply(lambda x: x[:2]).astype(int) * 3600 + data["time"].apply(
#         lambda x: x[3:5]).astype(int) * 60 + data["time"].apply(lambda x: x[6:]).astype(int)
#     return data
#
#
# # 划分时间片
# def timebins(data):
#     # 计算每个时间片的秒数间隔
#     seconds = [600 * x for x in range(0, 145)]
#     # 给时间片做标签
#     label = [x for x in range(1, 145)]
#     # 这里利用的是pandas的分箱操作
#     _10min = pd.cut(data["timespan"], bins=seconds, labels=label)
#     _10mins=_10min.to_numpy()
#     data['_10mins'] = _10mins
#     return data
#
#
#
# def input_data(data):
#     inputdata = data[data["figure"] == 0]
#     return inputdata
#
#
# def output_data(data):
#     output = data[data["figure"] > 0]
#     return output


# def groupby_data(data):
#     grouped_data = data.groupby(["linename", "_10mins"]).count()
#     manageG = grouped_data.reset_index(drop=False)
#     del grouped_data
#     gc.collect()
#     manageG.drop(["cardnum", "date", "time", "business", "figure", "attribute"], axis=1, inplace=True)
#     manageG.rename(columns={"timespan": "passengernums"}, inplace=True)
#     return manageG


# # mi是进站数据，mo是出站数据
# def merge_data(mi, mo):
#     final_data = pd.merge(mi, mo, how="outer", on=["linename", "_10mins"])
#     final_data.rename(columns={"passengernums_x": "inputnums", "passengernums_y": "outputnums"}, inplace=True)
#     FinalData = final_data.fillna(0)
#     del final_data
#     gc.collect()
#     return FinalData

# date = [datetime.strftime(x, '%Y%m%d') for x in list(pd.date_range('20150427', '20150430'))]
# x=['0401', '0402', '0403', '0404', '0405', '0406', '0407', '0408', '0409', '0410', '0411', '0412', '0413', '0414', '0415', '0416', '0417', '0418', '0419', '0420', '0421', '0422', '0423', '0424']
# y=[9024321, 8971553, 9573352, 6162357, 5385068, 5029066, 8652930, 9020361, 8995979, 9702824, 7099528, 6302967, 8893496, 8855560, 9089192, 9005793, 9690764, 6902696, 5826714, 8772734, 9073351, 9189314, 9122675, 9672276]
# for i in date:
#     x.append(i[4:])
#     filename = "一卡通乘客刷卡数据1/SPTCC-" + i + ".csv"
#     SHData = open_data(filename)
#     print(len(SHData))
#     RailWay = railway_data(SHData)
#     print(len(RailWay))
#     del SHData
#     gc.collect()
#     length=len(RailWay)
#     y.append(length)
# # print(x)
# # print(y)
# plt.plot(x,y,'s-',color = 'r',label="DATE-FLOW")#s-:方形
# plt.xlabel("date")#横坐标名字
# plt.ylabel("flow")#纵坐标名字
# plt.legend(loc = "best")#图例
# plt.show()
# # for i in date:
# #     filename = "一卡通乘客刷卡数据1/SPTCC-" + i + ".csv"
# #     SHData = open_data(filename)
# #     print(len(SHData))
# #     RailWay = railway_data(SHData)
# #     print(len(RailWay))
# #     del SHData
# #     gc.collect()
# #
# #     RailWay['stationid'] = RailWay['stationid'].apply(fix_linename)
# #     RailWay['status']=RailWay['status'].apply(fix_status)
# #     RailWay.drop(["business", "attribute"], axis=1, inplace=True)
# #     RailWay=RailWay.sort_values(by=["time"],ascending=[True])
# #     fn = str(i) + "new.csv"
# #     path = os.path.join("_10mins", fn)
# #     RailWay.to_csv(path)
# #     print(fn+"已完成")
