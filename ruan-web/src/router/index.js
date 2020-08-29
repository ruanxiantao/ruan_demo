import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import blogPublish from '../view/blog/blog_publish.vue';
import hello from '../view/blog/hello.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'blogPublish',
      component: blogPublish
    },
    {
      path: '/hello',
      name: 'hello',
      component: hello
    }
  ]
})
