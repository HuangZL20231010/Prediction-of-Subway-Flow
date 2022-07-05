import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Home.vue'
import Layout from "../layout/Layout.vue";
import Layout_admin from "@/layout/Layout_admin";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/login",
    children:[
      {
        path:'/home',
        name:'Home',
        component:()=>import("../views/Home.vue")
      },
      {
        path:'write',
        name:'write',
        component:()=>import("../views/write.vue")
      },
      {
        path:'sign',
        name:'sign',
        component:()=>import("../views/Sign.vue")
      },{
      path:'finalize',
      name:'finalize',
      component:()=>import("../views/finalize.vue")
},{
        path:'check',
        name:'check',
        component:()=>import("../views/check.vue")
      },{
        path:'qianding',
        name:'qianding',
        component:()=>import("../views/qianding.vue")
      },{
        path:'fenpei',
        name:'fenpei',
        component:()=>import("../views/fenpei.vue")
      },{
        path:'quanxian',
        name:'quanxian',
        component:()=>import("../views/quanxian.vue")
      },{
        path:'newCustomer',
        name:'newCustomer',
        component:()=>import("../views/newCustomer.vue")
      },{
        path:'chaxun',
        name:'chaxun',
        component:()=>import("../views/chaxun.vue")
      },
    ]
  },{
    path:'/Login',
    name:'Login',
    component: ()=>import('../views/Login1')
  },
  {
    path:'/Register',
    name:'Register',
    component: ()=>import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
