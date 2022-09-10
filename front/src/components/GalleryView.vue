<template>
  <div>
    <div class="row">
      <div class="container">
        <div class="row">
          <div class="column" v-for="(i,k) in images" v-bind:key="k">
            <img class="hover-shadow" style="width: 100%; height: 100%" :src="i" alt=""
                 v-on:click="openModal();currentSlide(k)"/>
          </div>
        </div>
      </div>
    </div>
    <div id="myModal" class="modal">
      <span class="close cursor" @click="closeModal()">&times;</span>
      <div class="modal-content d-flex justify-content-around mb-2">

        <div class="mySlides" v-for="i in images" v-bind:key="i">
          <div class="options">
            <div v-if="$parent.personalProfile" class="dropdown">
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
                  <span class=""><a href="" @click.prevent="changeProfilePicture">Izaberi kao profilnu fotografiju</a></span>
                </div>
                <div class="dropdown-item d-flex align-items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                       stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                       class="feather feather-edit-2 icon-sm mr-2">
                    <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                  </svg>
                  <span class=""><a href="" @click="deletePost()">Obrisi objavu</a></span>
                </div>
              </div>
            </div>
          </div>
          <img :src="i" style="max-width:600px; height: 70vh;">
        </div>
        <div class="description"><h5>{{post.description}}</h5></div>

        <div class="komentari">

          <div class="container justify-content-center mt-5 border-left border-right">
            <div class="d-flex justify-content-center pt-3 pb-2" v-if="logged_username != ''">
              <input type="text" name="text" placeholder="+ nov komentar" class="form-control addtxt" :id="post.id">
              <button class="btn btn-icon btn-outline-primary" @click="newComment(post)">Posalji komentar</button>
            </div>
            <div  class="d-flex justify-content-center py-2" v-for="comment in post.comments" :key="comment">
              <div class="second py-2 px-2" v-if="!comment.deleted">
                <div class="d-flex justify-content-between py-1 pt-2">
                  <div>
                    <span class="text2">{{comment.username.name}} {{comment.username.surname}}</span></div>
                </div>
                <span class="text1" >{{comment.text}}</span>
                <div v-if="comment.username.username == logged_username" class="dropdown">
                  <button class="btn p-0" type="button" id="dropdownMenuButton3" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-more-horizontal icon-lg pb-3px">
                      <circle cx="12" cy="12" r="1"></circle>
                      <circle cx="19" cy="12" r="1"></circle>
                      <circle cx="5" cy="12" r="1"></circle>
                    </svg>
                  </button>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton3">
                    <a href="" class="dropdown-item d-flex align-items-center" @click="deleteComment(post, comment.id)">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-meh icon-sm mr-2">
                        <circle cx="12" cy="12" r="10"></circle>
                        <line x1="8" y1="15" x2="16" y2="15"></line>
                        <line x1="9" y1="9" x2="9.01" y2="9"></line>
                        <line x1="15" y1="9" x2="15.01" y2="9"></line>
                      </svg> <span class="">Obrisi</span></a>
                    <a href=""  class="dropdown-item d-flex align-items-center" @click="changeComment(post, comment.id)">
                      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-meh icon-sm mr-2">
                        <circle cx="12" cy="12" r="10"></circle>
                        <line x1="8" y1="15" x2="16" y2="15"></line>
                        <line x1="9" y1="9" x2="9.01" y2="9"></line>
                        <line x1="15" y1="9" x2="15.01" y2="9"></line>
                      </svg> <span class="">Izmeni</span></a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Next/previous controls -->
        <a class="prev" @click="plusSlides(-1)">&#10094;</a>
        <a class="next" @click="plusSlides(1)">&#10095;</a>

      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "GalleryView",
  data() {
    return {
      username:this.$route.params.username,
      images: {},
      slideIndex: 1,
      post:{description:'',},
      logged_username:''
    }
  },
  methods: {
    newComment(post){

      axios.post('/updatePost',{id:post.id,text:document.getElementById(post.id).value}).then(() => {
        alert("Uspesno poslat komentar.")
      }).catch(resp => {
        alert(resp.data.error)
      })
      this.$router.go()
    },
    deleteComment(post, commentId){
      axios.post('/deleteComment',{id:post.id,commentId:commentId}).then(() => {
        alert("Uspesno brisanje komentara.")
      }).catch(resp => {
        alert(resp.data.error)
      })
      this.$router.go()
    },
    changeComment(post, commentId){
      let commentText = prompt("Nov tekst komentara:", "tekst");

      if (commentText == null || commentText == '') {
        alert("Niste uneli nista.")
        return
      }
      axios.post('/changeComment',{id:post.id,commentId:commentId, text:commentText}).then(() => {
        alert("Uspesna izmena.")
      }).catch(resp => {
        alert(resp.data.error)
      })
      this.$router.go()
    },
    changeProfilePicture(){
      if(confirm("da li ste sigurni da zelite da promenite profilnu sliku?")){
          axios.post('/changeProfilePhoto',this.post.picture).then( () => {
            this.closeModal()
            this.$router.go()
          }).catch(resp => {
            alert(resp.response.data.error)
          })
        }

    },
    deletePost(){
      axios.post('/deletePost',this.post.id).then(() => {
        alert("Uspesno brisanje.");
        this.closeModal()
        this.$forceUpdate();
      }).catch(resp => {
        alert(resp.response.data.error)
      })
      axios.get('/getUser', {params: {username: this.username}}).then(resp => {
        this.user = resp.data;
        this.profilePicture = this.getPictures([this.user.profilePicture]);
      })
    },
    openModal() {
      document.getElementById("myModal").style.display = "block";
    }, closeModal() {
      document.getElementById("myModal").style.display = "none";
    }, currentSlide(img) {
      this.post.description = ""
      let n = 0;
      for(let k in this.images){
        n = n+ 1;
        if(k == img) break
      }
      axios.get('/getPostByPicture',{params: {username:this.username, picture:img}}).then(resp => {
        this.post = resp.data;
      }).catch(resp => {
        alert(resp.response.data.error)
      });
      this.showSlides(this.slideIndex = n);
    }, plusSlides(n) {
      this.showSlides(this.slideIndex += n);
    },
    showSlides(n) {
      this.post.description = "";
      let j = 0, img = "";
      for(let k in this.images){
        j = j+ 1;
        if(j == n) img = k;
      }
      axios.get('/getPost',{params: {username:this.username, picture:img}}).then(resp => {
        this.post = resp.data;
      }).catch(resp => {
        alert(resp.response.data.error)
      });
      var i;
      var slides = document.getElementsByClassName("mySlides");
      if (n > slides.length) {
        this.slideIndex = 1
      }
      if (n < 1) {
        this.slideIndex = slides.length
      }
      for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
      }
      slides[this.slideIndex - 1].style.display = "block";
    },
    getPictures() {
      var pom = require.context(
          "../assets/pictures/",
          true,
          /^.*\.(jpe?g|png|gif)$/
      ).keys();
      for (let image of pom) {
        let img_name = image.replace('./', '')
        if(img_name.split('/')[0] == this.username) {
          img_name = img_name.split('/')[1]
          this.images[img_name] = require("../assets/pictures/" + this.username + "/" + img_name)
        }
      }
    }
  },
  created() {
    this.getPictures()
    axios.get('/getLoggedInUser').then(resp => {
      this.logged_username = resp.data.username;
    })
  }
}
</script>

<style scoped>
.row > .column {
  padding: 0 8px;
}

.row:after {
  content: "";
  display: table;
  clear: both;
}

.column {
  padding: 10px;
  width: 25%;
  height: 180px;
  border-radius: 5px;
  float: left;
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

/* Next & previous buttons */
.prev,
.next {
  cursor: pointer;
  position: absolute;
  top: 50%;
  width: auto;
  padding: 16px;
  margin-top: -50px;
  color: white;
  font-weight: bold;
  font-size: 20px;
  transition: 0.6s ease;
  border-radius: 0 3px 3px 0;
  user-select: none;
  -webkit-user-select: none;
}

/* Position the "next button" to the right */
.next {
  right: 0;
  border-radius: 3px 0 0 3px;
}

/* On hover, add a black background color with a little bit see-through */
.prev:hover,
.next:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

/* Caption text */
.caption-container {
  text-align: center;
  background-color: black;
  padding: 2px 16px;
  color: white;
}

img.demo {
  opacity: 0.6;
}

.active,
.demo:hover {
  opacity: 1;
}

img.hover-shadow {
  transition: 0.3s;
}

.hover-shadow:hover {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.description {
  font-size: 12px;
  padding: 8px 12px;
  max-width:600px;
  height: 30vh;
  margin-top: 50px;
  left: 0;
}
.options {
  color: #f2f2f2;
  font-size: 12px;
  padding: 8px 12px;
  position: absolute;
  top: 0;
  right: 0;
}



.addtxt{
  padding-top: 10px;
  padding-bottom: 10px;
  text-align: center;
  font-size: 13px;
  width: 350px;
  background-color: #e5e8ed;
  font-weight: 500;
}
.form-control:focus{
  color: #000;
}
.second{
  width: 350px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 5px 5px 5px #aaaaaa;
}
.text1{
  font-size: 15px;
  font-weight: 500;
  color: #56575b;
}
.text2{
  font-size: 13px;
  font-weight: 500;
  margin-left: 6px;
  color: #56575b;
}
.text3{
  font-size: 13px;
  font-weight: 500;
  margin-right: 4px;
  color: #828386;
}
.text3o{
  color: #00a5f4;

}
.text4{
  font-size: 13px;
  font-weight: 500;
  color: #828386;
}
.text4i{
  color: #00a5f4;
}
.text4o{
  color: white;
}
.thumbup{
  font-size: 13px;
  font-weight: 500;
  margin-right: 5px;
}
.thumbupo{
  color: #17a2b8;
}
</style>