<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        创建新用户
      </p>
    </div>
    <el-form ref="form" :model="form" label-width="80px" style="margin: 20px;font-weight: bold">
      <el-form-item label="用户名" >
        <el-input v-model="form.user_name" style="width: 400px"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.user_code" style="width: 200px">
        </el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.user_recode" style="width: 200px">
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">新 建！</el-button>
        <el-button>取 消</el-button>
      </el-form-item>
    </el-form>



  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        user_name: '',
        user_code: '',
        user_recode: '',
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!');
    }
  }
}
</script>

}
<style scoped>
</style>