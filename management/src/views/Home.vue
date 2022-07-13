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
    <div>
      <el-time-select
          class="scope3"
          style="width: 150px"
          v-model="selectime"
          @change="choosetime()"
          :picker-options="{
    start: '05:00',
    step: '00:10',
    end: '22:40'
  }"
          placeholder="选择时间">
      </el-time-select>
    </div>
    <!--    card-->
    <transition  name="slide-fade" style="z-index: 10" >

        <el-card v-if='showw' class="box-card" style="background-color: rgba(31,97,167,0.6);">
          <div slot="header" class="clearfix">
            <span style="color: white;font-weight: bolder;font-size: 20px;margin-bottom: 20px">今日客流量预测 站点名： {{chartsitename}}</span>
            <div id="main" style="width: 430px; height: 300px"></div>
          </div>
          <div>
            <span style="color: white;font-weight: bolder;font-size: 20px;margin-bottom: 20px;margin-top: 0px">预测流量排行 预测时间点：{{returntime}}</span>
<!--            <el-table-->
<!--                v-loading="loading"-->
<!--                element-loading-text="拼命加载中"-->
<!--                element-loading-spinner="el-icon-loading"-->
<!--                element-loading-background="rgba(0, 0, 0, 0.8)"-->
<!--                :header-cell-style="{background:'#206cbb',color:'#ffffff',fontWeight:'bolder',fontSize: '16px'}"-->
<!--                :cell-style="{color:'#ffffff',fontSize: '16px'}"-->
<!--                :data="tableData"-->
<!--                style="width: 400px;"-->
<!--                height="250px"-->
<!--                :row-class-name="tableRowClassName">-->
<!--              <el-table-column-->
<!--                  style="background-color: rgba(31,97,167,0.6);font-weight: bolder"-->

<!--                  prop="stationID"-->
<!--                  label="排名"-->
<!--                  width="70">-->
<!--              </el-table-column>-->
<!--              <el-table-column-->
<!--                  style="background-color: rgba(31,97,167,0.6)"-->
<!--                  prop="name"-->
<!--                  label="站点名称"-->
<!--                  width="200">-->
<!--                <template slot-scope="scope">-->
<!--                  &lt;!&ndash; 注意：这个地方要传参数进去才能进行操作  函数名称(scope.row) &ndash;&gt;-->
<!--                  <div @click="zoomsite(scope.row)">{{ scope.row.name }}</div>-->
<!--                </template>-->
<!--              </el-table-column>-->
<!--              <el-table-column-->
<!--                  prop="inNum"-->
<!--                  label="预测人流量"-->
<!--                  width="130">-->
<!--              </el-table-column>-->
<!--            </el-table>-->
            <div id="bar" style="width: 400px;height:250px;margin-top: 0px"></div>
          </div>
        </el-card>
    </transition>


    <div id="label" @click="testclick">
      <p style="font-size: 20px;font-weight: bolder;color:white;
      text-decoration: underline;letter-spacing:4px">
        上海轨道交通客流量预测</p>
      <p style="color:white">当点时间：{{nowDate}}</p>
<!--      <el-button type="primary" size="small" autocomplete="off" @click="testclick()">弹窗测试</el-button>-->
    </div>

    <div id="container" ></div>
    <div id="result" tabindex="0"></div>
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
      xValue:[],
      yValue:[],
      chartdatain:[],
      chartdataout:[],
      chartsitename:'未选择',
      selectime:'',
      nowDate: null,
      nowtimer: "",
      radio1:'',
      sites:['同济大学','上海南站','东方明珠'],
      clicktime:0,
      nowTime:'22:00',
      xzhou:[0,1,2],
      data1:[],
      busPolyline:null,
      showw:true,
      sitesinfo:[],
      markerList : [],
      tableData: [],
      map:null,
      mysubway: null,
    }
  },
  watch: {
    //监听的变量名
    xValue:{
      handler(newName, oldName) {
        this.showrank();
      },
      immediate: true
    }
  },
  created() {
    this.nowtimer = setInterval(this.gettime, 1000);
  },
  mounted() {
    this.initMap();
    this.drawChart();
    request.get('/api/StationFlow/getStationInNumRank').then(res =>{
      // this.tableData = res.data;
      // this.nowTime=this.tableData[0].time;
      for(var i=0;i<res.data.length;i++){
        this.xValue[i]=(res.data[i].inNum+res.data[i].outNum);
        this.yValue[i]=(res.data[i].name);
        this.returntime=res.data[i].time.substring(11);
      }
      this.xValue.reverse();
      this.yValue.reverse();
    })
    this.showrank();
},
  methods: {

    showrank() {
      let rank = this.$echarts.init(document.getElementById("bar"));
      var option = {
        color: ['#d84430'],
        tooltip: {
          show: true
        },

        yAxis: {
          axisTick: {
            show: false
          },
          axisLine: {
            show: false,
          },
          axisLabel: {
            inside: true,
            verticalAlign: 'bottom',
            lineHeight: 40,
            color: '#DDDFEB',
            formatter: function (value, index) {   // 设置y轴文字的颜色
              if (index >2) {
                return '{first|' + value + '}'
              } else {
                return '{other|' + value + '}'
              }
            },
            rich: {
              other: {
                color: '#DDDFEB',

                opacity: 1
              },
              first: {
                fontSize:'16',
                color: '#FFFFFF'
              }
            }
          },
          data: this.yValue
        },
        xAxis: {
          nameTextStyle: {
            color: 'rgba(255, 255, 255, 0.8)',
            align: 'right'
          },
          splitLine: {
            show: false,
          },
          axisLine: {
            show: false,
          },
          axisLabel: {
            color: 'rgba(255, 255, 255, 0.8)'
          },
        },
        grid: {
          top: '10%',
          bottom: '0%',
          left: '0%',
          right: '0%'
        },
        dataZoom: [
          {
            type: "slider",
            show: true,//隐藏或显示（true）组件
            backgroundColor: "rgb(19, 63, 100)", // 组件的背景颜色。
            fillerColor: "rgb(16, 171, 198)", // 选中范围的填充颜色。
            borderColor: "rgb(19, 63, 100)", // 边框颜色
            showDetail: false, //是否显示detail，即拖拽时候显示详细数值信息
            startValue: 14, // 数据窗口范围的起始数值
            endValue: 10, // 数据窗口范围的结束数值（一页显示多少条数据）
            yAxisIndex: [0, 1],//控制哪个轴，如果是 number 表示控制一个轴，如果是 Array 表示控制多个轴。此处控制第二根轴
            filterMode: "empty",
            width: 8, //滚动条高度
            height: "100%", //滚动条显示位置
            right: 0, // 距离右边
            handleSize: 0,//控制手柄的尺寸
            zoomLoxk: true, // 是否锁定选择区域（或叫做数据窗口）的大小
            top: "middle",
          },
          {
            //没有下面这块的话，只能拖动滚动条，鼠标滚轮在区域内不能控制外部滚动条
            type: "inside",
            yAxisIndex: [0, 1],//控制哪个轴，如果是 number 表示控制一个轴，如果是 Array 表示控制多个轴。此处控制第二根轴
            zoomOnMouseWheel: false, //滚轮是否触发缩放
            moveOnMouseMove: true, //鼠标移动能否触发平移
            moveOnMouseWheel: true,//鼠标滚轮能否触发平移
          },
        ],
        series: [{
          name: '客流量预测',
          barWidth: 15,
          type: 'bar',
          data: this.xValue,
          itemStyle: {
            normal: {
              borderRadius: [3, 20, 20, 3],
              color: function (params) {   // 设置柱形图的颜色
                if (params.dataIndex === 14) {
                  return '#d84430'
                } else if (params.dataIndex === 13) {
                  return '#f38237'
                } else if (params.dataIndex === 12) {
                  return '#e2aa20'
                } else {
                  return '#608289'
                }
              }
            },
          }
        }]
      };
      // 使用刚指定的配置项和数据显示图表。
      rank.setOption(option);
      window.addEventListener('resize', () => {
        rank.resize()
      })
    },
    choosetime(){
      console.log(this.selectime)
      if(this.radio1.length==3){var line =parseInt(this.radio1.charAt(0))}
      if(this.radio1.length==4){var line =parseInt(this.radio1.substring(0,2))}
      request.get('/api/StationFlow/StationByline/'+line+'/'+this.selectime).then(res =>{
        // this.sitesinfo = res.data;
        console.log(res);
        this.sitesinfo=res.data;
        this.map.remove(this.markerList);
        for(var i =0;i<this.sitesinfo.length;i++){
          this.drawsite(this.sitesinfo[i].longitude,this.sitesinfo[i].latitude,i)
        }
      });
      request.get('/api/StationFlow/getStationInNumRank/'+this.selectime).then(res =>{
        for(var i=0;i<res.data.length;i++){
          this.xValue[i]=(res.data[i].inNum+res.data[i].outNum);
          this.yValue[i]=(res.data[i].name);
          this.returntime=res.data[i].time.substring(11);
        }
        this.xValue.reverse();
        this.yValue.reverse();
      })

    },
    gettime() {
      this.nowDate = new Date().toLocaleString();
    },
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
      request.get('/api/StationFlow/StationByline/'+line).then(res =>{
        // this.sitesinfo = res.data;
        console.log(res);
        this.sitesinfo=res.data;
        for(var i =0;i<this.sitesinfo.length;i++){
          this.drawsite(this.sitesinfo[i].longitude,this.sitesinfo[i].latitude,i)
        }
      })
    },
    qingqiu(line){
      request.post('/api/StationFlow/getLineInNumByID',line).then(res =>{
        for(var i=0;i<res.data.length-1;i++){
          this.xzhou.push(res.data[i].first);
          this.data1.push(res.data[i].second);
        }
        this.drawChart();
      })
    },
    lineChange(){
      this.selectime=null;
      request.get('/api/StationFlow/getStationInNumRank/').then(res =>{
        for(var i=0;i<res.data.length;i++){
          this.xValue[i]=(res.data[i].inNum+res.data[i].outNum);
          this.yValue[i]=(res.data[i].name);
          this.returntime=res.data[i].time.substring(11);
        }
        this.xValue.reverse();
        this.yValue.reverse();
      })
      this.map.remove(this.busPolyline);
      this.map.remove(this.markerList);
      var linename= '地铁'+this.radio1;
      if(this.radio1=='1号线'){
        this.map.setCenter([121.45559270874024,31.247195689843117])
        this.map.setZoom(12)
        this.Addpoint(1);
        // this.qingqiu(1);
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
      if(this.radio1=='5号线'){
        this.map.setCenter([ 121.43856,31.03321])
        this.map.setZoom(11.2)
        this.Addpoint(5);
      }
      if(this.radio1=='6号线'){
        this.map.setCenter([ 121.533713,31.234293])
        this.map.setZoom(12.2)
        this.Addpoint(6);
      }
      if(this.radio1=='7号线'){
        this.map.setCenter([ 121.48336,31.221424])
        this.map.setZoom(13.2)
        this.Addpoint(7);
      }
      if(this.radio1=='8号线'){
        this.map.setCenter([ 121.48336,31.221424])
        this.map.setZoom(13.2)
        this.Addpoint(8);
      }
      if(this.radio1=='9号线'){
        this.map.setCenter([121.439427,31.159294])
        this.map.setZoom(11.2)
        this.Addpoint(9);
      }
      if(this.radio1=='10号线'){
        this.map.setCenter([ 121.497635,31.267927])
        this.map.setZoom(11.2)
        this.Addpoint(10);
      }
      if(this.radio1=='11号线'){
        this.map.setCenter([ 121.425343,31.228269])
        this.map.setZoom(11.2)
        this.Addpoint(11);
      }
      if(this.radio1=='12号线'){
        this.map.setCenter([ 121.48336,31.221424])
        this.map.setZoom(12.2)
        this.Addpoint(12);
      }
      if(this.radio1=='13号线'){
        this.map.setCenter([121.403703,31.230279])
        this.map.setZoom(13.2)
        this.Addpoint(13);
      }
      if(this.radio1=='16号线'){
        this.map.setCenter([ 121.728256,31.061244])
        this.map.setZoom(12.2)
        this.Addpoint(16);
      }

      this.lineSearch(linename);
    },

    testclick(){
      this.getRemote();
      // request.post('/api/StationFlow/getLineInNumByID',1).then(res =>{
      //   this.sitesinfo = res.data;
      //   console.log(res);
      // })
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
        this.showrank();
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
        plugins:['AMap.LineSearch','AMap.PlaceSearch','AMap.ToolBar', 'AMap.Scale','AMap.Transfer'],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
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
    getRemote(){
      this.map.remove(this.busPolyline);
      var transOptions = {
        map: this.map,
        panel: "result",
        city: '上海市',                            //公交城市
        policy: AMap.TransferPolicy.LEAST_TIME //乘车策略
      };
        var transfer = new AMap.Transfer(transOptions);
        //根据起、终点坐标查询公交换乘路线
        //trans.search([116.379028, 39.865042], [116.427281, 39.903719]);

      transfer.search([{keyword:'上海南站'},{keyword:'同济大学'}], function(status, result){
          console.log(result);
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
      if(pointData.in<100){info.push("<div><p style='color:green'>预测入站人数");}
      if(pointData.in>=100&&pointData.in<400){info.push("<div><p style='color:sandybrown'>预测入站人数");}
      if(pointData.in>=400){info.push("<div><p style='color:darkred'>预测入站人数");}
      info.push(": "+pointData.in+"</p></div>")
      if(pointData.out<100){info.push("<div><p style='color:green'>预测出站人数");}
      if(pointData.out>=100&&pointData.out<400){info.push("<div><p style='color:sandybrown'>预测出站人数");}
      if(pointData.out>=400){info.push("<div><p style='color:darkred'>预测出站人数");}
      info.push(": "+pointData.out+"</p></div>")
      info.push("<div><p style='color:LightSalmon'>时间点");
      info.push(": "+pointData.time.substring(11)+"</p></div>")

      let infoWindow = new AMap.InfoWindow({
        content: info.join(""),
        offset: new AMap.Pixel(10, -25)
      });
      infoWindow.open(this.map, positionResult.lnglat);
      this.chartsitename=pointData.name;
      request.get('/api/StationFlow/getStationInOutNumByID/'+pointData.id).then(res =>{
          for(var i =0;i<res.data.length;i++){
            this.chartdatain[i]=res.data[i].innum;
            this.chartdataout[i]=res.data[i].outnum;
            this.xzhou[i]=res.data[i].time.substring(11);
          }
          console.log(this.chartdatain)
        this.drawChart();
      })

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
      var endicon=null;
      var icon1=new AMap.Icon({
        image: '/api/resource/green.gif',
        size: new AMap.Size(50, 50),  //图标所处区域大小
        imageSize: new AMap.Size(50,50) //图标大小
      });
      var icon2=new AMap.Icon({
        image: '/api/resource/warn.gif',
        size: new AMap.Size(50, 50),  //图标所处区域大小
        imageSize: new AMap.Size(50,50) //图标大小
      });
      var icon3=new AMap.Icon({
        image: '/api/resource/red.gif',
        size: new AMap.Size(50, 50),  //图标所处区域大小
        imageSize: new AMap.Size(50,50) //图标大小
      });

      if(this.sitesinfo[index].inNum+this.sitesinfo[index].outNum<400){
        endicon=icon1;
        var off =new AMap.Pixel(-25, -50);
      }
      if(this.sitesinfo[index].inNum+this.sitesinfo[index].outNum>=400&&this.sitesinfo[index].inNum+this.sitesinfo[index].outNum<900){
        endicon=icon2;
        var off =new AMap.Pixel(-25, -50);
      }
      if(this.sitesinfo[index].inNum+this.sitesinfo[index].outNum>=900){
        endicon=icon3;
        var off =new AMap.Pixel(-25, -50);
      }


      var marker = new window.AMap.Marker({
        position: [lng,lat],
        map:this.map,
        // icon: icon,
        icon:endicon,
        // icon:icon1,
        offset: off,
        extData : {"name":this.sitesinfo[index].name,"in":this.sitesinfo[index].inNum,"out":this.sitesinfo[index].outNum,
          'id':this.sitesinfo[index].stationID,'time':this.sitesinfo[index].time}
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
          data: ['进站流量', '出站流量'],
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
          start: 30,                                //数据窗口范围的起始百分比,表示30%
          end: 70,
          axisLine: {
            lineStyle: { // X 轴颜色配置
              color: '#ffffff'
            }
          },
          axisLabel: {
            rotate: 45, // X 轴标签文字旋转角度
            interval: 12,  //设置 X 轴数据间隔几个显示一个，为0表示都显示
          },
          boundaryGap: false, //数据从 Y 轴起始
          data: this.xzhou,
        },

        yAxis: {
          name: '人次',
          type: 'value',
          scale:true,
          // min: 0, // 配置 Y 轴刻度最小值
          // max: 4000,  // 配置 Y 轴刻度最大值
          splitNumber: 7,  // 配置 Y 轴数值间隔
          splitLine: { //网格线
            show: true
          },
          axisLine: {
            lineStyle: {   // Y 轴颜色配置
              color: '#ffffff'
            }
          },
        },

        series: [
          {
            name: '进站流量',
            // data: this.data1,
            data:this.chartdatain,
            type: 'line',
            symbolSize: function (value) {  // 点的大小跟随数值增加而变大
              return value / 150;
            },
            symbol: 'circle',
            itemStyle: {
              normal: {
                label: {
                  show: false
                },
                lineStyle: {
                  width: 3,  // 设置虚线宽度
                  type: 'solid'  // 虚线'dotted' 实线'solid'
                }
              }
            }
          },

          {
            name: '出站流量',
            data: this.chartdataout,
            type: 'line',
            symbolSize: 8,  //设置折线上圆点大小
            itemStyle: {
              normal: {
                label: {
                  show: false // 在折线拐点上显示数据
                },
                lineStyle: {
                  width: 3,  // 设置虚线宽度
                  type: 'solid'  // 虚线'dotted' 实线'solid'
                }
              }
            }
          },
        ],
        color: ['#FFD700', '#99CC66', ], // 三个折线的颜色
        // dataZoom: [
        //   {
        //     type: "slider", //隐藏或显示（true）组件
        //     show: true,
        //     backgroundColor: "rgb(19, 63, 100)", // 组件的背景颜色。
        //     fillerColor: "rgb(16, 171, 198)", // 选中范围的填充颜色。
        //     borderColor: "rgb(19, 63, 100)", // 边框颜色
        //     showDetail: false, //是否显示detail，即拖拽时候显示详细数值信息
        //     startValue: 0,
        //     endValue: 30,
        //     filterMode: "empty",
        //     width: "80%", //滚动条高度
        //     height: 20, //滚动条显示位置
        //     left: "center",
        //     zoomLoxk: true, // 是否锁定选择区域（或叫做数据窗口）的大小
        //     handleSize: 0, //控制手柄的尺寸
        //     bottom: 3, // dataZoom-slider组件离容器下侧的距离
        //   },
        //   {
        //     //没有下面这块的话，只能拖动滚动条，鼠标滚轮在区域内不能控制外部滚动条
        //     type: "inside",
        //     zoomOnMouseWheel: false, //滚轮是否触发缩放
        //     moveOnMouseMove: true, //鼠标滚轮触发滚动
        //     moveOnMouseWheel: true,
        //   },
        // ],
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
.scope3 {
  position: absolute;
  /*width: 10px;*/
  left: 10px;
  top:170px;
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
#result{

  height: 10%;
  width: 100%;
}

</style>