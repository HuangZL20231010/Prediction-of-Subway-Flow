<template>
  <div class="wrapper">
    <div style="margin: 200px auto;background-color: #fff; width:350px;height:300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>注 册</b></div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" show-password v-model="user.password"></el-input>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="Register()">注册</el-button>
        <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data(){
    return{
      user:{}
    }
  },
  methods:{
    Register(){
      request.post("/api/",this.user).then(res =>{
      })
    }
  }
}
</script>

<style>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC4668,#3F5EF8);
    overflow: hidden;
  }

</style>