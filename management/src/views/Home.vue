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
            <span style="color: white;font-weight: bolder;font-size: 20px">今日客流量预测</span>
            <div class="hygrometer" ref="hygrometer" style="height: 200px;width: 350px"></div>
          </div>
          <div  class="clearfix">
            <span style="color: white;font-weight: bolder;font-size: 20px">图表测试</span>
          </div>

          <div>
            <span style="color: white;font-weight: bolder;font-size: 20px">预测流量排行</span>
            <el-table
                :header-cell-style="{background:'#206cbb',color:'#ffffff',fontWeight:'bolder',fontSize: '16px'}"
                :cell-style="{color:'#ffffff'}"
                :data="tableData"
                style="width: 340px;"
                :row-class-name="tableRowClassName">
              <el-table-column
                  style="background-color: rgba(31,97,167,0.6);font-size: 30px"
                  prop="rank"
                  label="排名"
                  width="70">
              </el-table-column>
              <el-table-column
                  style="background-color: rgba(31,97,167,0.6)"
                  prop="site"
                  label="站点"
                  width="170">
              </el-table-column>
              <el-table-column
                  prop="flow"
                  label="预测人流量"
                  width="100">
              </el-table-column>
            </el-table></div>

        </el-card>
    </transition>


    <div id="label">
      <p style="font-size: 20px;font-weight: bolder;color:white;
      text-decoration: underline;letter-spacing:4px"
      >
        上海轨道交通客流量预测{{nowTime}}</p>
      <el-button type="primary" size="small" autocomplete="off" @click="showw = !showw">弹窗测试</el-button>
    </div>

    <div id="container" ></div>
  </div>


</template>
<script type="text/javascript">
window._AMapSecurityConfig = {
  securityJsCode:'53df3829a7c8dcf72f5a2810c94b1f1d',
}
import AMapLoader from '@amap/amap-jsapi-loader';
export default {
  data() {
    return {


      nowTime:'',
      showw:true,
      tableData: [{rank:1,site:"10号线",flow:6324},{rank:2,site:"5号线",flow:9324}],
      map:null,
      mysubway: null,
      option : {
        legend: {
          icon: 'rect',//形状  类型包括 circle，rect,line，roundRect，triangle，diamond，pin，arrow，none
          itemWidth: 10,  // 设置宽度
          itemHeight: 4, // 设置高度
          itemGap: 24, // 设置间距
          data: ['温度', '湿度'],
          textStyle: {
            //文字样式
            color: '#c1dafc',
            fontSize: '12'
          },
          right: '30%',
          selectedMode: 'single'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          splitLine: {
            lineStyle: {
              color: '#29457e'
            }
          },
          axisLabel:{
            interval: 0
          },
          data: ['1小时', '2小时', '3小时', '4小时', '5小时', '6小时', '7小时', '8小时',
            '9小时', '10小时', '11小时', '12小时', '13小时', '14小时', '15小时', '16小时',
            '17小时', '18小时', '19小时', '20小时', '21小时', '22小时', '23小时','24小时']
        },
        yAxis: [
          {
            type: 'value',
            axisTick:{       //y轴刻度线
              show: false
            },
            name: '单位：(°C)',
            min: 0,
            max: 50,
            interval: 10,
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '温度',
            type: 'line',
            itemStyle : {
              normal : {
                lineStyle:{
                  color:'#b1de6a'
                }
              }
            },
            data: [2.0, 4.9, 7.0, 23.2, 25.6, 23, 25, 21, 26, 20, 25, 36, 48, ]
          },
          {
            name: '湿度',
            type: 'line',
            itemStyle : {
              normal : {
                lineStyle:{
                  color:'#4ab0ee'
                }
              }
            },
            data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 75.6, 82.2, 54, 63]
          }
        ]
      }

    }
  },
  mounted() {
    this.initMap();
    let hygrometer = this.$echarts.init(this.$refs.hygrometer)
    hygrometer.setOption(option);
},
  methods: {
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
        plugins:['AMap.LineSearch'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap)=>{
        this.map = new AMap.Map("container",{  //设置地图容器id
          resizeEnable: true,
          zoom: 12,           //初始化地图级别
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
      var name = '10号线';
      linesearch.search("1号线", function(status, result) {
        console.log('查询出错')
        if (status === 'complete' && result.info === 'OK') {
          // 查询成功调用
          console.log('查询出错')
          _this.lineSearchData(result)
        } else {
          console.log('查询出错')
        }
      })
    },
    lineSearchData(data) {
      console.log('yes');
      var lineArr = data.lineInfo;
      var lineNum = data.lineInfo.length;
      if (lineNum == 0) {
        console.log(lineNum)
      } else {
        for (var i = 0; i < lineNum; i++) {
          var pathArr = lineArr[i].path;
          if (i == 0) // 只绘制一条线路，就是需要高亮的地铁线路
            this.drawbusLine(pathArr);
        }
      }

    },
    drawbusLine(BusArr) {
      let busPolyline = new window.AMap.Polyline({
        map: this.map,
        path: BusArr,
        strokeColor: 'red',// 线颜色
        strokeOpacity: 0.4,// 线透明度
        isOutline: false,
        outlineColor: 'white',
        strokeWeight: 3 // 线宽
      })
    }
  }
}

</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  list-style: none;
}

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
  width: 400px;
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

</style>