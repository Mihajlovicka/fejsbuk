<template>
  <div id="app">
    <HeaderPart/>
    <router-view></router-view>
  </div>
</template>

<script>
import HeaderPart from './components/HeaderPart.vue'
// import LogIn from "@/components/LogIn";

export default {
  name: 'App',
  components: {
    HeaderPart,
    // LogIn
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
