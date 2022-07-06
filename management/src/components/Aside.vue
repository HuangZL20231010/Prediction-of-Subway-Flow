<template>
  <div>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      user:{},
    }
  },

  created(){
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user=JSON.parse(userStr)
  },
}
</script>

<style scoped>

</style>