<template>
<div>
  <div class="container">
    <div class="profile-page tx-13">
      <div class="row">
        <div class="col-12 grid-margin">
          <div class="profile-header">
            <div class="cover">
              <div class="gray-shade"></div>
              <figure>
                <img src="https://bootdey.com/img/Content/bg1.jpg" style="height: 50vh;" class="img-fluid" alt="profile cover">
              </figure>
              <div class="cover-body d-flex justify-content-between align-items-center">
                <div>
                  <img class="profile-pic" :src="profilePicture" alt="profile" @click="showProfilePicture">
                  <span class="profile-name">{{user.name}} {{user.surname}}</span>
                </div>
                <router-link :to="{name:'personalInfo', params:{username:username}}">
                <div class="d-none d-md-block">
                  <button class="btn btn-primary btn-icon-text btn-edit-profile">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit btn-icon-prepend">
                      <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                      <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                    </svg> Izmeni profil
                  </button>
                </div></router-link>
              </div>
            </div>
            <div class="header-links">
              <ul class="links d-flex align-items-center mt-3 mt-md-0">
                <li class="header-link-item d-flex align-items-center active">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-columns mr-1 icon-md">
                    <path d="M12 3h7a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-7m0-18H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h7m0-18v18"></path>
                  </svg>
                  <a class="pt-1px d-none d-md-block" href="#">Objave</a>
                </li>
                <li class="header-link-item ml-3 pl-3 border-left d-flex align-items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users mr-1 icon-md">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                    <circle cx="9" cy="7" r="4"></circle>
                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                  </svg>
                  <a class="pt-1px d-none d-md-block" href="#">Prijatelji</a>
                </li>
                <li class="header-link-item ml-3 pl-3 border-left d-flex align-items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-image mr-1 icon-md">
                    <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                    <circle cx="8.5" cy="8.5" r="1.5"></circle>
                    <polyline points="21 15 16 10 5 21"></polyline>
                  </svg>
                  <a class="pt-1px d-none d-md-block" href="#">Slike</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div> <!--heder-->
      <div class="row profile-body">
        <!-- left wrapper start -->
        <div class="d-none d-md-block col-md-4 col-xl-3 left-wrapper">
          <div class="card rounded">
            <div class="card-body">
              <div class="d-flex align-items-center justify-content-between mb-2">
                <h6 class="tx-11 font-weight-bold mb-0 text-uppercase">O {{user.name}} {{user.surname}}</h6>
                <div class="dropdown">
                  <button class="btn p-0" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal icon-lg text-muted pb-3px">
                      <circle cx="12" cy="12" r="1"></circle>
                      <circle cx="19" cy="12" r="1"></circle>
                      <circle cx="5" cy="12" r="1"></circle>
                    </svg>
                  </button>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <div class="dropdown-item d-flex align-items-center">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 icon-sm mr-2">
                        <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                      </svg> <span class=""><router-link :to="{name:'personalInfo', params:{username:username}}">Izmena licnih informacija</router-link></span></div>
                    <div class="dropdown-item d-flex align-items-center">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 icon-sm mr-2">
                        <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                      </svg> <span class=""><router-link :to="{name:'passwordChange', params:{username:username}}">Promena lozinke</router-link></span></div>
                  </div>
                </div>
              </div>
              <div class="mt-3">
                <label class="tx-11 font-weight-bold mb-0 text-uppercase">Email: </label>
                <p class="text-muted">{{ user.email}}</p>
              </div>
              <div class="mt-3">
                <label class="tx-11 font-weight-bold mb-0 text-uppercase">Datum rodjenja:</label>
                <p class="text-muted">{{ user.birthDate | dateViewFilter}}</p>
              </div>
            </div>
          </div>
        </div>
        <!-- left wrapper end -->
        <!-- middle wrapper start -->
<!--        <div class="col-md-8 col-xl-6 middle-wrapper">-->
<!--&lt;!&ndash;          <user-posts></user-posts>&ndash;&gt;-->
<!--          <gallery-view></gallery-view>-->
<!--        </div>-->
        <!-- middle wrapper end -->
        <div class="col-md-8 middle-wrapper">
          <gallery-view></gallery-view>
        </div>
      </div>
    </div>
  </div>
  <div id="profilePicModal" class="modal">
    <span class="close cursor" @click="closeModal()">&times;</span>
    <div class="modal-content">

      <div class="mySlides" id="profPic">
        <div class="options ">
          <div class="dropdown">
            <button class="btn p-0" type="button" id="dropdownMenuButtonPicture" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal icon-lg text-muted pb-3px">
                <circle cx="12" cy="12" r="1"></circle>
                <circle cx="19" cy="12" r="1"></circle>
                <circle cx="5" cy="12" r="1"></circle>
              </svg>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonPicture">
              <div class="dropdown-item d-flex align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 icon-sm mr-2">
                  <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                </svg> <span class=""><router-link :to="{name:'newProfilePic', params:{username:username}}">Izaberi profilnu fotografiju</router-link></span></div>
            </div>
          </div>
        </div>
        <img :src="profilePicture" style="max-width:600px; height: 70vh;">
      </div>

    </div>
  </div>
</div>
</template>

<script>
// import UserPosts from "@/components/UserPosts";
import axios from "axios";
import GalleryView from "@/components/GalleryView";
export default {
  name: "UserProfile",
  components: {GalleryView,
    // UserPosts
  },
  data(){
    return {
      username:this.$route.params.username,
      profilePicture: "",
      user:Object
    }
  },
  methods:{
    showProfilePicture(){
      document.getElementById("profilePicModal").style.display = "block";
      document.getElementById("profPic").style.display = "block";
    },closeModal() {
      document.getElementById("profilePicModal").style.display = "none";
    },
    getPictures(file){
      var pom = require.context(
          "../assets/pictures/",
          true,
          /^.*\.(jpe?g|png|gif)$/
      ).keys();
      for (let image of pom){
        let img_name = image.replace('./','')
        if(img_name == file)
          return require("../assets/pictures/"+ img_name);
      }
    }
  },
  created() {
    axios.get('/getUser',{params: {username:this.username}}).then(resp => {
      this.user = resp.data;
      this.profilePicture = this.getPictures([this.user.profilePicture]);
    })
  }
}
</script>

<style scoped>
body{
  background-color: #f9fafb;
  margin-top:20px;}

.profile-page .profile-header {
  box-shadow: 0 0 10px 0 rgba(183, 192, 206, 0.2);
  border: 1px solid #f2f4f9;
}

.profile-page .profile-header .cover {
  position: relative;
  border-radius: .25rem .25rem 0 0;
}


.profile-page .profile-header .cover figure {
  margin-bottom: 0;
}

@media (max-width: 767px) {
  .profile-page .profile-header .cover figure {
    height: 110px;
    overflow: hidden;
  }
}

@media (min-width: 2400px) {
  .profile-page .profile-header .cover figure {
    height: 280px;
    overflow: hidden;
  }
}

.profile-page .profile-header .cover figure img {
  border-radius: .25rem .25rem 0 0;
  width: 100%;
}

@media (max-width: 767px) {
  .profile-page .profile-header .cover figure img {
    -webkit-transform: scale(2);
    transform: scale(2);
    margin-top: 15px;
  }
}

@media (min-width: 2400px) {
  .profile-page .profile-header .cover figure img {
    margin-top: -55px;
  }
}

.profile-page .profile-header .cover .gray-shade {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  background: linear-gradient(rgba(255, 255, 255, 0.1), #fff 99%);
}

.profile-page .profile-header .cover .cover-body {
  position: absolute;
  bottom: -20px;
  left: 0;
  z-index: 2;
  width: 100%;
  padding: 0 20px;
}

.profile-page .profile-header .cover .cover-body .profile-pic {
  border-radius: 50%;
  width: 100px;
}

@media (max-width: 767px) {
  .profile-page .profile-header .cover .cover-body .profile-pic {
    width: 70px;
  }
}

.profile-page .profile-header .cover .cover-body .profile-name {
  font-size: 20px;
  font-weight: 600;
  margin-left: 17px;
}

.profile-page .profile-header .header-links {
  padding: 15px;
  display: -webkit-flex;
  display: flex;
  -webkit-justify-content: center;
  justify-content: center;
  background: #fff;
  border-radius: 0 0 .25rem .25rem;
}

.profile-page .profile-header .header-links ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.profile-page .profile-header .header-links ul li a {
  color: #000;
  -webkit-transition: all .2s ease;
  transition: all .2s ease;
}

.profile-page .profile-header .header-links ul li:hover,
.profile-page .profile-header .header-links ul li.active {
  color: #727cf5;
}

.profile-page .profile-header .header-links ul li:hover a,
.profile-page .profile-header .header-links ul li.active a {
  color: #727cf5;
}

.profile-page .profile-body .left-wrapper .social-links a {
  width: 30px;
  height: 30px;
}

.profile-page .profile-body .right-wrapper .latest-photos > .row {
  margin-right: 0;
  margin-left: 0;
}

.profile-page .profile-body .right-wrapper .latest-photos > .row > div {
  padding-left: 3px;
  padding-right: 3px;
}

.profile-page .profile-body .right-wrapper .latest-photos > .row > div figure {
  -webkit-transition: all .3s ease-in-out;
  transition: all .3s ease-in-out;
  margin-bottom: 6px;
}

.profile-page .profile-body .right-wrapper .latest-photos > .row > div figure:hover {
  -webkit-transform: scale(1.06);
  transform: scale(1.06);
}

.profile-page .profile-body .right-wrapper .latest-photos > .row > div figure img {
  border-radius: .25rem;
}

.rtl .profile-page .profile-header .cover .cover-body .profile-name {
  margin-left: 0;
  margin-right: 17px;
}
.rounded-circle {
  border-radius: 50% !important;
}
img {
  vertical-align: middle;
  border-style: none;
}

.card-header:first-child {
  border-radius: 0 0 0 0;
}
.card-header {
  padding: 0.875rem 1.5rem;
  margin-bottom: 0;
  background-color: rgba(0, 0, 0, 0);
  border-bottom: 1px solid #f2f4f9;
}

.card-footer:last-child {
  border-radius: 0 0 0 0;
}
.card-footer {
  padding: 0.875rem 1.5rem;
  background-color: rgba(0, 0, 0, 0);
  border-top: 1px solid #f2f4f9;
}

.grid-margin {
  margin-bottom: 1rem;
}

.card {
  box-shadow: 0 0 10px 0 rgba(183, 192, 206, 0.2);
  -webkit-box-shadow: 0 0 10px 0 rgba(183, 192, 206, 0.2);
  -moz-box-shadow: 0 0 10px 0 rgba(183, 192, 206, 0.2);
  -ms-box-shadow: 0 0 10px 0 rgba(183, 192, 206, 0.2);
}
.rounded {
  border-radius: 0.25rem !important;
}
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 1px solid #f2f4f9;
  border-radius: 0.25rem;
}


.modal {
  display: none;
  position: fixed;
  z-index: 1;
  padding-top: 100px;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: black;
}

/* Modal Content */
.modal-content {
  position: relative;
  background-color: #fefefe;
  margin: auto;
  padding: 0;
  width: 90%;
  max-width: 1200px;
}

/* The Close Button */
.close {
  color: white;
  position: absolute;
  top: 10px;
  right: 25px;
  font-size: 35px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #999;
  text-decoration: none;
  cursor: pointer;
}

/* Hide the slides by default */
.mySlides {
  display: none;
}
.options {
  color: #f2f2f2;
  font-size: 12px;
  padding: 8px 12px;
  position: absolute;
  top: 0;
}
</style>