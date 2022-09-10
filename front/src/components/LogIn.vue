<template>
  <div class="forma align-items-center h-75 d-inline-block ">

  <!-- Pills content -->
  <div class="tab-content ">
    <div class="tab-pane fade show active" id="pills-login" role="tabpanel" aria-labelledby="tab-login">
      <form @submit.prevent="login">
        <div v-if="errorMessage !== ''" class="alert alert-danger text-center form-outline mb-4">{{errorMessage}}</div>
        <!-- Email input -->
        <div class="form-outline mb-4">
          <label class="form-label" for="loginName">Korisnicko ime</label>
          <input type="text" id="loginName" class="form-control" v-model="username"/>

        </div>

        <!-- Password input -->
        <div class="form-outline mb-4">
          <label class="form-label" for="loginPassword">Loznika</label>
          <input type="password" id="loginPassword" class="form-control" v-model="password"/>

        </div>

        <!-- 2 column grid layout -->
<!--        <div class="row mb-4">-->
<!--          <div class="col-md-6 d-flex justify-content-center">-->
<!--            &lt;!&ndash; Checkbox &ndash;&gt;-->
<!--            <div class="form-check mb-3 mb-md-0">-->
<!--              <input class="form-check-input" type="checkbox" value="" id="loginCheck" checked />-->
<!--              <label class="form-check-label" for="loginCheck"> Zapamti me </label>-->
<!--            </div>-->
<!--          </div>-->

<!--          <div class="col-md-6 d-flex justify-content-center">-->
<!--            &lt;!&ndash; Simple link &ndash;&gt;-->
<!--            <a href="#!">Zaboravljena lozinka?</a>-->
<!--          </div>-->
<!--        </div>-->

        <!-- Submit button -->
        <button type="submit" class="btn btn-outline-primary btn-block mb-4">Uloguj se</button>

        <!-- Register buttons -->
        <div class="text-center">
          <p>Zelis zabavu? <a href="#!">Registruj se</a></p>
        </div>
      </form>
    </div>
  </div>
  </div>
  <!-- Pills content -->
</template>

<script>
import axios from "axios";
export default {
  name: "LogIn",
  data(){
    return{
      username:"",
      password:"",
      errorMessage:""
    }
  },
  methods:{
    login(){
      axios.post("/login",{username:this.username, password:this.password})
      .then(resp => {
        this.errorMessage = "";
        localStorage.setItem("token",resp.data.token)
        localStorage.setItem("role",resp.data.role)
        if(localStorage.getItem("role") == "admin")
        window.location.assign('/homepage')
        else
          window.location.assign('/profileView/'+resp.data.username);

      }).catch(resp => {
        console.log(resp);
        this.errorMessage = resp.response.data.error;

      })
    }
  }
}
</script>

<style scoped>
.forma{
  margin-top: 100px !important;
}
</style>