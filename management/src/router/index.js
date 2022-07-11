import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Home.vue'
import Layout from "../layout/Layout.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/login",
    children: [
      {
        path:'/home',
        name:'Home',
        component:()=>import("../views/Home.vue")
      },
      {
        path: '/history',
        name: '/history',
        component: () => import("../views/history.vue")
      },
      {
        path: '/ZL',
        name: '/ZL',
        component: () => import("../views/ZL.vue")
      },{
        path: '/overview',
        name: '/overview',
        component: () => import("../views/overview.vue")
      },

    ]
  }
  ]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
