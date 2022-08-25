import Vue from 'vue'
import App from './App.vue'
import './axios.js'
import LogIn from "@/components/LogIn";
import VueRouter from "vue-router";
import HomePage from "@/components/HomePage";
import UserSearchResult from "@/components/UserSearchResult";
import './filters.js'
import UserProfile from "@/components/UserProfile";
import ProfileInfoChange from "@/components/ProfileInfoChange";
import PasswordChange from "@/components/PasswordChange";

Vue.config.productionTip = false
Vue.use(VueRouter);

const routes = [
  {path:'/login', component: LogIn},
  {path:'/homepage', component: HomePage},
  {path:'/userSearch', component: UserSearchResult, name:'userSearch', props:true},
  {path:'/userProfile/:username', component: UserProfile, name:'userProfile', props:true},
  {path:'/personalInfo', component: ProfileInfoChange, name:'personalInfo', props:true},
  {path:'/passwordChange/:username', component: PasswordChange, name:'passwordChange', props:true},

]

const router = new VueRouter({
  mode: 'history',
  routes, // short for `routes: routes`
})

new Vue({
  render: h => h(App),
  router: router,
}).$mount('#app')
