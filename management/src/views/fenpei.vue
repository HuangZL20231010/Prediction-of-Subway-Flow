<template>
  <div style="padding: 10px">
    <div style="padding: 30px;background:#409EFF "  >
      <p class="sample">
        合同分配
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
              size="small">
            分配
          </el-button>

          <!--          会签弹窗-->
          <el-dialog title="流程配置" :visible.sync="dialogFormVisible" style="width: 100%">
            <el-form :model="signdata">
              <el-form-item label="合同名称" >
                {{signdata.htname}}
              </el-form-item>
              <el-form-item label="分配会签人"></el-form-item>
              <el-transfer v-model="value1" :data="userdata" :props="{key: 'id',label: 'name',}" :titles="['待选人', '会签人']"></el-transfer>
              <el-form-item label="分配审批人" ></el-form-item>
              <el-transfer v-model="value2" :data="userdata" :props="{key: 'id',label: 'name',}" :titles="['待选人', '审批人']"></el-transfer>
              <el-form-item label="分配签订人" ></el-form-item>
              <el-transfer v-model="value3" :data="userdata" :props="{key: 'id',label: 'name',}" :titles="['待选人', '签订人']"></el-transfer>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="fenpei()">分 配</el-button>
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
              <el-button @click="dialogFormVisible = false">关 闭</el-button>
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
  name: "fenpei",
  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
    this.load()
  },
  data(){

    return{
      search:'',
      total:0,
      dialogFormVisible :false,
      showVisible:false,
      signdata:{
        hq1:"",
        hq2:"",

      },
      userdata:[{
        name:"张三",
        id:1
      },{name:"李四"
      ,id:2}],

      tableData:[{
        htname: "测试合同1",
        time1:"2022-06-05",
        content:"这是毁灭软件学院得合同" +
            "这是毁灭软件学院得合同"
      },{
        htname: "测试合同2",
        time1:"2022-06-04",
      }],
      value1: [],
      value2: [],
      value3: [],
    }
  }, methods: {
    load(){
      console.log(this.search),
          //请求所有人员的接口
          request.get("").then(res =>{
            this.userdata=res.data.records

          })


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

    fenpei(){
        if(this.value1.length==0||this.value2.length==0||this.value3.length==0){
          this.$alert('请每个步骤至少选中一名人员。', '分配失败！', {
            type: 'warning',
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: '',
                message: `action: ${ action }`
              });
            }
          })
        }
        else{
          //会签
          if(this.value1.length==1){

            var data= this.userdata.filter((item) => item.id == this.value1[0]);
            console.log(data[0].name)
            this.signdata.hq1=data[0].name

          }if(this.value1.length==2){
            var data= this.userdata.filter((item) => item.id == this.value1[0]);
            console.log(data[0].name)
            this.signdata.hq1=data[0].name
            var data1= this.userdata.filter((item) => item.id == this.value1[1]);
            console.log(data1[0].name)
            this.signdata.hq2=data1[0].name
          }
          //审批
          if(this.value2.length==1){
            var data= this.userdata.filter((item) => item.id == this.value2[0]);
            console.log(data[0].name)
            this.signdata.sp1=data[0].name

          }if(this.value2.length==2){
            var data= this.userdata.filter((item) => item.id == this.value2[0]);
            console.log(data[0].name)
            this.signdata.sp1=data[0].name
            var data1= this.userdata.filter((item) => item.id == this.value2[1]);
            console.log(data1[0].name)
            this.signdata.sp2=data1[0].name
          }
          //签订
          if(this.value3.length==1){
            var data= this.userdata.filter((item) => item.id == this.value3[0]);
            console.log(data[0].name)
            this.signdata.qd1=data[0].name

          }if(this.value3.length==2){
            var data= this.userdata.filter((item) => item.id == this.value3[0]);
            console.log(data[0].name)
            this.signdata.qd1=data[0].name
            var data1= this.userdata.filter((item) => item.id == this.value3[1]);
            console.log(data1[0].name)
            this.signdata.qd2=data1[0].name
            console.log(this.signdata)
            //
            request.post("",this.signdata).then(res =>{
            })
          }}
    }
  },
}
</script>

<style scoped>

</style>