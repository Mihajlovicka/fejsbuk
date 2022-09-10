<template>
<div>
  <label>Enter text:</label><input type="text" id="text"/>
  <input type="button" id="send" value="send" @click="send"/>

  <p></p>

  <div id="chatLog" ></div>


</div>
</template>

<script>
import axios from "axios";

export default {
  name: "PrivateMessages",
  data(){
    return{
      receiver:this.$route.params.receiver,
      sender:this.$route.params.sender
    }
  },
  methods:{
    send(){
      var text = document.getElementById("text").value;

      if(text==""){
        return ;
      }
      try{
        this.socket.send(text);
      } catch(exception){
        document.getElementById('chatLog').innerHTML += ('<p>Greska: ' + exception+'</p>');
      }
    }
  },
  created() {
    axios.get('/getMessages',{params: {sender:this.sender, receiver:this.receiver}}).then(resp => {
      this.messages = resp.data
      console.log(this.messages)
      for(let m of this.messages){
        document.getElementById('chatLog').innerHTML += ('<p>'+ m.sender + ': ' + m.content+'</p>');
      }
    })


    try{
      this.socket = new WebSocket("ws://localhost:8080/ws?sender=" + this.sender + "&receiver="  + this.receiver);
      // this.message('<p>connect: Socket Status: '+this.socket.readyState);

      // this.socket.onopen = function(){
      //   this.message('<p>onopen: Socket Status: '+this.socket.readyState+' (open)');
      // }

      this.socket.onmessage = function(msg){
        document.getElementById('chatLog').innerHTML += ('<p>' + msg.data +'</p>');
      }

      // this.socket.onclose = function(){
      //   this.message('<p>onclose: Socket Status: '+this.socket.readyState+' (Closed)');
      //   this.socket = null;
      // }

    } catch(exception){
      document.getElementById('chatLog').innerHTML += ('<p>Greska: ' + exception+'</p>');
    }

  }
}
</script>

<style scoped>

</style>