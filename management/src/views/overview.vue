<template>
<div style="background-color: #033447;height: 100%">
  <div id="main" style="width: 100%; height: 780px"></div>
</div>
</template>

<script>
export default {
  name: "overview",
  mounted() {
    this.drawChart();
  },
  methods:{
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
            interval: 3  //设置 X 轴数据间隔几个显示一个，为0表示都显示
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
            name: '一号线',
            data: [454, 226, 891, 978, 901, 581, 400, 543, 272, 955, 1294, 1581],
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
            name: '三号线',
            data: [1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3],
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