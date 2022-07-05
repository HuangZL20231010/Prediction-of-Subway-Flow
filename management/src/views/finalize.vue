<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        待定稿合同
      </p>
    </div>
    <div>
      <el-steps :active="2" finish-status="success" simple style="margin-top: 20px">
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
          width="200">
        <template slot-scope="scope">
          <el-button
              @click="finalize(scope.row)"
              type="text"
              style="color: #42b983"
              size="small">
            定稿
          </el-button>
          <el-button
              @click="showcontract(scope.row)"
              type="text"
              size="small">
            修改
          </el-button>

          <el-button v-if="scope.row.shyj!='' "
                     @click="showcheck(scope.row)"
              type="text"
              size="small">
            查看审核意见
          </el-button>


          <!--          会签弹窗-->
          <el-dialog title="定稿合同" :visible.sync="dialogFormVisible" style="width: 100%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >
                {{signdata.htname}}
              </el-form-item>
              <el-form-item label="会签意见" >
                <el-card class="box-card">
                  <div  v-model="signdata" class="text item">
                    {{ signdata.hqyj }}
                  </div>
                </el-card>
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="dinggao()">定 稿</el-button>
            </div>
          </el-dialog>

          <!--         审核意见弹窗-->
          <el-dialog title="定稿合同" :visible.sync="dialogcheck" style="width: 100%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >
                {{signdata.htname}}
              </el-form-item>
              <el-form-item label="审核意见" >
                <el-card class="box-card">
                  <div  v-model="signdata" class="text item">
                    {{ signdata.shyj }}
                  </div>
                </el-card>
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogcheck = false">关 闭</el-button>
            </div>
          </el-dialog>

          <!--          查看弹窗  还没写-->
          <el-dialog title="修改合同" :visible.sync="showVisible" style="width: 100%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >
                <el-input v-model="signdata.htname" style="width: 200px"></el-input>
              </el-form-item>
              <el-form-item label="客户名称" >
                <el-input v-model="signdata.khname" style="width: 200px"></el-input>
              </el-form-item>
              <el-form-item label="合同起止">
                <el-col :span="6">
                  <el-date-picker type="date" placeholder="选择开始日期" v-model="signdata.time1" style="width: 100%;"></el-date-picker>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="8">
                  <el-date-picker type="date" placeholder="选择结束日期" v-model="signdata.time2" style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
              <el-form-item label="合同内容" >
                <el-input type="textarea" :rows="15" v-model="signdata.content" style="width: 500px"></el-input>
              </el-form-item>

            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="save()">保 存</el-button>
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
  name: "finalize",
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
      total:1,
      dialogcheck:false,
      signdata:{},
      tableData:[{
       htname: "测试合同1",
        content:"你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好",
        shyj:"改！",
        hqyj:"不行",
        time1:"2022-06-05",
      },{
        htname: "测试合同2",
        content:"你好",
        shyj:"",
        time1:"2022-06-04",
      }]

    }
  },
  methods: {
    dinggao(){

      this.dialogFormVisible = false
      request.post("",this.signdata).then(res =>{
      })
    },

    showcheck(row){
      this.dialogcheck = true
      this.signdata=JSON.parse(JSON.stringify(row))
    },

    finalize(row) {
      // rows.splice(index, 1);
      this.dialogFormVisible = true
      this.signdata=JSON.parse(JSON.stringify(row))
    },
    save(){
      request.post("",this.signdata).then(res =>{
      })
      load()
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
    showcontract(name) {
      // rows.splice(index, 1);
      this.showVisible = true
      this.signdata = JSON.parse(JSON.stringify(name))
    }
  },

}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
}
</style>