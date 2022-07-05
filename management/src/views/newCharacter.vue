<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        创建新角色
      </p>
    </div>
    <el-form ref="form" :model="form" label-width="80px" style="margin: 20px;font-weight: bold">
      <el-form-item label="角色名" >
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

      <el-dialog title="流程配置"  style="width: 100%">
        <el-form :model="signdata">
          <el-form-item label="分配权限人"></el-form-item>
          <el-transfer v-model="value1" :data="datas_qianxian" :titles="['未选权限', '已选权限']"></el-transfer>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">分 配</el-button>
        </div>
      </el-dialog>


      <el-form-item>
        <el-button type="primary" @click="dialogFormVisible=true">新 建！</el-button>
        <el-button>取 消</el-button>
      </el-form-item>
    </el-form>
  </div>

          <!--          会签弹窗-->



</template>

<script>
export default {
  name: "fenpei",
  data(){
    return{
      search:'',
      dialogFormVisible :false,
      showVisible:false,
      signdata:{},
      tableData:[{
        contract_name: "测试合同1",
        time:"2022-06-05",
      },{
        contract_name: "测试合同2",
        time:"2022-06-04",
      }],
      datas_quanxian: [{key:1,label:"起草合同"},{key:2,label:"会签合同"},{key:3,label:"定稿合同"},{key:4,label:"审批合同"},{key:5,label:"签订合同"},
        {key:6,label:"分配合同"},{key:7,label:"日志查询"},{key:8,label:"新增用户"},{key:9,label:"分配权限"},],
      value1: [1],

    }
  }, methods: {
    sign_contract(index, rows) {
      // rows.splice(index, 1);
      this.dialogFormVisible = true
    },
    showcontract(index, rows) {
      // rows.splice(index, 1);
      this.showVisible = true
    }
  },
}
</script>

<style scoped>

</style>