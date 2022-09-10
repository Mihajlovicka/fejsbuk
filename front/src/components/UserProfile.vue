<template >
  <div>
    <div class="container">
      <div class="profile-page tx-13">
        <div class="row">
          <div class="col-12 grid-margin">
            <div class="profile-header">
              <div class="cover">
                <div class="gray-shade"></div>
                <figure>
                  <img src="https://bootdey.com/img/Content/bg1.jpg" style="height: 50vh;" class="img-fluid"
                       alt="profile cover">
                </figure>
                <div class="cover-body d-flex justify-content-between align-items-center">
                  <div>
                    <img v-if="profilePicture != undefined" class="profile-pic" :src="profilePicture" alt="profile" @click="showProfilePicture">
                    <img v-if="profilePicture == undefined" class="profile-pic" :src="require('../assets/pictures/no_image.jpg')" alt="profile" @click="showProfilePicture">
                    <span class="profile-name">{{ user.name }} {{ user.surname }}</span>
                  </div>
                  <div>
                    <button class="btn btn-icon-text btn-edit-profile btn-outline-success col-9" style="width:100%; margin-bottom: 5px;" @click="manageFriendRequest(true,user);" v-if="!personalProfile && !requestSent && !checkFriendship && requestRecieved">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-check" viewBox="0 0 16 16">
                        <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                        <path fill-rule="evenodd" d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                      </svg>
                      Prihvati zahtev
                    </button>
                    <button class="btn btn-icon-text btn-edit-profile btn-outline-danger col-9" style="width:100%;" @click="manageFriendRequest(false, user);" v-if="!personalProfile && !requestSent && !checkFriendship && requestRecieved">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-check" viewBox="0 0 16 16">
                        <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                        <path fill-rule="evenodd" d="M15.854 5.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                      </svg>
                      Odbij zahtev
                    </button>
                      <button v-if="personalProfile" @click="showNewPost = true; showGallery = false; showFriends = false;" class="btn btn-primary btn-icon-text btn-edit-profile">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-edit btn-icon-prepend">
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                        </svg>
                        Nova objava
                      </button>
                    <button class="btn btn-icon btn-outline-primary addFriend" v-if="!personalProfile && !requestSent && !checkFriendship && !requestRecieved" @click="sendFriendRequest()">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user-plus" data-toggle="tooltip" title="" data-original-title="Connect">
                        <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="8.5" cy="7" r="4"></circle>
                        <line x1="20" y1="8" x2="20" y2="14"></line>
                        <line x1="23" y1="11" x2="17" y2="11"></line>
                      </svg>
                      Dodaj prijatelja
                    </button>

                    <button class="btn btn-icon btn-outline-danger"  v-if="!personalProfile && checkFriendship" style="width:100%; margin-top:5px;" @click="removeFriend();">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-dash-fill" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
                        <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                      </svg>
                      Ukloni prijatelja
                    </button>

                    <button class="btn btn-icon btn-outline-primary addFriend" v-if="!personalProfile && requestSent && !checkFriendship" @click="cancelRequest()">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-person-dash-fill" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
                        <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                      </svg>
                      Otkazi zahtev
                    </button>
                    </div>
                </div>
              </div>
              <div class="header-links">
                <ul class="links d-flex align-items-center mt-3 mt-md-0" id="menu_nav_items">
                  <li class="header-link-item d-flex align-items-center active" @>
                     <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="feather feather-columns mr-1 icon-md">
                      <path
                          d="M12 3h7a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-7m0-18H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h7m0-18v18"></path>
                    </svg>
                    <a class="pt-1px d-none d-md-block" @click="showNewPost = false;showGallery = false; showFriends = false;changeActiveComponent();">Objave</a>
                  </li>
                  <li class="header-link-item ml-3 pl-3 border-left d-flex align-items-center" @click="showGallery = false; showNewPost = false; showFriends = true;changeActiveComponent();">
                     <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="feather feather-users mr-1 icon-md">
                      <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                      <circle cx="9" cy="7" r="4"></circle>
                      <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                      <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                    <a class="pt-1px d-none d-md-block">Prijatelji</a>
                  </li>
                  <li @click="showGallery = true; showNewPost = false; showFriends = false;changeActiveComponent();" class="header-link-item ml-3 pl-3 border-left d-flex align-items-center">
                     <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="feather feather-image mr-1 icon-md">
                      <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                      <circle cx="8.5" cy="8.5" r="1.5"></circle>
                      <polyline points="21 15 16 10 5 21"></polyline>
                    </svg>
                    <a class="pt-1px d-none d-md-block">Slike</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div> <!--heder-->
        <div class="row profile-body">
          <!-- left wrapper start -->
          <div class="d-none d-md-block col-md-4 col-xl-4 left-wrapper">
            <div class="card rounded">
              <div class="card-body">
                <div class="d-flex align-items-center justify-content-between mb-2">
                  <h6 class="tx-11 font-weight-bold mb-0 text-uppercase">O {{ user.name }} {{ user.surname }}</h6>
                  <div v-if="personalProfile" class="dropdown">
                    <button class="btn p-0" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                           stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                           class="feather feather-more-horizontal icon-lg text-muted pb-3px">
                        <circle cx="12" cy="12" r="1"></circle>
                        <circle cx="19" cy="12" r="1"></circle>
                        <circle cx="5" cy="12" r="1"></circle>
                      </svg>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                      <div class="dropdown-item d-flex align-items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-edit-2 icon-sm mr-2">
                          <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                        </svg>
                        <span class=""><router-link :to="{name:'personalInfo', params:{username:username}}">Izmena licnih informacija</router-link></span>
                      </div>
                      <div class="dropdown-item d-flex align-items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-edit-2 icon-sm mr-2">
                          <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                        </svg>
                        <span class=""><router-link :to="{name:'passwordChange', params:{username:username}}">Promena lozinke</router-link></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="mt-3">
                  <label class="tx-11 font-weight-bold mb-0 text-uppercase">Email: </label>
                  <p class="text-muted">{{ user.email }}</p>
                </div>
                <div class="mt-3">
                  <label class="tx-11 font-weight-bold mb-0 text-uppercase">Datum rodjenja:</label>
                  <p class="text-muted">{{ user.birthDate | dateViewFilter }}</p>
                </div>
              </div>
            </div>
          </div>
          <!-- left wrapper end -->
          <!-- middle wrapper start -->
          <div class="col-md-8 col-xl-8 middle-wrapper">
            <new-post v-if="showNewPost && !showGallery && !showFriends"></new-post>
            <user-posts v-if="!showNewPost && !showGallery && !showFriends"></user-posts>
            <gallery-view v-if="showGallery && !showNewPost && !showFriends"></gallery-view>
            <friends-view v-if="showFriends && !showGallery && !showNewPost"></friends-view>
          </div>
        </div>
      </div>
    </div>
    <div id="profilePicModal" class="modal">
      <span class="close cursor" @click="closeModal()">&times;</span>
      <div class="modal-content d-flex justify-content-around mb-2">

        <div class="mySlidesProfile" id="profPic">
          <div class="options">
            <div v-if="personalProfile" class="dropdown">
              <button class="btn p-0" type="button" id="dropdownMenuButtonPicture" data-toggle="dropdown"
                      aria-haspopup="true" aria-expanded="false">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="feather feather-more-horizontal icon-lg text-muted pb-3px">
                  <circle cx="12" cy="12" r="1"></circle>
                  <circle cx="19" cy="12" r="1"></circle>
                  <circle cx="5" cy="12" r="1"></circle>
                </svg>
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonPicture">
                <div class="dropdown-item d-flex align-items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                       class="feather feather-edit-2 icon-sm mr-2">
                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                  </svg>
                  <span class=""><router-link :to="{name:'newProfilePic', params:{username:username}}">Izaberi profilnu fotografiju</router-link></span>
                </div>
                <div class="dropdown-item d-flex align-items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                       class="feather feather-edit-2 icon-sm mr-2">
                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                  </svg>
                  <span class=""><a href="" @click="deletePost()">Izbrisi objavu</a></span>
                </div>
              </div>
            </div>
          </div>

          <img id="modelPicture" style="max-width:600px; height: 70vh;">

        </div>
        <div class="description"><h5>{{post.description}}</h5></div>
      </div>
    </div>
  </div>
</template>

<script>
import UserPosts from "@/components/UserPosts";
import axios from "axios";
import NewPost from "@/components/NewPost";
import GalleryView from "@/components/GalleryView";
import FriendsView from "@/components/FriendsView";
export default {
  name: "UserProfile",
  components:
      {
        GalleryView,
        NewPost,
        UserPosts,
        FriendsView
      },
  data() {
    return {
      username: this.$route.params.username,
      personalProfile:false,
      profilePicture: '',
      user: Object,
      post:{description:''},
      showNewPost:false,
      showGallery:false,
      showFriends:false,
      logged_username: '',
      loggedUser:Object,
      sentRequests:[],
      recievedRequests:[],
      friends:[],
      isLogged: false,
    }
  },
  computed : {
    requestSent(){
      for(let i = 0; i < this.sentRequests.length; i++){
        if(this.username === this.sentRequests[i]){
          return true;
        }
      }
      return false;
    },
    requestRecieved(){
      for(let i = 0; i < this.recievedRequests.length; i++){
        if(this.loggedUser.username === this.recievedRequests[i]){
          return true;
        }
      }
      return false;
    },
    checkFriendship(){
      for(let i = 0; i < this.friends.length; i++){
        if(this.logged_username === this.friends[i]){
          return true;
        }
      }
      return false;
    },
  },
  methods: {
    manageFriendRequest(accepted, user){

      if(accepted == true) {
        axios.post('/acceptFriendRequest', user).then(resp => {
          alert("Uspesno prihvacen zahtev za prijateljstvo.")
          console.log(resp.data)
          this.loggedUser = resp.data;
          this.friends = this.loggedUser.friendships;
          this.getPictures();
          this.$forceUpdate();


        }).catch(resp => {
          alert(resp.data.error)
        })
      }
      else{
        axios.post('/rejectFriendRequest', user).then(resp => {
          alert("Uspesno odbijen zahtev za prijateljstvo.")
          console.log(resp.data)
          this.loggedUser = resp.data;
          this.friends = this.loggedUser.friendships;
          this.getPictures();
          this.$forceUpdate();

        }).catch(resp => {
          alert(resp.data.error)
        })
      }
      this.$router.go();
      this.$forceUpdate();
    },
    removeFriend(){


      axios.post('/removeFriend', this.user).then(resp => {
        alert("Uspesno uklonjen prijatelj. Cestitke zivot je sada lepsi, jer lazni prijatelji vam ne trebaju u zivotu! 🤗")
        console.log(resp.data)
        console.log(this.friends);
        this.loggedUser = resp.data;
        this.friends = this.loggedUser.friendships;
        console.log(this.friends);
        this.getPictures();
      }).catch(resp => {
        alert(resp.data.error)
      })
      this.loadRequests();
      this.$forceUpdate();
    },

    cancelRequest(){
      axios.post('/cancelFriendRequest',this.user).then(resp => {
        alert("Uspesno otkazan zahtev za prijateljstvo.")
        console.log(resp.data)
        this.sentRequests = resp.data.friendshipRequests;
        this.$forceUpdate();
      }).catch(resp => {
        alert(resp.response.data.error)
      })
    },

    sendFriendRequest(){
      axios.post('/addFriend',this.user).then(resp => {
        alert("Uspesno poslat zahtev za prijateljstvo.")
        console.log(resp.data)
        this.sentRequests = resp.data.friendshipRequests;
        this.$forceUpdate();
      }).catch(resp => {
        alert(resp.response.data.error)
      })
    },
    showProfilePicture() {
      document.getElementById("profilePicModal").style.display = "block";
      document.getElementById("profPic").style.display = "block";
      if(this.profilePicture != undefined){
      document.getElementById("modelPicture").src = this.profilePicture;
      axios.get('/getPostByPicture',{params: {username:this.username, picture:this.user.profilePicture}}).then(resp => {
        this.post = resp.data;
      }).catch(resp => {
        alert(resp.response.data.error)
      });}
      else{
        document.getElementById("modelPicture").src = require('../assets/pictures/no_image.jpg');
      }

    }, closeModal() {
      document.getElementById("profilePicModal").style.display = "none";
    },
    deletePost(){
      if(this.profilePicture == undefined) return
      axios.post('/deletePost',this.post.id).then(() => {
        alert("Uspesno brisanje.")
        this.closeModal()
        this.$forceUpdate();
      }).catch(resp => {
        alert(resp.response.data.error)
      })
      axios.get('/getUser', {params: {username: this.username}}).then(resp => {
        this.user = resp.data;
        this.profilePicture = this.getPictures(this.user.profilePicture);
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
        if (img_name.split('/')[1] == file && img_name.split('/')[0] == this.username)
          return require("../assets/pictures/" + img_name);
      }
    },
    changeActiveComponent(){
      let activeComponent = 0;
      if(this.showGallery) activeComponent = 2;
      else if (this.showFriends) activeComponent = 1;

      for(let i=0; i<document.getElementById("menu_nav_items").children.length; i++){
        document.getElementById("menu_nav_items").children[i].classList.remove('active');
      }
      document.getElementById("menu_nav_items").children[activeComponent].classList.add('active');
    }
  },
  created() {
    this.personalProfile = false;
    axios.get('/getUser', {params: {username: this.username}}).then(resp => {
      this.user = resp.data;
      this.friends = this.user.friendships;
      this.username = this.user.username;

      this.recievedRequests = this.user.friendshipRequests;
      this.profilePicture = this.getPictures([this.user.profilePicture]);
    })
    axios.get('/getLoggedInUser').then(resp => {
      this.logged_username = resp.data.username;
      this.loggedUser = resp.data;
      this.sentRequests = this.loggedUser.friendshipRequests;
      this.isLogged = true;
      if(resp.data.username === this.username)
        this.personalProfile = true;
    }).catch(resp => {
      this.isLogged = false;
      console.log(resp.data);
    });

  }
}
</script>

<style scoped>
.addFriend{
  color:black;
  border:none;
}
.addFriend:hover{
   color:white;

 }
body {
  background-color: #f9fafb;
  margin-top: 20px;
}

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
  z-index: 3;
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
  /*flex-flow: row; da bude jedno posred drugog*/
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
.mySlidesProfile {
  display: none;
}

.options {
  color: #f2f2f2;
  font-size: 12px;
  padding: 8px 12px;
  position: absolute;
  top: 0;
  right: 0;
}
.description {
  font-size: 12px;
  padding: 8px 12px;
  max-width:600px;
  height: 30vh;
  margin-top: 50px;
  left: 0;
}
</style>