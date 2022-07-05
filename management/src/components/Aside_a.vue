<template>
  <div>
    <el-menu
        style="width: 200px;min-height: calc(100vh - 50px)"
        default-active="write"
        router
        :default-active="this.$router.path"
        class="el-menu-vertical-demo">
      <!--            @open="handleOpen"-->
      <!--            @close="handleClose"-->
      <el-submenu index="1">
        <template #title>合同管理</template>
        <el-menu-item index="admin/write" :rout="{path:'admin/write'}">起草合同</el-menu-item>
        <el-menu-item index="sign" :rout="{path:'/sign'}">会签合同</el-menu-item>
        <el-menu-item index="finalize" :rout="{path:'/finalize'}">定稿合同</el-menu-item>
        <el-menu-item index="check" :rout="{path:'/check'}">审批合同</el-menu-item>
        <el-menu-item index="qianding" :rout="{path:'/qianding'}">签订合同</el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template #title>查询统计</template>
        <el-menu-item index="sign" :rout="{path:'/sign'}">合同信息查询</el-menu-item>
        <el-menu-item index="sign" :rout="{path:'/sign'}">合同流程查询</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>

</style>