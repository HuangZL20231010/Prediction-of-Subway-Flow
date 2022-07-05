<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        待会签合同
      </p>
    </div>
    <div>
      <el-steps :active="1" finish-status="success" simple style="margin-top: 20px">
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
      <el-button type="primary" @click="load()">查询</el-button>
    </div>
    <div style="width: 100%">
      <el-table
          :data="tableData"
          style="width: 100%;max-height: 6000px"
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
                size="small"
                style="color: #42b983">
              会签
            </el-button>
            <el-button
                @click="showcontract(scope.row)"
                type="text"
                size="small">
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
<!--    表单显示区-->

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

    <!--          会签弹窗-->
    <el-dialog title="会签合同" :visible.sync="dialogFormVisible" style="width: 100%">
      <el-form :model="signdata">
        <el-form-item label="合同名称" >
            {{signdata.htname}}
        </el-form-item>
        <el-form-item label="会签意见" >
          <el-input type="textarea" :rows="7"  v-model="signdata.hqyj"style="width: 50%;height: 80%"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="huiqian()">会 签</el-button>
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



  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Sign",
  data(){
    return{
      user:{},
      total:0,
      pageNum:1,
      PageSize:10,
      search:'',
      dialogFormVisible :false,
      showVisible:false,
      signdata:{
      },
      tableData:[{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },{
        htname: "测试合同2",
        time1:"2022-06-04",
      },{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },{
        htname: "测试合同1",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
      },]

    }
  },
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
    this.load()
  },
  methods: {
    huiqian(){
      if(!this.signdata.hqyj){
        this.$alert('会签意见不能为空。', '起草失败！', {
          type: 'warning',
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: '',
              message: `action: ${ action }`
            });
          }
        })
      }else{//上传
        console.log(this.signdata)
        request.post("/api/user",this.signdata).then(res =>{
        })
      }
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

    sign_contract(name) {
      // rows.splice(index, 1);
      this.dialogFormVisible = true
      this.signdata=JSON.parse(JSON.stringify(name))
    },

    showcontract(name) {
      // rows.splice(index, 1);
      this.showVisible=true;
      this.signdata=JSON.parse(JSON.stringify(name))
    }
  },

}
</script>

<style scoped>

</style>