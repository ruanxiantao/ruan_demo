import Vue from 'vue'
import Router from 'vue-router'
import naspc from '../view/nas/pc/nas.vue';
import filedetailpc from '../view/nas/pc/filedetail.vue';
import nasmobile from '../view/nas/mobile/nas.vue';
import filedetailmobile from '../view/nas/mobile/filedetail.vue';
import index from '../view/nas/index/index.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/naspc',
      name: 'naspc',
      component: naspc
    },
    {
      path: '/filedetailpc',
      name: 'filedetailpc',
      component: filedetailpc
    },
    {
      path: '/nasmobile',
      name: 'nasmobile',
      component: nasmobile
    },
    {
      path: '/filedetailmobile',
      name: 'filedetailmobile',
      component: filedetailmobile
    }
  ]
})
