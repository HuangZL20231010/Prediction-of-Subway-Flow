<template>
  <div :style="background" class="bgBackground" style="height:  60px;line-height: 50px ;border-bottom: 10px solid #ccc;display: flex ">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: royalblue">合同操作系统</div>
    <div style="flex: 1"></div>
    <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" style="margin-right: 20px"></el-avatar>
    <div style="width: 100px"><el-dropdown>
      <el-button type="primary" style="margin-right: 130px">
        {{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item @click.native="$router.push('/login')" >退出系统</el-dropdown-item>
        <!--        <el-dropdown-item>螺蛳粉</el-dropdown-item>-->
        <!--        <el-dropdown-item>双皮奶</el-dropdown-item>-->
        <!--        <el-dropdown-item>蚵仔煎</el-dropdown-item>-->
      </el-dropdown-menu>
    </el-dropdown>
    </div>

  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{
      user:{},
      background: {
        // 背景图片地址
        backgroundImage: 'url(' + require('D:\\Web\\Vue\\management\\src\\resource\\f3c15e7cf8eb6be9b0bf4b9a5dac2961.jpg') + ')',
        // 背景图片是否重复
        backgroundRepeat: 'no-repeat',
        // 背景图片大小
        backgroundSize: 'cover',
        // 背景颜色
        backgroundColor: '#000',
        // 背景图片位置
        backgroundPosition: 'center top'
      },
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
  }
}
</script>

<style scoped>

</style>