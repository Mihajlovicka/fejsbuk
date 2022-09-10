<template>
  <div class="row">
    <div class="col-md-12 grid-margin">
      <div class="card rounded">
        <div class="card-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="d-flex align-items-center">
              <img v-if="profilePicture != undefined" class="img-s rounded-circle" :src="profilePicture" alt="profile" @click="showProfilePicture">
              <img v-if="profilePicture == undefined" class="img-s rounded-circle" :src="require('../assets/pictures/no_image.jpg')" alt="profile" @click="showProfilePicture">
              <div class="ml-2">
                <h5>{{user.name}} {{user.surname}}</h5>
                <p class="tx-11 text-muted">Nova objava</p>
              </div>
            </div>
          </div>
        </div>
        <div class="card-body">
          <div class="wrapper">
            <form enctype="multipart/form-data">
              <div class="form-group d-flex align-items-center" >
                <textarea class="form-control"  placeholder="Tekst objave..." v-model="new_post" rows="4" cols="50"/>
              </div>

              <div class="form-group d-flex align-items-center" >
                <div class="custom-file">
                  <input type="file" id="newPicture" class="custom-file-input" accept=".jpg,.jpeg,.png">
                  <label class="custom-file-label" >Izaberi sliku...</label>
                </div></div>
              <div class="d-flex justify-content-end" >
                <button class="btn btn-primary mb-3" type="submit" @click.stop.prevent="newPost">Objavi</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "NewPost",
  data(){
    return{
      username:this.$route.params.username,
      profilePicture:'',
      new_post:'',
    }
  },
  methods:{
    newPost(){
      var picture = document.getElementById("newPicture")
      if (picture.files.length === 0 && this.new_post === "") {
        return
      }
      let file = picture.files[0]
      let formData = new FormData();
      formData.set('file', file);
      formData.set('text', this.new_post)
      axios.post('/newPost', formData).then(resp => {
        alert(resp.data.success)
        window.location.assign("/userProfile/"+this.username);
      }).catch(resp => {
        alert(resp.data.error)
      })
    },
    getPictures(file) {
      var pom = require.context(
          "../assets/pictures/",
          true,
          /^.*\.(jpe?g|png|gif)$/
      ).keys();
      for (let image of pom) {
        let img_name = image.replace('./', '')
        if (img_name.split("/")[1] == file && img_name.split("/")[0] == this.username)
          return require("../assets/pictures/" + img_name);
      }
    }
  },
  created() {
    axios.get('/getUser',{params: {username:this.username}}).then(resp => {
      this.user = resp.data;
      this.profilePicture = this.getPictures([this.user.profilePicture]);
    });
  },
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

.error-message {
  color: red;
}
input:focus {
  outline: none;
}
.error {
  color:red;
  border: 1px solid red !important;
}
/* Importing fonts from Google */
/* Reseting */


.wrapper {
  max-width: 450px;
  margin: 50px auto;
  padding: 20px 30px;
  min-height: 300px;
  background-color: #ffffff27;
  border-top: 1px solid #ffffff6e;
  border-left: 1px solid #ffffff6e;
  border-radius: 15px;
}

.wrapper .h5 {
  /*color: #ddd;*/
}

.wrapper .form-group {
  border-bottom: 1px solid #ccc;
  margin-bottom: 1.5rem;
}

.wrapper .form-group:hover {
  border-bottom: 1px solid #eee;
}

.wrapper .form-group .icon {
  /*color: #e8e8e8;*/
}

.wrapper .form-group .form-control {
  background: inherit;
  border: none;
  border-radius: 0px;
  box-shadow: none;
  /*color: #e9e9e9;*/
}

.wrapper .form-group input::placeholder {
  /*color: #ccc;*/
}

.wrapper .form-group input:focus::placeholder {
  opacity: 0;
}

.wrapper .form-group .fa-phone {
  transform: rotate(90deg);
}


.wrapper .btn.btn-primary {
  position: relative;
  color: black;
  padding: 0.3rem 1rem;
  border-radius: 20px;
  border: 1px solid #ddd;
  background-color: inherit;
  box-shadow: none;
  overflow: hidden;
}

.wrapper .btn.btn-primary:hover {
  background-color: #b4b4b433;
  color: #fff;
}

.wrapper .btn.btn-primary::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 25px;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.6);
  transform: skew(45deg) translateX(130px);
  transition: .5s;
  opacity: 0;
}

.wrapper .btn.btn-primary:hover::before {
  opacity: 1;
  transform: skew(45deg) translateX(-130px);
}

@media (max-width: 460px) {
  .wrapper {
    margin: 15px;
    padding: 20px;
  }

  .wrapper .connect::after {
    left: 38%;
  }
}

@media (max-width: 345px) {
  .wrapper .connect::after {
    left: 32%;
  }
}
</style>