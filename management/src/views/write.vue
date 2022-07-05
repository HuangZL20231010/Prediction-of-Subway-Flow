<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        起草合同
      </p>
    </div>
    <div>
      <el-amap ref="map" vid="amapDemo" :center="center" :zoom="zoom" :events="events" class="amap-demo">
      </el-amap>
    </div>
    <div>
      <el-steps :active="0" finish-status="success" simple style="margin-top: 20px">
        <el-step title="起草" ></el-step>
        <el-step title="会签" ></el-step>
        <el-step title="定稿" ></el-step>
        <el-step title="审批" ></el-step>
        <el-step title="签订" ></el-step>
      </el-steps>
    </div>

    <el-form ref="form" :model="form" label-width="80px" style="margin: 20px;font-weight: bold">
      <el-form-item label="合同名称" >
        <el-input v-model="form.htname" style="width: 400px"></el-input>
      </el-form-item>
      <el-form-item label="客户名称">
        <el-input v-model="form.khname" style="width: 200px">
        </el-input>
      </el-form-item>
      <el-form-item label="合同起止">
        <el-col :span="6">
          <el-date-picker type="date" placeholder="选择开始日期" v-model="form.time1" style="width: 50%;"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="8">
          <el-date-picker type="date" placeholder="选择结束日期" v-model="form.time2" style="width: 50%;"></el-date-picker>
        </el-col>
      </el-form-item>

      <el-form-item label="合同内容">
        <el-input type="textarea" :rows="15"  v-model="form.content"style="width: 50%;height: 80%"></el-input>
      </el-form-item>
      <el-form-item label="上传附件">
        <el-upload
            class="upload-demo"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即起草</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>


  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      function() {
        return {
          zoom: 12,
          center: [121.59996, 31.197646],
          events: {
            'click': (e) => {
              alert('map clicked');
            }
          },
        };
      },
      form: {
        where:1,
        hq1:"",
        hq2:"",
        dg1:"",
        dg2:"",
        sp1:"",
        sp2:"",
        qd1:"",
        qd2:"",
      },
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!');
      if(this.form.time1>this.form.time2){
        this.$alert('起止时间错误。', '起草失败！', {
          type: 'warning',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: '',
              message: `action: ${ action }`
            });
          }
        });
      }else if(!this.form.htname){
        this.$alert('合同名称不能为空。', '起草失败！', {
          type: 'warning',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: '',
              message: `action: ${ action }`
            });
          }
        });
      }else if(!this.form.content){
        this.$alert('合同内容不能为空。', '起草失败！', {
          type: 'warning',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: '',
              message: `action: ${ action }`
            });
          }
        });
      }
      else if(!this.form.khname){
        this.$alert('客户名称不能为空。', '起草失败！', {
          type: 'warning',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: '',
              message: `action: ${ action }`
            });
          }
        });
      }else{

        //弹窗
        this.$alert('已成功起草', '上传成功！', {
          type: 'success',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'success',
              message: `action: ${ action }`
            });
          }
        });
        //上传
        request.post("/api/user",this.form).then(res =>{
        })
      }
    }
  }
}
</script>

}
<style scoped>
</style>