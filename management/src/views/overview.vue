<template>
  <div style="background-color: #033447;height: 100%">
    <div
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        id="main" style="width: 100%; height: 780px"></div>
    <el-button type="primary" size="small" autocomplete="off" @click="testclick()">弹窗测试</el-button>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "overview",
  data(){
    return{
      data1:[[],[],[],[],[]],
      xzhou:[],


    }
  },
  mounted() {
    this.drawChart();
    request.get('/api/StationFlow/getLineInNumByID/'+'1').then(res =>{
      for(var i=0;i<res.data.length-1;i++){
        this.xzhou.push(res.data[i].first);
        this.data1[0].push(res.data[i].second);
      }
    })
    this.qingqiu(2);
    this.qingqiu(3);
    this.openFullScreen2();
  },
  watch: {
    //监听的变量名
    data1:{
      handler(newName, oldName) {
        this.drawChart();
      },
      immediate: true
    }
  },
  methods:{
    openFullScreen2() {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 5000);
    },
    qingqiu(line){
      request.get('/api/StationFlow/getLineInNumByID/'+line).then(res =>{
        for(var i=0;i<res.data.length-1;i++){
          this.data1[line-1].push(res.data[i].second);
        }
        console.log(this.data1[1])
      })
    },
    testclick(){
      this.drawChart();
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
          data: ['一号线', '二号线', '三号线'],
          textStyle: {
            color: '#ffffff'                              // 图例文字颜色
          }
        },
        grid: { // 图表距离边框的距离，可用百分比和数字（px）配置
          top: '20%',
          left: '3%',
          right: '10%',
          bottom: '20%',
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
            saveAsImage: {
              color: '#ffffff'
            } //下载按钮
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
            interval: 10  //设置 X 轴数据间隔几个显示一个，为0表示都显示
          },
          boundaryGap: false, //数据从 Y 轴起始
          data: this.xzhou,
        },

        yAxis: {
          name: '人次',
          type: 'value',
          min: 0, // 配置 Y 轴刻度最小值
          max: 35000,  // 配置 Y 轴刻度最大值
          splitNumber: 7,  // 配置 Y 轴数值间隔
          axisLine: {
            lineStyle: {   // Y 轴颜色配置
              color: '#ffffff'
            }
          },
        },

        series: [
          {
            name: '一号线',
            data: this.data1[0],
            type: 'line',
            symbolSize: 8,
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
            name: '二号线',
            data: this.data1[1],
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
            name: '三号线',
            data: this.data1[2],
            type: 'line',
            symbolSize: 8,
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
          }
        ],
        color: ['pink', 'red', 'yellow'] // 三个折线的颜色
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  }
}

</script>

<style scoped>

</style>