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
            <div class="input-group" style="margin-left: 50px !important;">
              <input type="search" class="form-control rounded" v-model="search" placeholder="Pretraga" aria-label="Search" aria-describedby="search-addon" />
              <button type="button" class="btn btn-outline-primary" @click="userSearch">pretraga</button>
            </div>
          </li>
        </ul>
      </div>
      <div class="form-inline my-2 my-lg-0" v-if="!loogedin">
        <a class="nav-link" href="#">Registracija</a>
        <router-link class="nav-link"  to="/login">Prijava</router-link>
      </div>
      <div class="form-inline my-2 my-lg-0" v-if="loogedin">
        <button class="nav-link" @click="logout" >Odjava</button>
      </div>
    </div>
  </nav>
</header>
</template>

<script>

export default {
  name: "HeaderPart",

  data(){
    return{
      loogedin:false,
      search:"",
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("token")
      this.$parent.forceRerender()
      this.$router.push('/')
    },
    userSearch(){
      this.$parent.search(this.search)
    }
  },
  created(){
    this.loogedin = localStorage.getItem("token") !== null
  }
}
</script>

<style scoped>
.datepickerclass{
  width: 30% !important;
  height: 38px ;
}
</style>