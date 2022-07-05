<template>
  <div style="height: 50px;line-height: 50px ;border-bottom: 1px solid #ccc;display: flex ">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: royalblue">合同后台操作系统</div>
    <div style="flex: 1"></div>
    <div style="width: 100px"><el-dropdown>
      <el-button type="primary">
        用户名称<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>{{ user.username }}</el-dropdown-item>
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

}
</script>

<style scoped>

</style>