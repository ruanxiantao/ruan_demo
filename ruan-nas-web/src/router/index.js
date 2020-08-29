import Vue from 'vue'
import Router from 'vue-router'
import nas from '../view/nas/pc/nas.vue';
import filedetail from '../view/nas/pc/filedetail.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'nas',
      component: nas
    },
    {
      path: '/filedetail',
      name: 'filedetail',
      component: filedetail
    }
  ]
})
