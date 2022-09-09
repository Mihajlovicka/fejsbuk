<template>
  <div class="event-schedule-area-two bg-color pad100">
    <div class="container">
      <div class="row">
        <div class="col-4">
          <div class="d-flex justify-content-start flex-column">
            <div class="row">
              <div class="col-12" style="text-align: left">
                <h4>Dodatne opcije pretrage</h4>
              </div>
              <div class="col-12">
                <div class="input-group">
                  <div class="row">
                    <input type="search" class="form-control rounded" v-model="user_name" value="" placeholder="ime" aria-label="Search" aria-describedby="search-addon" />
                  </div>
                  <div class="row">
                    <input type="search" class="form-control rounded" v-model="user_surname" value="" placeholder="prezime" aria-label="Search" aria-describedby="search-addon" />
                  </div>
                  <div class="row">
                    <input type="search" class="form-control rounded" v-model="user_email" value="" placeholder="email" aria-label="Search" aria-describedby="search-addon" />
                  </div>

                </div>
                <div class="row" style="justify-content: center;">
                  <br><button type="button" class="btn btn-outline-primary" @click="userSearch">pretraga</button>
                </div>
              </div>
            </div>

          </div>
        </div>
        <div class="col-8">

          <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade active show" id="home" role="tabpanel">
              <div class="table-responsive">
                <table class="table">
                  <thead>
                  <tr>
                    <!--                    <th class="text-center" scope="col">Date</th>-->
                    <!--                    <th scope="col">Speakers</th>-->
                    <!--                    <th scope="col">Session</th>-->
                    <!--                    <th scope="col">Venue</th>-->
                    <!--                    <th class="text-center" scope="col">Venue</th>-->
                  </tr>
                  </thead>
                  <tbody>
                  <tr class="inner-box" v-for="user in filteredOffers" :key="user.username">
                    <td>
                      <div class="event-img">
                        <img :src="images[user.username]" alt=""/>
                      </div>
                    </td>
                    <td>
                      <div class="event-wrap">
                        <h3><router-link :to="{name: 'userProfile', params: {username:user.username}}">{{ user.name }} {{ user.surname }}</router-link></h3>
                        <div class="meta">

                          <div>
                            <span>{{ user.birthDate | dateViewFilter}}</span>
                          </div>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div class="primary-btn" v-if="!user.blocked">
                        <button class="btn btn-icon btn-outline-danger" style="width:100%; margin-top:5px;" @click="blockUser(user); user.blocked = true;">
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-x-fill" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                          </svg>
                          Blokiraj korisnika
                        </button>
                      </div>
                      <div class="primary-btn" v-if="user.blocked">
                        <button class="btn btn-icon btn-outline-primary" style="width:100%; margin-top:5px;" @click="unblockUser(user); user.blocked = false;">
                          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-check-fill" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                            <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                          </svg>
                          Odblokiraj korisnika
                        </button>
                      </div>


                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <!-- /col end-->
      </div>
      <!-- /row end-->
    </div>
  </div>
</template>

<script>
import axios from "axios";
import 'vue2-datepicker/index.css';


export default {
  name: "UserSearchResult",

  props:['headerSearch'],
  data() {
    return {
      users: [],
      loggedIn:false,
      user_name:"",
      user_surname: "",
      user_email:"",
      selectedDate:null,
      images:[],
      selectedFilter:'',

    }
  },
  methods:{

    blockUser(user){
      axios.post('/blockUser', user).then(resp => {
        alert("Uspesno blokiran korisnik. Cestitke zivot je sada lepsi, jer toksicni korisnici vam ne trebaju u sistemu! 🤗")
        console.log(resp.data);


      }).catch(resp => {
        alert(resp.data.error)
      })

    },
    unblockUser(user){
      axios.post('/unblockUser', user).then(resp => {
        alert("Uspesno odblokiran korisnik. Cestitke zivot je sada lepsi, jer ljudi se mogu popraviti! 🤗")
        console.log(resp.data);


      }).catch(resp => {
        alert(resp.data.error)
      })

    },

    userSearch(){
      // const offset = this.selectedDate[0].getTimezoneOffset()
      // this.selectedDate[0] = new Date(this.selectedDate[0].getTime() - (offset*60*1000))
      // console.log(this.selectedDate[0].toISOString().split('T')[0])


      axios.get('/adminSearchUsers',{params: {name:this.user_name === "" ? "" : this.user_name, surname:this.user_surname === "" ? "" : this.user_surname, email:this.user_email === "" ? "" : this.user_email}}).then(resp => {
        this.users = resp.data;
        this.getPictures();
      })
    },
    getPictures(){
      let imgs = {}
      for(let k of this.users){
        imgs[k.username] = k.profilePicture;
      }
      var pom = require.context(
          "../assets/pictures/",
          true,
          /^.*\.(jpe?g|png|gif)$/
      ).keys();
      for (let image of pom){
        let img_name = image.replace('./','')
        if(imgs[img_name.split('/')[0]] == img_name.split('/')[1])
          this.images[img_name.split('/')[0]] = require("../assets/pictures/"+ img_name);
      }
    }
  },
  created() {
    this.loggedIn = localStorage.getItem("token") !== null
    this.search = this.headerSearch
    axios.get('/searchUsers',{params: {start:"", end:"", search:this.headerSearch === "" ? "" : this.headerSearch}}).then(resp => {
      this.users = resp.data;
      this.getPictures();
    })
  },
  computed: {
    filteredOffers() {
      let filtered_all = this.users;

      return filtered_all;
    }
  }
}
</script>

<style scoped>
.input-group{
  padding: 10px;
  float: left;
  justify-content: center;
}
.input-group input{
  margin: 10px 30px 10px 30px;
  display: block;
}

.input-group button{
  margin-right: 20px;
}

.input-group .datepickerclass{
  margin: 10px 30px 10px 0px;
}
.btn-group{
  margin: 10px 5px;
}

.container {
  margin-top: 5%;
  width: 90% !important;
}

body {
  margin-top: 20px;
}

.event-schedule-area .section-title .title-text {
  margin-bottom: 50px;
}

.event-schedule-area .tab-area .nav-tabs {
  border-bottom: inherit;
}

.event-schedule-area .tab-area .nav {
  border-bottom: inherit;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  margin-top: 80px;
}

.event-schedule-area .tab-area .nav-item {
  margin-bottom: 75px;
}

.event-schedule-area .tab-area .nav-item .nav-link {
  text-align: center;
  font-size: 22px;
  color: #333;
  font-weight: 600;
  border-radius: inherit;
  border: inherit;
  padding: 0px;
  text-transform: capitalize !important;
}

.event-schedule-area .tab-area .nav-item .nav-link.active {
  color: #4125dd;
  background-color: transparent;
}

.event-schedule-area .tab-area .tab-content .table {
  margin-bottom: 0;
  width: 80%;
}

.event-schedule-area .tab-area .tab-content .table thead td,
.event-schedule-area .tab-area .tab-content .table thead th {
  border-bottom-width: 1px;
  font-size: 20px;
  font-weight: 600;
  color: #252525;
}

.event-schedule-area .tab-area .tab-content .table td,
.event-schedule-area .tab-area .tab-content .table th {
  border: 1px solid #b7b7b7;
  padding-left: 30px;
}

.event-schedule-area .tab-area .tab-content .table tbody th .heading,
.event-schedule-area .tab-area .tab-content .table tbody td .heading {
  font-size: 16px;
  text-transform: capitalize;
  margin-bottom: 16px;
  font-weight: 500;
  color: #252525;
  margin-bottom: 6px;
}

.event-schedule-area .tab-area .tab-content .table tbody th span,
.event-schedule-area .tab-area .tab-content .table tbody td span {
  color: #4125dd;
  font-size: 18px;
  text-transform: uppercase;
  margin-bottom: 6px;
  display: block;
}

.event-schedule-area .tab-area .tab-content .table tbody th span.date,
.event-schedule-area .tab-area .tab-content .table tbody td span.date {
  color: #656565;
  font-size: 14px;
  font-weight: 500;
  margin-top: 15px;
}

.event-schedule-area .tab-area .tab-content .table tbody th p {
  font-size: 14px;
  margin: 0;
  font-weight: normal;
}

.event-schedule-area-two .section-title .title-text h2 {
  margin: 0px 0 15px;
}

.event-schedule-area-two ul.custom-tab {
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  border-bottom: 1px solid #dee2e6;
  margin-bottom: 30px;
}

.event-schedule-area-two ul.custom-tab li {
  margin-right: 70px;
  position: relative;
}

.event-schedule-area-two ul.custom-tab li a {
  color: #252525;
  font-size: 25px;
  line-height: 25px;
  font-weight: 600;
  text-transform: capitalize;
  padding: 35px 0;
  position: relative;
}

.event-schedule-area-two ul.custom-tab li a:hover:before {
  width: 100%;
}

.event-schedule-area-two ul.custom-tab li a:before {
  position: absolute;
  left: 0;
  bottom: 0;
  content: "";
  background: #4125dd;
  width: 0;
  height: 2px;
  -webkit-transition: all 0.4s;
  -o-transition: all 0.4s;
  transition: all 0.4s;
}

.event-schedule-area-two ul.custom-tab li a.active {
  color: #4125dd;
}

.event-schedule-area-two .primary-btn {
  margin-top: 40px;
}

.event-schedule-area-two .tab-content .table {
  -webkit-box-shadow: 0 1px 30px rgba(0, 0, 0, 0.1);
  box-shadow: 0 1px 30px rgba(0, 0, 0, 0.1);
  margin-bottom: 0;
}

.event-schedule-area-two .tab-content .table thead {
  background-color: #007bff;
  color: #fff;
  font-size: 20px;
}

.event-schedule-area-two .tab-content .table thead tr th {
  padding: 20px;
  border: 0;
}

.event-schedule-area-two .tab-content .table tbody {
  background: #fff;
}

.event-schedule-area-two .tab-content .table tbody tr.inner-box {
  border-bottom: 1px solid #dee2e6;
}

.event-schedule-area-two .tab-content .table tbody tr th {
  border: 0;
  padding: 30px 20px;
  vertical-align: middle;
}

.event-schedule-area-two .tab-content .table tbody tr th .event-date {
  color: #252525;
  text-align: center;
}

.event-schedule-area-two .tab-content .table tbody tr th .event-date span {
  font-size: 50px;
  line-height: 50px;
  font-weight: normal;
}

.event-schedule-area-two .tab-content .table tbody tr td {
  padding: 30px 20px;
  vertical-align: middle;
}

.event-schedule-area-two .tab-content .table tbody tr td .r-no span {
  color: #252525;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap h3 a {
  font-size: 20px;
  line-height: 20px;
  color: #cf057c;
  -webkit-transition: all 0.4s;
  -o-transition: all 0.4s;
  transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap h3 a:hover {
  color: #4125dd;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories {
  display: -webkit-inline-box;
  display: -ms-inline-flexbox;
  display: inline-flex;
  margin: 10px 0;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories a {
  color: #252525;
  font-size: 16px;
  margin-left: 10px;
  -webkit-transition: all 0.4s;
  -o-transition: all 0.4s;
  transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .categories a:before {
  content: "\f07b";
  font-family: fontawesome;
  padding-right: 5px;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .time span {
  color: #252525;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers {
  display: -webkit-inline-box;
  display: -ms-inline-flexbox;
  display: inline-flex;
  margin: 10px 0;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a {
  color: #4125dd;
  font-size: 16px;
  -webkit-transition: all 0.4s;
  -o-transition: all 0.4s;
  transition: all 0.4s;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a:hover {
  color: #4125dd;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-wrap .organizers a:before {
  content: "\f007";
  font-family: fontawesome;
  padding-right: 5px;
}

.event-schedule-area-two .tab-content .table tbody tr td .primary-btn {
  margin-top: 0;
  text-align: center;
}

.event-schedule-area-two .tab-content .table tbody tr td .event-img img {
  width: 100px;
  height: 100px;
  border-radius: 8px;
}
</style>