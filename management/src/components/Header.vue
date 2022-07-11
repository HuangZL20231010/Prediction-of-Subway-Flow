<template>

  <div id="tou">
    <el-row>
      <el-col :span="12"><div style="background-color: #0d5ab1">
        <el-image
          style="width: 170px; height: 60px;margin-left: 70px"
          :src="require('C:\\Users\\Jarvis2K\\Desktop\\小学期2\\Workspace\\management\\src\\resource\\shdt.png')"
          :fit="container"
      ></el-image></div></el-col>
      <el-col :span="12">
        <div >
        <el-menu
          background-color="#0d5ab1"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          text-color="#fff"
          router
          :default-active="this.$router.path"
          style="height: 65px;font-weight: bolder;text-align: center;margin: 0;background-position: center"
          active-text-color="#ffd04b"

        >

        <el-menu-item
            style="height: 60px;font-size: 20px;text-align: center;margin-left: 200px;background-position: center"
            index="home"
            :rout="{path:'/home'}"
        >客流预测</el-menu-item>
          <el-menu-item
              style="height: 60px;font-size: 20px;text-align: center;"
              index="overview"
              :rout="{path:'/overview'}"
          >客流总览</el-menu-item>
          <el-menu-item
              style="height: 60px;font-size: 20px;text-align: center;"
              index="history"
              :rout="{path:'/history'}"
          >历史数据查询</el-menu-item>
          <el-menu-item
              style="height: 60px;font-size:20px;text-align: center;"
              index="ZL"
          >出行推荐</el-menu-item>


      </el-menu></div></el-col>
    </el-row>


  </div>


</template>

<script>
export default {
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
  el-menu{
    height: 200px;
  }
</style>