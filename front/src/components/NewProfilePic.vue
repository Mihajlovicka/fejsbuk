<template>
  <div class="container">
    <div class="row">
      <div class="column" v-for="(i,k) in images" v-bind:key="k">
        <img class="hover-shadow" style="width: 100%; height: 100%" :src="i" alt=""
             v-on:click="choosePicture(k)"/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "NewProfilePic",
  data() {
    return {
      username:this.$route.params.username,
      images: {},
    }
  },
  methods: {
    choosePicture(i) {
      if(confirm("da li ste sigurni da zelite da promenite profilnu sliku?")){
          axios.post('/changeProfilePhoto',i).then( () => {
            this.$router.push({name:'userProfile',params: {username: this.username}})
          }).catch(resp => {
            alert(resp.response.data.error)
          })
      }
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


img.hover-shadow {
  transition: 0.3s;
}

.hover-shadow:hover {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}

</style>