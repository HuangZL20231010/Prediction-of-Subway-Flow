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
  <div class="subway-container">
    <div id="mysubway"></div>
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
      map:null,
      mysubway: null,
    }
  },
  mounted() {
    this.initMap();
  },
  methods: {
    initsub() {
      this.mysubway = subway("mysubway", { easy: 1 });
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
      linesearch.search("10号线", function(status, result) {
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
<style>
  #container {
  width: 100%;
  height: 900px;
  margin: 0px;
  border: 1px;
}
</style>