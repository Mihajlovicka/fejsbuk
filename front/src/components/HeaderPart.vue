<template>
<header>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <router-link to="/homepage" class="navbar-brand" >Lazni fjesbuk</router-link>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-expanded="false">
      <span class="navbar-toggler-icon"></span>
    </button>



    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <div class="navbar-nav mr-auto">
        <ul class="navbar-nav">
          <li>
            <div class="input-group" style="margin-left: 50px !important;" v-if="!isAdmin">
              <input type="search" class="form-control rounded" v-model="search" placeholder="Pretraga" aria-label="Search" aria-describedby="search-addon" />
              <button type="button" class="btn btn-outline-primary" @click="userSearch">pretraga</button>
            </div>
          </li>
        </ul>
      </div>

      <div class="form-inline my-2 my-lg-0" v-if="!loogedin">
        <router-link to="/register"><a class="nav-link" href="#">Registracija</a></router-link>
        <router-link class="nav-link"  to="/login">Prijava</router-link>
      </div>
      <div class="form-inline my-2 my-lg-0" v-if="loogedin && !isAdmin">
        <router-link class="nav-link" :to="{name: 'friendRequestsView'}" @click="this.$parent.forceRerenderPage()">Zahtevi za prijateljstvo</router-link>
        <router-link class="nav-link" :to="{name: 'userProfile', params: {username:username}}" @click="this.$parent.forceRerenderPage()">Profil</router-link>
        <a class="nav-link" href="" @click="logout" >Odjava</a>

      </div>
      <div class="form-inline my-2 my-lg-0" v-if="loogedin && isAdmin">
        <router-link class="nav-link" :to="{name: 'usersViewAdmin'}" @click="this.$parent.forceRerenderPage()">Prikaz svih korisnika</router-link>
        <router-link class="nav-link" :to="{name: 'postsViewAdmin'}" @click="this.$parent.forceRerenderPage()">Prikaz svih objava i slika</router-link>
        <a class="nav-link" href="" @click="logout" >Odjava</a>

      </div>
    </div>
  </nav>
</header>
</template>

<script>

import axios from "axios";

export default {
  name: "HeaderPart",

  data(){
    return{
      loogedin:false,
      search:"",
      username:"",
      isAdmin:false,
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("token")
      localStorage.removeItem("role")
      this.$router.push('/login')
    },
    userSearch(){
      this.$parent.search(this.search)
    },
  },
  created(){
    this.loogedin = localStorage.getItem("token") !== null;
    this.isAdmin = localStorage.getItem("role") == "admin" ? true : false;
    axios.get('/getLoggedInUser').then(resp => {
      this.username = resp.data.username
    }).catch(() => {})
  }
}
</script>

<style scoped>
.datepickerclass{
  width: 30% !important;
  height: 38px ;
}
</style>