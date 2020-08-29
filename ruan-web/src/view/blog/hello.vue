<template>
  <el-button @click="hello">hello</el-button>
</template>
<script>
  export default {
    data() {
      return {}
    },
    methods: {
      hello(){
        alert(navigator.userAgent)
      }
    }
  }

</script>
