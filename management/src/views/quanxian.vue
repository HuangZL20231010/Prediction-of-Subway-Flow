<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        用户权限列表
      </p>
    </div>
    <!--      搜索区域-->
    <div style="padding: 10px">
      <el-input v-model="search" style="width: 200px ;padding-right: 15px" placeholder="请输入关键字！"></el-input>
      <el-button type="primary">查询</el-button>
    </div>
    <!--    表单显示区-->
    <el-table
        :data="tableData"
        style="width: 100%"
        max-height="250">
      <el-table-column
          fixed
          prop="name"
          label="用户名称"
          width="600"
      >
      </el-table-column>
      <el-table-column
          label="角色名称"
          width="120">
        <template #default="scope">
          <span v-if="scope.row.role == 'a'">管理员</span>
          <span v-if="scope.row.role == 'b'">操作员</span>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          width="120">
        <template slot-scope="scope">
          <el-button
              @click="sign_contract(scope.row)"
              type="text"
              size="small">
            授权
          </el-button>

          <!--          会签弹窗-->
          <el-dialog title="" :visible.sync="dialogFormVisible" style="width: 100%" append-to-body=“true”>
            <el-form :model="signdata">
              <el-form-item label="分配角色"   ></el-form-item>
              <el-transfer v-model="value1" :data="datas_character" :titles="['可选择角色', '目前角色']" style="width: auto"></el-transfer>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="shouquan()">授 权</el-button>
            </div>
          </el-dialog>
          <!--          查看弹窗  还没写-->
          <el-dialog title="查看合同" :visible.sync="showVisible" style="width: 70%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >

              </el-form-item>
              <el-form-item label="合同内容" >
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="shouquan()">关 闭</el-button>
            </div>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>

    <!--    翻页-->
    <div class="block">
      <span class="demonstration">显示总数</span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage1"
          :page-size="100"
          layout="total, prev, pager, next"
          :total=total>
      </el-pagination>
    </div>


  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "quanxian",
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
    this.load()
  },
  data(){
    return{
      search:'',
      dialogFormVisible :false,
      showVisible:false,
      signdata:{},
      tableData:[{
        user_name: "张三",
        character:"操作员",
      },{
        user_name: "李四",
        character:"管理员",
      }],
      datas_character: [{key:1,label:"管理员"},{key:2,label:"操作员"}],
      value1: [],
    }
  }, methods: {
    load(){
      console.log(this.search),
          //请求所有人员的接口
          request.get("/api/user/list").then(res =>{
            this.tableData = res.data.records
            this.total=res.data.total
          })
    },
    shouquan(){

      if(this.value1.length==1) {
        if (value1[0] == 1) {
          this.signdata.role = 'a'
        }
        if (value1[0] == 2) {
          this.signdata.role = 'b'
        }
      }else{
        this.signdata.role = 'a'
      }
      this.dialogFormVisible=false
      request.put("/api/user/shouquan",this.signdata).then(res =>{
        console.log(res)
        this.$message({
          type:"success",
          message:"授权成功"
        })
      })

    },
    sign_contract(row) {
      this.signdata=JSON.parse(JSON.stringify(row))
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