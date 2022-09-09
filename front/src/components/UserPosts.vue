<template>
<div class="row">
    <div class="col-md-12 grid-margin" v-for="post in posts" v-bind:key="post">
      <div class="card rounded">
        <div class="card-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="d-flex align-items-center">
              <img v-if="post.profilePic != undefined" class="img-s rounded-circle" :src="images[post.username+'/'+post.profilePic]" alt="profile">
              <img v-if="post.profilePic == undefined" class="img-s rounded-circle" :src="require('../assets/pictures/no_image.jpg')" alt="profile">
<div class="ml-2">
                <h5>{{post.nameSurname}}</h5>
              </div>
            </div>
            <div v-if="$parent.personalProfile" class="dropdown">
              <button class="btn p-0" type="button" id="dropdownMenuButton2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal icon-lg pb-3px">
                  <circle cx="12" cy="12" r="1"></circle>
                  <circle cx="19" cy="12" r="1"></circle>
                  <circle cx="5" cy="12" r="1"></circle>
                </svg>
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton2">
                <button class="dropdown-item d-flex align-items-center" @click="deletePost(post)">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-meh icon-sm mr-2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="8" y1="15" x2="16" y2="15"></line>
                    <line x1="9" y1="9" x2="9.01" y2="9"></line>
                    <line x1="15" y1="9" x2="15.01" y2="9"></line>
                  </svg> <span class="">Obrisi</span></button>
               </div>
            </div>
          </div>
        </div>
        <div class="card-body">
          <p class="mb-3 tx-14">{{post.description}}</p>
          <img class="img-fluid" :src="images[post.username+'/'+post.picture]" alt="">
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "UserPosts",
  data(){
    return{
      images:{},
      posts:{},
      logged_username:'',
    }
  },
  methods:{
    deletePost(post){
      axios.post('/deletePost',post.id).then(resp => {
        this.posts = resp.data
        alert("Uspesno brisanje.")
      }).catch(resp => {
        alert(resp.data.error)
      })
    },
  },
  created() {
    axios.get('/getPosts', {params: {username: this.$parent.username}}).then(resp => {
      this.posts = resp.data;
    })
    let post_images = {}
    var pom = require.context(
        "../assets/pictures/",
        true,
        /^.*\.(jpe?g|png|gif)$/
    ).keys();
    for (let image of pom) {
      let img_name = image.replace('./', '')
      post_images[img_name] = require("../assets/pictures/" + img_name);

    }
    this.images = post_images
  },
}
</script>

<style scoped>
.img-fluid{
  max-height: 50vh;
}
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
.img-s {
  width: 50px;
  height: 50px;
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
</style>