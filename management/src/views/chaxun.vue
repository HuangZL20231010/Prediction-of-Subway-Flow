<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        合同总览
      </p>
    </div>
    <!--      搜索区域-->
    <div style="padding: 10px">
      <el-input v-model="search" style="width: 200px ;padding-right: 15px" placeholder="请输入关键字！"></el-input>
      <el-button type="primary" @click="load()">查询</el-button>
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
          width="300"
      >
      </el-table-column>
      <el-table-column
          prop="khname"
          label="客户名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="time1"
          label="起草时间"
          width="120">
      </el-table-column>
      <el-table-column
          prop="time2"
          label="结束时间"
          width="120">
      </el-table-column>
      <el-table-column
          label="合同状态"
          width="120">
          <template #default="scope">
            <span v-if="scope.row.jieduan === 0">待分配</span>
            <span v-if="scope.row.jieduan === 1">待会签</span>
            <span v-if="scope.row.jieduan === 2">待定稿</span>
            <span v-if="scope.row.jieduan === 3">待审核</span>
            <span v-if="scope.row.jieduan === 4">待签订</span>
            <span v-if="scope.row.jieduan === 5">已完成</span>
          </template>
      </el-table-column>

      <el-table-column
          label="角色名称"
          width="120">
        <template #default="scope">
          <span v-if="scope.row.jieduan == 'a' ">管理员</span>
          <span v-if="scope.row.jieduan ==='b'">操作员</span>
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
            查看
          </el-button>
          <el-button
              @click="showcontract(scope.row)"
              type="text"
              size="small">
            修改
          </el-button>
          <el-button
              @click="del(scope.row)"
              type="text"
              size="small">
            删除
          </el-button>
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

    <!--          会签弹窗-->
    <el-dialog title="会签合同" :visible.sync="dialogFormVisible" style="width: 100%">
      <el-form :model="signdata">
        <el-form-item label="合同名称" >
          {{signdata.htname}}
        </el-form-item>
        <el-form-item label="客户名称" >
          {{signdata.khname}}
        </el-form-item>
        <el-form-item label="起草时间" >
          {{signdata.time1}}
        </el-form-item>
        <el-form-item label="结束时间" >
          {{signdata.time2}}
        </el-form-item>
        <el-form-item v-if="signdata.hqyj" label="会签意见" >
          {{signdata.hqyj}}
        </el-form-item>
        <el-form-item v-if="signdata.spyj" label="审核意见" >
          {{signdata.spyj}}
        </el-form-item>
        <el-form-item v-if="signdata.qdyj" label="签订信息" >
          {{signdata.qdyj}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
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



  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "chaxun",
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
        khname:"我是谁？",
        content:'LormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLormLorm',
        time1:"2022-06-05",
        time2:"2022-06-30",
        qdyj:"还行吧!",
        hqyj:"让你过你就过",
        where:0,
        spyj:'',

      },{
        htname: "测试合同2",
        time1:"2022-06-04",
      }]

    }
  },
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
    this.load()
  },
  methods: {
    del(row){


},
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