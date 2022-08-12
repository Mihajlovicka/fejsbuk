import Vue from 'vue'
import App from './App.vue'
import './axios.js'
import LogIn from "@/components/LogIn";
import VueRouter from "vue-router";
import HomePage from "@/components/HomePage";

Vue.config.productionTip = false
Vue.use(VueRouter);

const routes = [
  {path:'/login', component: LogIn},
  {path:'/homepage', component: HomePage}
]

const router = new VueRouter({
  mode: 'history',
  routes, // short for `routes: routes`
})

new Vue({
  render: h => h(App),
  router: router,
}).$mount('#app')
