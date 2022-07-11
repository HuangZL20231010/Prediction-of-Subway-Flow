<template>
  <div >

    <div>
      <el-radio-group class="scope" v-model="radio1" @change="lineChange">
        <el-radio-button @click="testclick"  label="1号线"></el-radio-button>
        <el-radio-button label="2号线"></el-radio-button>
        <el-radio-button label="3号线"></el-radio-button>
        <el-radio-button label="4号线"></el-radio-button>
        <el-radio-button label="5号线"></el-radio-button>
        <el-radio-button label="6号线"></el-radio-button>
        <el-radio-button label="7号线"></el-radio-button>
      </el-radio-group>
    </div>
    <div>
      <el-radio-group class="scope2" v-model="radio1" @change="lineChange">
        <el-radio-button label="8号线"></el-radio-button>
        <el-radio-button label="9号线"></el-radio-button>
        <el-radio-button label="10号线"></el-radio-button>
        <el-radio-button label="11号线"></el-radio-button>
        <el-radio-button label="12号线"></el-radio-button>
        <el-radio-button label="13号线"></el-radio-button>
        <el-radio-button label="16号线"></el-radio-button>
      </el-radio-group>
    </div>
    <!--    card-->
    <transition  name="slide-fade" style="z-index: 10" >

        <el-card v-if='showw' class="box-card" style="background-color: rgba(31,97,167,0.6);">
          <div slot="header" class="clearfix">
            <span style="color: white;font-weight: bolder;font-size: 20px;margin-bottom: 20px">今日客流量预测</span>
            <div id="main" style="width: 430px; height: 300px"></div>
          </div>
          <div>
            <span style="color: white;font-weight: bolder;font-size: 20px">预测流量排行</span>
            <el-table
                :header-cell-style="{background:'#206cbb',color:'#ffffff',fontWeight:'bolder',fontSize: '16px'}"
                :cell-style="{color:'#ffffff',fontSize: '16px'}"
                :data="tableData"
                style="width: 400px;"
                height="250px"
                :row-class-name="tableRowClassName">
              <el-table-column
                  style="background-color: rgba(31,97,167,0.6);font-weight: bolder"

                  prop="stationID"
                  label="排名"
                  width="70">
              </el-table-column>
              <el-table-column
                  style="background-color: rgba(31,97,167,0.6)"
                  prop="name"
                  label="站点名称"
                  width="200">
                <template slot-scope="scope">
                  <!-- 注意：这个地方要传参数进去才能进行操作  函数名称(scope.row) -->
                  <div @click="zoomsite(scope.row)">{{ scope.row.name }}</div>
                </template>
              </el-table-column>
              <el-table-column
                  prop="inNum"
                  label="预测人流量"
                  width="130">
              </el-table-column>
            </el-table></div>

        </el-card>
    </transition>


    <div id="label" @click="testclick">
      <p style="font-size: 20px;font-weight: bolder;color:white;
      text-decoration: underline;letter-spacing:4px">
        上海轨道交通客流量预测</p>
      <p style="color:white">{{nowTime}}</p>
<!--      <el-button type="primary" size="small" autocomplete="off" @click="testclick()">弹窗测试</el-button>-->
    </div>

    <div id="container" ></div>
  </div>


</template>
<script type="text/javascript">
import request from "@/utils/request";

window._AMapSecurityConfig = {
  securityJsCode:'53df3829a7c8dcf72f5a2810c94b1f1d',
}
import AMapLoader from '@amap/amap-jsapi-loader';
export default {
  data() {
    return {
      radio1:'1号线',
      sites:['同济大学','上海南站','东方明珠'],
      clicktime:0,
      nowTime:'22:00',
      busPolyline:null,
      showw:true,
      sitesinfo:[],
      markerList : [],
      tableData: [],
      map:null,
      mysubway: null,
    }
  },
  mounted() {
    this.initMap();
    this.drawChart();
    request.get('/api/StationFlow/getStationInNumRank').then(res =>{
      this.tableData = res.data;
      this.nowTime=this.tableData[0].time;
    })

},
  methods: {
    zoomsite(row) {
      this.map.remove(this.markerList);
      if(row.line!=this.radio1){
        this.map.remove(this.busPolyline)
        this.radio1=row.line;
        this.lineSearch(this.radio1);
      }


    },

    //描绘一整条线
    Addpoint(line){
      this.map.remove(this.markerList);
      request.post('/api/StationFlow/StationByLine',line).then(res =>{
        // this.sitesinfo = res.data;
        console.log(res);
        this.sitesinfo=res.data;
        for(var i =0;i<this.sitesinfo.length;i++){
          this.drawsite(this.sitesinfo[i].longitude,this.sitesinfo[i].latitude,i)
        }
      })
    },

    lineChange(){
      this.map.remove(this.busPolyline);
      this.map.remove(this.markerList);
      var linename= '地铁'+this.radio1;
      if(this.radio1=='1号线'){
        this.map.setCenter([121.45559270874024,31.247195689843117])
        this.map.setZoom(12)
        this.Addpoint(1);
      }
      if(this.radio1=='2号线'){
        this.map.setCenter([121.44494970336915,31.218426406876784])
        this.map.setZoom(13)
        this.Addpoint(2);
      }
      if(this.radio1=='3号线'){
        this.map.setCenter([121.48202856079102,31.29385374102983])
        this.map.setZoom(11.5)
        this.Addpoint(3);
      }
      if(this.radio1=='4号线'){
        this.map.setCenter([ 121.48336,31.221424])
        this.map.setZoom(13.2)
        this.Addpoint(4);
      }
      this.lineSearch(linename);
    },

    testclick(){
      // var line = '2';
      // request.post('/api/StationFlow/StationByLine').then(res =>{
      //   this.sitesinfo = res.data;
      //   console.log(this.sitesinfo[0]);
      // })
      // for(var i =0;i<this.sitesinfo.length;i++){
      //   this.searchsite(this.sitesinfo[i].name,i);
      // }
      if(this.clicktime==0){
        this.showw = !this.showw;
        this.clicktime++;
      }
      if(this.clicktime==1){
        this.drawChart();
        this.clicktime=0;
      }
    },
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
      let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
      let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
      let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
      let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
      let week = new Date(timeStamp).getDay();
      let weeks = ["日","一","二","三","四","五","六"];
      let getWeek = "星期" + weeks[week];
      this.nowTime = year + "年" + month + "月" + date +"日"+" "+hh+":"+mm+':'+ss+getWeek  ;
    },
    nowTimes(){
      this.timeFormate(new Date());
      setInterval(this.nowTimes,1000);
      this.clear()
    },
    clear(){
      clearInterval(this.nowTimes)
      this.nowTimes = null;
    },
    initMap(){
      AMapLoader.load({
        key:"905b4f99bdf009b2c1607f940ec805e5", // 申请好的Web端开发者Key，首次调用 load 时必填

        version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins:['AMap.LineSearch','AMap.PlaceSearch','AMap.ToolBar', 'AMap.Scale'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap)=>{
        this.map = new AMap.Map("container",{  //设置地图容器id
          resizeEnable: true,
          zoom: 12.5,           //初始化地图级别
          mapStyle: 'amap://styles/blue',
          center: [121.45088, 31.25145]//初始化地图中心点位置
        });
        this.map.addControl(new AMap.ToolBar());
        this.map.addControl(new AMap.Scale());
        this.lineSearch('1号线');

      }).catch(e=>{
        console.log(e);
      })
    },
    lineSearch(line) {
      var linesearch = null;
       linesearch = new window.AMap.LineSearch({
         pageIndex: 1, // 第一页的线路
         city: '上海',
         pageSize: 1, // 每一页的线路条数
        extensions: 'all' // 所有线路类型
      })

      let _this = this;
      var line = line;
      for(var i = 0; i < 1; i++){
        var index=0;
        linesearch.search(line, function(status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 查询成功调用

            _this.lineSearchData(result,index)
            index++;
          } else {
            console.log('查询出错')
          }
        })
      }


    },
    lineSearchData(data,index) {
      var index=index;
      console.log('index='+index)
      var lineArr = data.lineInfo;
      var lineNum = data.lineInfo.length;
      if (lineNum == 0) {
        // console.log(lineNum)
      } else {
        for (var i = 0; i < lineNum; i++) {
          var pathArr = lineArr[i].path;
          console.log(pathArr)
          this.drawbusLine(pathArr,index);
        }
      }

    },
    drawbusLine(BusArr,index) {
      var index =index;
      var colors=['pink','green'];
      this.busPolyline = new window.AMap.Polyline({
        map: this.map,
        path: BusArr,
        strokeColor: colors[index],// 线颜色
        strokeOpacity: 0.9,// 线透明度
        isOutline: false,
        outlineColor: 'white',
        strokeWeight: 3.5 // 线宽
      })


    },
    //打开marker消息窗体
    openInfo(positionResult,pointData) {
      var name=pointData.name
      var info = [];
      var input=6666;
      var output=3333;
      info.push("<div class='input-card content-window-card'>");
      info.push("<div style=\"padding:7px 0px 0px 0px;color:royalblue\"><h4>"+name);
      info.push("</h4><div class='input-item'>列车信息</div></div></div>");
      if(pointData.in<500){info.push("<div><p style='color:green'>预测入站人数");}
      if(pointData.in>=500&&pointData.in<1000){info.push("<div><p style='color:sandybrown'>预测入站人数");}
      if(pointData.in>=1000){info.push("<div><p style='color:darkred'>预测入站人数");}
      info.push(": "+pointData.in+"</p></div>")
      if(pointData.out<500){info.push("<div><p style='color:green'>预测出站人数");}
      if(pointData.out>=500&&pointData.out<1000){info.push("<div><p style='color:sandybrown'>预测出站人数");}
      if(pointData.out>=1000){info.push("<div><p style='color:darkred'>预测出站人数");}
      info.push(": "+pointData.out+"</p></div>")

      let infoWindow = new AMap.InfoWindow({
        content: info.join(""),
        offset: new AMap.Pixel(10, -25)
      });
      infoWindow.open(this.map, positionResult.lnglat);
    },

    searchsite(site,i) {
      var index=i;
      var site =site;
      var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
        pageSize: 5,
        pageIndex: 1,
        city: "上海"//城市
      });
      //关键字查询
      let _this = this;
      placeSearch.search(site, function(status, result) {
        var lat = result.poiList.pois[0].location.lat;
        var lng = result.poiList.pois[0].location.lng;
        console.log(lat,lng,site);
        _this.drawsite(lng,lat,index);
      })

    },
    drawsite(lng,lat,index){
      var index=index;
      let icon = new AMap.Icon({
        image: "C:\\Users\\Jarvis2K\\Desktop\\小学期2\\Workspace\\management\\src\\resource\\reddot200.gif",
        size: new AMap.Size(52, 52),  //图标大小
        imageSize: new AMap.Size(200,200)
      });
      var marker = new window.AMap.Marker({
        position: [lng,lat],
        map:this.map,
        // icon: icon,
        icon:require("C:\\Users\\Jarvis2K\\Desktop\\小学期2\\Workspace\\management\\src\\resource\\reddot100.gif"),
        offset: new AMap.Pixel(-50, -50),
        extData : {"name":this.sitesinfo[index].name,"in":this.sitesinfo[index].inNum,"out":this.sitesinfo[index].outNum}
      });
    //     marker.on("click", () => {
    //   console.log(this.lnglatData[i]);
    // });
    // // 鼠标经过marker
    // marker.on("mouseover", () => {
    //   marker.setLabel({
    //     offset: new AMap.Pixel(20, 20), //设置文本标注偏移量
    //     content: `<div class='info'>经过了此点</div>`, //设置文本标注内容
    //     direction: "right", //设置文本标注方位
    //   });
    // });
    // // 鼠标离开marker
    // marker.on("mouseout", () => {
    //   marker.setLabel(null);
    // });
      //绑定点击事件
      marker.on('click',positionResult => {
        console.log(positionResult);
        let pointData = positionResult.target.getExtData();
        this.openInfo( positionResult ,pointData);
      });

    this.markerList.push(marker);
    this.map.add(marker);
    },
    drawChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById("main"));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '',
          x: 'center',
          color:'#ffffff'
        },

        legend: { // 图例配置选项
          orient: 'horizontal', //图例布局方式：水平 'horizontal' 、垂直 'vertical'
          x: 'left', // 横向放置位置，选项：'center'、'left'、'right'、'number'（横向值 px）
          y: 'top',// 纵向放置位置，选项：'top'、'bottom'、'center'、'number'（纵向值 px）
          data: ['猜想', '预期', '实际'],
          textStyle: {
            color: '#ffffff'                              // 图例文字颜色
          }
        },
        grid: { // 图表距离边框的距离，可用百分比和数字（px）配置
          top: '20%',
          left: '3%',
          right: '10%',
          bottom: '5%',
          containLabel: true
        },

        tooltip: {  // tooltip 用于控制鼠标滑过或点击时的提示框（下一章展开讲）
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器配置项。
            type: 'cross', // 'line' 直线指示器  'shadow' 阴影指示器  'none' 无指示器  'cross' 十字准星指示器。
            axis: 'auto', // 指示器的坐标轴。
            snap: true, // 坐标轴指示器是否自动吸附到点上
          },
          showContent: true,
        },
        toolbox: {   // 右上角的工具框（下一章展开讲）
          feature: {
            saveAsImage: {} //下载按钮
          }
        },

        xAxis: {
          name: '时间',
          type: 'category',
          axisLine: {
            lineStyle: { // X 轴颜色配置
              color: '#ffffff'
            }
          },
          axisLabel: {
            rotate: 45, // X 轴标签文字旋转角度
            interval: 0  //设置 X 轴数据间隔几个显示一个，为0表示都显示
          },
          boundaryGap: false, //数据从 Y 轴起始
          data: ['6时', '8时', '10时', '12时', '14时', '16时', '18时', '20时', '22时', '24时', ]
        },

        yAxis: {
          name: '人次',
          type: 'value',
          min: 0, // 配置 Y 轴刻度最小值
          max: 4000,  // 配置 Y 轴刻度最大值
          splitNumber: 7,  // 配置 Y 轴数值间隔
          axisLine: {
            lineStyle: {   // Y 轴颜色配置
              color: '#ffffff'
            }
          },
        },

        series: [
          {
            name: '猜想',
            data: [454, 226, 891, 978, 901, 581, 400, 543, 272, 955, 1294, 1581],
            type: 'line',
            symbolSize: function (value) {  // 点的大小跟随数值增加而变大
              return value / 150;
            },
            symbol: 'circle',
            itemStyle: {
              normal: {
                label: {
                  show: true
                },
                lineStyle: {
                  color: 'rgba(0,0,0,0)'// 折线颜色设置为0，即只显示点，不显示折线
                }
              }
            }
          },

          {
            name: '预期',
            data: [2455, 2534, 2360, 2301, 2861, 2181, 1944, 2197, 1745, 1810, 2283, 2298],
            type: 'line',
            symbolSize: 8,  //设置折线上圆点大小
            itemStyle: {
              normal: {
                label: {
                  show: true // 在折线拐点上显示数据
                },
                lineStyle: {
                  width: 3,  // 设置虚线宽度
                  type: 'dotted'  // 虚线'dotted' 实线'solid'
                }
              }
            }
          },

          {
            name: '实际',
            data: [1107, 1352, 1740, 1968, 1647, 1570, 1343, 1757, 2547, 2762, 3170, 3665],
            type: 'line',
            symbol: 'circle', // 实心圆点
            smooth: 0.5, // 设置折线弧度
          }
        ],
        color: ['#ffffff', '#FFCC99', '#ffffff'] // 三个折线的颜色
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  }
}

</script>
<style scoped>


.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 450px;
  height: 85%;
  position: absolute;
  right: 10px;
  top:80px;
  transition: 1s linear;
  background-color: rgba(31,97,167,0.6);
  /*background-color: #0d5ab1;*/
  z-index: 10;
}

.scope {
  position: absolute;
  left: 10px;
  top:90px;
  background-color: rgba(31,97,167,0.6);
  /*background-color: #0d5ab1;*/
  z-index: 10;
}

.scope2 {
  position: absolute;
  left: 10px;
  top:130px;
  background-color: rgba(31,97,167,0.6);
  /*background-color: #0d5ab1;*/
  z-index: 10;
}

  #label{
  position: absolute;
    right: 650px;
    top:100px;
    z-index: 10;
}

  #container {
  width: 100%;
  height: 725px;
    z-index: 0;
  margin-top: 0px;
  border: 0px;
}

.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(100px);
  opacity: 0;
}

/*.v-enter-active{*/
/*  animation: slidein 1s linear;*/
/*}*/
/*.v-leave-active{*/
/*  animation: slidein 1s linear reverse;*/
/*}*/
/*@keyframes slidein {*/
/*  from{*/
/*    transform: translateX(+50%);*/
/*  }*/
/*  to{*/
/*    transform: translateX(0px);*/
/*  }*/
/*}*/
/*最外层透明*/
::v-deep .el-table,
::v-deep .el-table__expanded-cell {
  background-color: transparent !important;
}
/* 表格内背景颜色 */
/*::v-deep .el-table th,*/
::v-deep .el-table tr,
::v-deep .el-table td {
  background-color: transparent !important;
}

.info-middle  {
  float: left;
  margin-right: 6px;
  background-color: #0d5ab1;
}

/deep/.el-table__body-wrapper::-webkit-scrollbar{
  width: 0;
}
</style>