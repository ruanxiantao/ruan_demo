// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css'
import { scorllbar } from "./utils/scorllbar.js"; //滚动条
Vue.prototype.escrollY = scorllbar     //滚动条

import 'jquery'

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(Mint);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
