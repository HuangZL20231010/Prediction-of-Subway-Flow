<template>
  <div>
        <el-menu
            style="width: 200px;min-height: calc(100vh - 50px)"
            default-active="home"
            router
            class="el-menu-vertical-demo">
<!--            @open="handleOpen"-->
<!--            @close="handleClose"-->
          <el-menu-item index="home" :rout="{path:'/write'}">我的主页</el-menu-item>
        <el-submenu index="1">
          <template #title>合同管理</template>
          <el-menu-item index="write" :rout="{path:'/write'}">起草合同</el-menu-item>
          <el-menu-item index="sign" :rout="{path:'/sign'}">会签合同</el-menu-item>
          <el-menu-item index="finalize" :rout="{path:'/finalize'}">定稿合同</el-menu-item>
          <el-menu-item index="check" :rout="{path:'/check'}">审批合同</el-menu-item>
          <el-menu-item index="qianding" :rout="{path:'/qianding'}">签订合同</el-menu-item>
        </el-submenu>
          <el-submenu index="2">
            <template #title>查询统计</template>
            <el-menu-item index="chaxun" :rout="{path:'/sign'}">合同信息查询</el-menu-item>
            <el-menu-item index="sign" :rout="{path:'/sign'}">合同流程查询</el-menu-item>
          </el-submenu>
          <el-submenu  index="3">
            <template #title>系统操作</template>
            <el-menu-item index="fenpei" :rout="{path:'/sign'}">分配合同</el-menu-item>
<!--            <el-menu-item index="sign" :rout="{path:'/sign'}">权限管理</el-menu-item>-->
            <el-submenu index="4">
              <template slot="title">权限管理</template>
              <el-menu-item index="newCustomer">新增用户</el-menu-item>
              <el-menu-item index="quanxian">分配权限</el-menu-item>
              <el-menu-item index="1-4-1">角色管理</el-menu-item>
            </el-submenu>
            <el-menu-item index="sign" :rout="{path:'/sign'}">日志管理</el-menu-item>
          </el-submenu>

        </el-menu>


  </div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      user:{},
    }
  },

  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
  },
}
</script>

<style scoped>

</style>