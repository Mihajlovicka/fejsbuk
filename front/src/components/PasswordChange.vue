<template>
  <div class="wrapper">
    <form @submit.prevent="saveInfo" novalidate>
      <div class="h5 font-weight-bold text-center mb-3">Izmena lozinke</div>
      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.passwords.old_one.$error }">
        <input class="form-control" type="password" placeholder="Stara lozinka" v-model="passwords.old_one"
               @input="$v.passwords.old_one.$touch()"/>
        <div class="error-message" v-if="!$v.passwords.old_one.required">Molimo popunite</div>
      </div>

      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.passwords.new_one.$error }">
        <input type="password" class="form-control" placeholder="Nova lozinka" v-model="passwords.new_one"
               @input="$v.passwords.new_one.$touch()">
        <div class="error-message" v-if="!$v.passwords.new_one.required">Molimo popunite</div>
      </div>
      <div class="form-group d-flex align-items-center" :class="{ 'error': $v.passwords.new_two.$error }">
        <input type="password" class="form-control" placeholder="Nova lozinka ponov" v-model="passwords.new_two"
               @input="$v.passwords.new_two.$touch()">
        <div class="error-message" v-if="!$v.passwords.new_two.required">Molimo popunite</div>
        <div class="error-message" v-if="!$v.passwords.new_two.sameAsPassword">Lozinke nisu iste</div>
      </div>
      <button class="btn btn-primary mb-3" type="submit" @submit.prevent="saveInfo">Izmeni</button>


    </form>
  </div>
</template>

<script>
import axios from "axios";
import {validationMixin} from "vuelidate";
import {required, sameAs} from "vuelidate/lib/validators";


export default {
  name: "ProfileInfoChange",
  mixins: [validationMixin],
  validations: {
    passwords: {
      old_one: {
        required,
      },
      new_two: {
        required,
        sameAsPassword: sameAs('new_one')
      },
      new_one: {
        required,
      }
    }
  },
  data() {
    return {
      username: this.$route.params.username,
      passwords: {
        old_one: "",
        new_two: "",
        new_one: "",
      },
    }
    },
    methods:{
      saveInfo()
      {
        this.$v.passwords.$touch();
        if (this.$v.passwords.$error) return;

        axios.post('/changePassword', {
          new_password: this.passwords.new_one,
          old_password: this.passwords.old_one
        }).then(resp => {
          alert(resp.response.data.succes)
          this.$router.push({name: 'userProfile', params: {username: this.username}})
        })
            .catch(resp => {
              alert(resp.response.data.error)
            })
      }
    }
  ,
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
  color: red;
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