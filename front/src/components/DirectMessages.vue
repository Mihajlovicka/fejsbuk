<template>
  <div class="event-schedule-area-two bg-color pad100">
    <div class="container">
      <div class="row">
        <div class="col-4">
          <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade active show" id="home" role="tabpanel">
              <div class="table-responsive">
                <table class="table">
                  <tbody>
                  <tr class="inner-box" v-for="friend in friends" :key="friend">
                    <td>
                      <div class="event-img">
                        <!--                <img v-if="images[user.username] != '' && images.hasOwnProperty(user.username)" :src="images[user.username]" alt=""/>-->
                        <!--                <img v-if="images[user.username] == '' || !images.hasOwnProperty(user.username)" :src="images['no_image.jpg']" alt=""/>-->
                      </div>
                    </td>
                    <td>
                      <div class="event-wrap">
                        <h3>{{ friend.name }} {{ friend.surname }}</h3>

                      </div>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="col-8">
          <label>Enter text:</label><input type="text" id="text"/>
          <input type="button" id="send" value="send" @click="send"/>

          <p></p>

          <div id="chatLog" ></div>


        </div>
        <!-- /col end-->
      </div>
      <!-- /row end-->
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DirectMessages",
  data(){
    return {
      soket:null,
      logged_username: '',
      user:Object,
      messages:Object,
      friends:[],
    }
  },
  methods:{
    message(msg){
      console.log(msg);
      document.getElementById('chatLog').innerHTML += (msg+'</p>');
    },
    send(){
      var text = document.getElementById("text").value;

      if(text==""){
        this.message('<p>Unesite poruku');
        return ;
      }
      try{
        this.socket.send(text);
        this.message('<p>Poslato: '+text);
      } catch(exception){
        this.message('<p>Greska: ' + exception);
      }
    }
  },
  created() {
    axios.get('/getLoggedInUser').then(resp => {
      this.user = resp.data
      this.logged_username = resp.data.username;
      for(let k of this.user.friendships) {
        axios.get('/getUser',{params: {username: k}}).then(resp => {
          this.friends.push(resp.data)
        })
      }
    })
    // axios.get('/getMessages').then(resp => {
    //   this.messages = resp.data
    // })


      try{
        this.socket = new WebSocket("ws://localhost:8080/ws" + "?name=saraaaaaaaaaaaaaa");
        this.message('<p>connect: Socket Status: '+this.socket.readyState);

        this.socket.onopen = function(){
          this.message('<p>onopen: Socket Status: '+this.socket.readyState+' (open)');
        }

        this.socket.onmessage = function(msg){
          this.message('<p>onmessage: Received: '+msg.data);
        }

        this.socket.onclose = function(){
          this.message('<p>onclose: Socket Status: '+this.socket.readyState+' (Closed)');
          this.socket = null;
        }

      } catch(exception){
        this.message('<p>Greska'+exception);
      }

  }

}
</script>

<style scoped>
.input-group{
  padding: 10px;
  float: left;
  justify-content: right;
}
.input-group input{
  margin: 10px 30px 10px 0px;
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