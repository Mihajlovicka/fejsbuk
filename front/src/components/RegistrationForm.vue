<template>
  <div class="wrapper">
    <form @submit.prevent="saveInfo" novalidate>
      <div class="h5 font-weight-bold text-center mb-3">Registracija</div>
      <div class="form-group d-flex align-items-center"  :class="{ 'error': $v.user.name.$error }">
        <input class="form-control" type="text" placeholder="Ime" v-model="user.name" @input="$v.user.name.$touch()"/>
        <div class="error-message" v-if="!$v.user.name.required">Molimo popunite</div>
      </div>

      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.user.surname.$error }">
        <input type="text" class="form-control" placeholder="Prezime" v-model="user.surname" @input="$v.user.surname.$touch()">
        <div class="error-message" v-if="!$v.user.surname.required">Molimo popunite</div>
      </div>
      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.user.email.$error }">
        <input type="email" class="form-control" placeholder="user@gmail.com" v-model="user.email"  @input="$v.user.email.$touch()">
        <div class="error-message" v-if="!$v.user.email.required">Molimo popunite</div>
        <div class="error-message" v-if="!$v.user.email.email">Format nije ispravan</div>
      </div>
      <div class="form-group d-flex align-items-center">
        <label class="col-4">Datum rodjenja:</label>
        <input type="date" class="form-control col-8" placeholder="Dan rodjenja" v-model="birthDate" >
      </div>
      <div >
        <div class="form-row">
          <div class="form-group col-md-6">
            <label for="inputSex">Pol</label>
            <select id="inputSex" class="form-control" v-model="user.sex">
              <option value="m" selected>Muski</option>
              <option value="w">Zenski</option>
              <option value="n">Neopredeljen</option>
            </select>
          </div>
          <div class="form-group col-md-6">
            <label for="inputVisibility">Vidljivost profila</label>
            <select id="inputVisibility" class="form-control" v-model="profileVisibility">
              <option value="public" selected>Javan</option>
              <option value="private">Privatan</option>
            </select>
          </div>
        </div>
      </div>
      <div class="form-group d-flex align-items-center"  :class="{ 'error': $v.user.username.$error }">
        <input class="form-control" type="text" placeholder="Ime" v-model="user.username" @input="$v.user.username.$touch()"/>
        <div class="error-message" v-if="!$v.user.username.required">Molimo popunite</div>
      </div>
      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.passwords.new_one.$error }">
        <input type="password" class="form-control" placeholder="Lozinka" v-model="passwords.new_one"
               @input="$v.passwords.new_one.$touch()">
        <div class="error-message" v-if="!$v.passwords.new_one.required">Molimo popunite</div>
      </div>
      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.passwords.new_two.$error }">
        <input type="password" class="form-control" placeholder="Ponovite lozinku" v-model="passwords.new_two"
               @input="$v.passwords.new_two.$touch()">
        <div class="error-message" v-if="!$v.passwords.new_two.required">Molimo popunite</div>
        <div class="error-message" v-if="!$v.passwords.new_two.sameAsPassword">Lozinke nisu iste</div>
      </div>

      <button class="btn btn-primary mb-3" type="submit" @submit.prevent="saveInfo">Registruj se</button>


    </form>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import { validationMixin } from "vuelidate";
import {required, email, sameAs} from "vuelidate/lib/validators";


export default {
  name: "ProfileInfoChange",
  mixins: [validationMixin],
  validations: {
    passwords: {
      new_two: {
        required,
        sameAsPassword: sameAs('new_one')
      },
      new_one: {
        required,
      }
    },
    user: {
      name: {
        required,
      },
      username: {
        required,
      },
      surname: {
        required,
      },
      email: {
        required,
        email
      }
    }},
  data() {
    return {
      passwords: {
        new_two: "",
        new_one: "",
      },

      user:{name:"", sex:"n"},
      profileVisibility:'public',
      birthDate:'',
    }
  },
  methods:{

    login(user){
      axios.post("/login",{username:user.username, password:user.password})
          .then(resp => {
            this.errorMessage = "";
            localStorage.setItem("token",resp.data.token)
            localStorage.setItem("role",resp.data.role)
            this.$router.push({name:'userProfile',params: {username: user.username}})
          }).catch(resp => {
        this.errorMessage = resp.response.data.error;
      })
    },
    saveInfo(){
      this.$v.user.$touch();
      if (this.$v.user.$error) return;
      this.$v.passwords.$touch();
      if (this.$v.passwords.$error) return;
      this.user.password = this.passwords.new_one;
      this.user.profilePrivate = this.profileVisibility == "private" ? true : false
      this.user.birthDate = moment(this.birthDate,"yyyy-MM-DD").format('DD-MM-yyyy')
      axios.post('/register',this.user).then(resp => {
        this.user = resp.data;
        alert("Uspesna registracija. logovanje...");
        localStorage.setItem("token",resp.data.token)
        localStorage.setItem("role",resp.data.role)

      })
          .catch( resp => {
            alert(resp.response.data.error)
          })
      window.location = "http://localhost:8082/userProfile/" + this.user.username;
    }
  },

}
</script>

<style scoped>
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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  min-height: 100vh;
  background: linear-gradient(to bottom, #000428, #004683);
}

.wrapper {
  max-width: 500px;
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