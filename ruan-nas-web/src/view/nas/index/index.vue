<template>
    <div>
      <el-button @click="goMobile">手机版</el-button>
      <el-button @click="goPc">电脑版</el-button>
    </div>
</template>

<script>
    export default {
        name: "index",
      methods:{
        goMobile() {
          this.$router.push({
            path: 'nasmobile'
          })
        },
        goPc() {
          this.$router.push({
            path: 'naspc'
          })
        }
      }
    }
</script>

<style scoped>

</style>
