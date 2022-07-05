<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        待审批合同
      </p>
    </div>
    <div>
      <el-steps :active="3" finish-status="success" simple style="margin-top: 20px">
        <el-step title="起草" ></el-step>
        <el-step title="会签" ></el-step>
        <el-step title="定稿" ></el-step>
        <el-step title="审批" ></el-step>
        <el-step title="签订" ></el-step>
      </el-steps>
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
          prop="htname"
          label="合同名称"
          width="600"
      >
      </el-table-column>
      <el-table-column
          prop="time1"
          label="起草时间"
          width="120">
      </el-table-column>
      <el-table-column
          label="操作"
          width="120">
        <template slot-scope="scope">
          <el-button
              @click="sign_contract(scope.row)"
              type="text"
              style="color: #42b983"
              size="small">
            审批
          </el-button>
          <el-button
              @click="showcontract(scope.row)"
              type="text"
              size="small">
            查看
          </el-button>

          <!--          审批弹窗-->
          <el-dialog title="审批合同" :visible.sync="dialogFormVisible" style="width: 70%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >
                {{signdata.htname}}
              </el-form-item>
              <el-form-item label="审批意见" >
                <el-input type="textarea" :rows="7"  v-model="signdata.spyj"style="width: 50%;height: 80%"></el-input>
              </el-form-item>
            </el-form>
            <div>
              <el-radio v-model="signdata.spzt" label="1">通过</el-radio>
              <el-radio v-model="signdata.spzt" label="0">不通过</el-radio>
            </div>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="shenpi()">审 批</el-button>
            </div>
          </el-dialog>
          <!--          查看弹窗  还没写-->
          <el-dialog title="查看合同" :visible.sync="showVisible" style="width: 70%">
            <el-form :model="tableData">
              <el-form-item label="合同名称" >
                {{signdata.htname}}
              </el-form-item>
              <el-form-item label="合同内容" >
                <el-card class="box-card">
                  <div  v-model="signdata" class="text item">
                    {{ signdata.content }}
                  </div>
                </el-card>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="showVisible = false">关 闭</el-button>
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
  name: "check",
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
    this.load()
  },
  data(){
    return{
      spzt:"0",
      search:'',
      total:1,
      dialogFormVisible :false,
      showVisible:false,
      signdata:{},
      tableData:[{
        htname: "测试合同1",
        time1:"2022-06-05",
        content:"这是毁灭软件学院得合同" +
            "这是毁灭软件学院得合同"
      },{
        htname: "测试合同2",
        time1:"2022-06-04",
      }]

    }
  },
  methods: {
    shenpi(){
      this.dialogFormVisible = false
      console.log(this.signdata)
      request.post("",this.signdata).then(res =>{
      })
    },
    load(){
      console.log(this.search),
          request.get("",{
            params:{ pageNum:1,
              PageSize: 10,
              search:this.search,
              user:this.user.name,
            }
          }).then(res =>{
            console.log(res)
            console.log("request")
            this.tableData = res.data.records
            this.total=res.data.total
          })
    },

    sign_contract(row) {
      // rows.splice(index, 1);
      this.dialogFormVisible = true
      this.signdata=JSON.parse(JSON.stringify(row))
    },
    showcontract(row){
      // rows.splice(index, 1);
      this.showVisible = true
      this.signdata=JSON.parse(JSON.stringify(row))
    }
  },

}
</script>

<style scoped>

</style>