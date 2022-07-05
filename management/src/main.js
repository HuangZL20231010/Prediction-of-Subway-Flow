import Vue from 'vue'

import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "../src/assets/global.css"
import VueAMap from 'vue-amap';
Vue.use(VueAMap);

VueAMap.initAMapApiLoader({
  //申请地址 https://lbs.amap.com/ 选择web端jsAPI
  key: '905b4f99bdf009b2c1607f940ec805e5',
  // 插件集合，用到什么插件就使用什么插件
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor','AMap.LineSearch','AMap.Polyline'],
  v: '2.0', // 高德sdk版本，最新的sdk已经出到了2.0
  //官网解释：JSAPI 2.0 提供的开发接口与 1.4 版本达到 99%的兼容度，但是为了保证插件的稳定性我们还是选择1.4.4。
})
const amapKeys = Object.keys(localStorage).filter(key => key.match(/^_AMap_/))
amapKeys.forEach(key => {
  // console.log(key)
  localStorage.removeItem(key)
})
Vue.config.productionTip = false
Vue.use(ElementUI,{size:"small"});
import axios from 'axios'
Vue.prototype.$axios = axios
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
