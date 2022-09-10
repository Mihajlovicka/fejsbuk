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
import NewProfilePic from "@/components/NewProfilePic";
import NewPost from "@/components/NewPost";

import RegistrationForm from "@/components/RegistrationForm";
import FriendRequestsView from "@/components/FriendRequestsView";
import FriendsView from "@/components/FriendsView";
import UsersViewAdmin from "@/components/UsersViewAdmin";
import UserPosts from "@/components/UserPosts";
import DirectMessages from "@/components/DirectMessages";


Vue.config.productionTip = false
Vue.use(VueRouter);

const routes = [
  {path:'/login', component: LogIn},
  {path:'/homepage', component: HomePage},
  {path:'/directMessages', name:'directMessages', component: DirectMessages},

  {path:'/userSearch', component: UserSearchResult, name:'userSearch', props:true},
  {path:'/userProfile/:username', component: UserProfile, name:'userProfile', props:true},
  {path:'/personalInfo', component: ProfileInfoChange, name:'personalInfo', props:true},
  {path:'/passwordChange/:username', component: PasswordChange, name:'passwordChange', props:true},
  {path:'/newProfilePic/:username', component: NewProfilePic, name:'newProfilePic', props:true},
  {path:'/newPost/:username', component: NewPost, name:'newPost', props:true},
  {path:'/friendRequestsView', component: FriendRequestsView, name:'friendRequestsView', props:true},
  {path:'/friendsView', component: FriendsView, name:'friendRequestsView', props:true},
  {path:'/adminUserView', component: UsersViewAdmin, name:'usersViewAdmin', props:true},
  {path:'/adminPostsView', component: UserPosts, name:'postsViewAdmin', props:true},

  {path:'/register', component: RegistrationForm, name:'register', props:true}

]

const router = new VueRouter({
  mode: 'history',
  routes, // short for `routes: routes`
})

new Vue({
  render: h => h(App),
  router: router,
}).$mount('#app')
