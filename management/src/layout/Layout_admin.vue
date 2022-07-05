<template>
  <div>
    <Header_a />
    <!--    主题-->
    <div style="display :flex">
      <!--      侧边栏-->
      <Aside_a />
      <!--      主体-->
      <router-view style="flex:1"/>

    </div></div>
</template>

<script>

import Header from "@/components/Header";
import Aside from "@/components/Aside";
import Header_a from "@/components/Header_a";
import Aside_a from "@/components/Aside_a";
export default {
  name: "Layout_a",
  components:{
    Header_a,
    Aside_a
  }

}
</script>

<style scoped>

</style>