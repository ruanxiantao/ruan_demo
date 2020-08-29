<template>
  <div>
    <div v-if="ext === 'mp4'">
      <video preload="auto" controls>
        <source :src="url" type="video/mp4">
      </video>
    </div>
    <div v-else-if="ext === 'mp3'">
      <audio :src="url"></audio>
    </div>
    <div v-else-if="ext === 'jpg' || ext === 'jpeg' || ext === 'png'">
      <img :src="url" width="100%">
    </div>
    <div v-else>
      <span>不支持的文件格式</span>
    </div>
    <el-button @click="goback">返回</el-button>
  </div>
</template>

<script>
  export default {
    name: "filedetail",
    data() {
      return {
        url: '',
        ext: ''
      }
    },
    created() {
      this.url = process.env.BASE_API + "image/" + this.$route.query.url;
      let index = this.url.lastIndexOf('.');
      let length = this.url.length;
      this.ext = this.url.substring(index + 1, length);
    },
    methods: {
      goback() {
        this.$router.go(-1);
      },
    }
  }
</script>

<style scoped>

</style>
