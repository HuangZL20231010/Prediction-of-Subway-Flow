<!--<template>-->

<!--    <div id="container"></div>-->


<!--</template>-->

<!--<script>-->

<!--import AMapLoader from '@amap/amap-jsapi-loader';-->


<!--export default {-->

<!--  data() {-->
<!--    return {-->
<!--      map: null, //初始化 map 对象-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    initMap() {-->
<!--      AMapLoader.load({-->
<!--        key: "ed2ea36f8564541569c370254845d93d", //此处填入我们注册账号后获取的Key-->
<!--        version:"2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15-->
<!--        plugins:['AMap.LineSearch'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等-->
<!--      }).then((AMap) => {-->
<!--        this.map = new AMap.Map("container", { //设置地图容器id-->
<!--          viewMode: "3D", //是否为3D地图模式-->
<!--          zoom: 11, //初始化地图级别-->
<!--          mapStyle: 'amap://styles/blue',-->
<!--          center: [121.430041,31.154579], //初始化地图中心点位置-->
<!--        });-->
<!--        this.lineSearch();-->
<!--      }-->
<!--      ).catch(e => {-->
<!--        console.log(e);-->
<!--      })-->
<!--    },-->
<!--    lineSearch() {-->
<!--      var linesearch = null;-->
<!--      linesearch = new AMap.LineSearch({-->
<!--        pageIndex: 1,-->
<!--        city: '西安',-->
<!--        pageSize: 2,-->
<!--        extensions: 'all'-->
<!--      });-->

<!--      let _this = this;-->
<!--      linesearch.search('411', function(status, result) {-->
<!--        _this.map.clearMap()-->
<!--        if (status === 'complete' && result.info === 'OK') {-->
<!--          console.log('result', result)-->
<!--          _this.lineSearchData(result);-->
<!--        } else {-->
<!--          alert(result);-->
<!--        }-->
<!--      });-->
<!--    },-->
<!--    lineSearchData(data) {-->
<!--      var lineArr = data.lineInfo;-->
<!--      var lineNum = data.lineInfo.length;-->
<!--      if (lineNum == 0) {-->
<!--      } else {-->
<!--        for (var i = 0; i < lineNum; i++) {-->
<!--          var pathArr = lineArr[i].path;-->
<!--          var stops = lineArr[i].via_stops;-->
<!--          var startPot = stops[0].location;-->
<!--          var endPot = stops[stops.length - 1].location;-->
<!--          if (i == 0) //作为示例，只绘制一条线路-->
<!--            this.drawbusLine(startPot, endPot, pathArr);-->
<!--        }-->
<!--      }-->
<!--    },-->
<!--    drawbusLine(startPot, endPot, BusArr) {-->
<!--      //绘制起点，终点-->
<!--      new AMap.Marker({-->
<!--        map: this.map,-->
<!--        position: startPot, //基点位置-->
<!--        icon: "https://webapi.amap.com/theme/v1.3/markers/n/start.png",-->
<!--        zIndex: 10-->
<!--      });-->
<!--      new AMap.Marker({-->
<!--        map: this.map,-->
<!--        position: endPot, //基点位置-->
<!--        icon: "https://webapi.amap.com/theme/v1.3/markers/n/end.png",-->
<!--        zIndex: 10-->
<!--      });-->
<!--      //绘制乘车的路线-->
<!--      var busPolyline = null;-->
<!--      busPolyline = new AMap.Polyline({-->
<!--        map: this.map,-->
<!--        path: BusArr,-->

<!--        strokeColor: "#09f",//线颜色-->
<!--        strokeOpacity: 0.8,//线透明度-->
<!--        isOutline:true,-->
<!--        outlineColor:'white',-->
<!--        strokeWeight: 6//线宽-->
<!--      });-->
<!--      this.map.setFitView();-->
<!--    }-->
<!--  },-->

<!--  mounted() {-->
<!--    //DOM初始化完成进行地图初始化-->
<!--    this.initMap();-->
<!--  }-->
<!--}-->

<!--</script>-->

<!--<style>-->
<!--#map-container {width:300px; height: 180px; }-->

<!--#container {-->
<!--  width: 100%;-->
<!--  height: 900px;-->
<!--  margin: 0px;-->
<!--  border: 1px;-->
<!--}-->
<!--</style>-->


<template>
  <div >

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

                  prop="rank"
                  label="排名"
                  width="70">
              </el-table-column>
              <el-table-column
                  style="background-color: rgba(31,97,167,0.6)"
                  prop="site"
                  label="站点名称"
                  width="200">
              </el-table-column>
              <el-table-column
                  prop="flow"
                  label="预测人流量"
                  width="130">
              </el-table-column>
            </el-table></div>

        </el-card>
    </transition>


    <div id="label">
      <p style="font-size: 20px;font-weight: bolder;color:white;
      text-decoration: underline;letter-spacing:4px"
      >
        上海轨道交通客流量预测{{nowTime}}</p>
      <el-button type="primary" size="small" autocomplete="off" @click="testclick()">弹窗测试</el-button>
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

      clicktime:0,
      nowTime:'',
      showw:true,
      tableData: [{rank:1,site:"10号线",flow:6324},{rank:2,site:"5号线",flow:9324},{rank:3,site:"9号线",flow:3324}
      ,{rank:4,site:"8号线",flow:7777},{rank:5,site:"3号线",flow:6344},{rank:6,site:"2号线",flow:1324},{rank:7,site:"12号线",flow:324}],
      map:null,
      mysubway: null,
    }
  },
  mounted() {
    this.initMap();
    this.drawChart();
},
  methods: {
    testclick(){

      if(this.clicktime==0){
        this.showw = !this.showw;
        this.clicktime++;
      }
      if(this.clicktime==1){
        this.drawChart();
        this.clicktime=0;
      }
      this.toiletSelect();
      request.post("/api/user/test",1).then(res =>{
        console.log(res)
      })

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
        plugins:['AMap.LineSearch','AMap.PlaceSearch'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap)=>{
        this.map = new AMap.Map("container",{  //设置地图容器id
          resizeEnable: true,
          zoom: 12.5,           //初始化地图级别
          mapStyle: 'amap://styles/blue',
          center: [121.45088, 31.25145]//初始化地图中心点位置
        });

        this.lineSearch();

      }).catch(e=>{
        console.log(e);
      })
    },
    lineSearch() {
      var linesearch = null;
       linesearch = new window.AMap.LineSearch({
         pageIndex: 1, // 第一页的线路
         city: '上海',
         pageSize: 1, // 每一页的线路条数
        extensions: 'all' // 所有线路类型
      })

      let _this = this;
      var name = ['10号线','2号线','1号线'];
      for(var i = 0; i < 3; i++){
        var index=0;
        linesearch.search(name[i], function(status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 查询成功调用
            console.log('ok')
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
      var colors=['blue','red','green'];
      let busPolyline = new window.AMap.Polyline({
        map: this.map,
        path: BusArr,
        strokeColor: colors[index],// 线颜色
        strokeOpacity: 0.7,// 线透明度
        isOutline: false,
        outlineColor: 'white',
        strokeWeight: 3 // 线宽
      })
    },
    toiletSelect() {
      var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
        pageSize: 5,
        pageIndex: 1,
        city: "上海"//城市
      });
//关键字查询
      let _this = this;
      placeSearch.search('上海南站', function(status, result) {
        var lat = result.poiList.pois[0].location.lat;
        var lng = result.poiList.pois[0].location.lng;
        console.log(lat,lng);
        _this.drawsite(lng,lat);
      })

    },
    drawsite(lng,lat){
      var marker = new window.AMap.Marker({
        position: [lng,lat],
        map:this.map,
        icon: "https://h5.newljlx.com/static/jpjj/cesuo1.png",
      })
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
  top:100px;
  transition: 1s linear;
  background-color: rgba(31,97,167,0.6);
  /*background-color: #0d5ab1;*/
  z-index: 10;
}

  #label{
  position: absolute;
    right: 850px;
    top:120px;
    z-index: 10;
}

  #container {
  width: 100%;
  height: 900px;
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


/deep/.el-table__body-wrapper::-webkit-scrollbar{
  width: 0;
}
</style>