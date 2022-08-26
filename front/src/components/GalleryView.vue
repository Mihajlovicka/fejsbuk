<template>
  <div>
    <div class="row">
      <div class="container">
        <div class="row">
          <div class="column" v-for="i in images" v-bind:key="i">
            <img class="hover-shadow" style="width: 100%; height: 100%" :src="i" alt=""
                 v-on:click="openModal();currentSlide(i)"/>
          </div>
        </div>
      </div>
    </div>
    <div id="myModal" class="modal">
      <span class="close cursor" @click="closeModal()">&times;</span>
      <div class="modal-content">

        <div class="mySlides" v-for="i in images" v-bind:key="i">
          <img :src="i" style="max-width:600px; height: 70vh;">
        </div>

        <!-- Next/previous controls -->
        <a class="prev" @click="plusSlides(-1)">&#10094;</a>
        <a class="next" @click="plusSlides(1)">&#10095;</a>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "GalleryView",
  data() {
    return {
      images: [],
      slideIndex: 1,
    }
  },
  methods: {
    openModal() {
      document.getElementById("myModal").style.display = "block";
    }, closeModal() {
      document.getElementById("myModal").style.display = "none";
    }, currentSlide(img) {
      let n = 0;
      for (let i = 0; i < this.images.length; i++) {
        if (this.images[i] == img) n = i + 1;
      }
      this.showSlides(this.slideIndex = n);
    }, plusSlides(n) {
      this.showSlides(this.slideIndex += n);
    },
    showSlides(n) {
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
        this.images.push(require("../assets/pictures/" + img_name))
      }
    }
  },
  created() {
    this.getPictures()
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

</style>