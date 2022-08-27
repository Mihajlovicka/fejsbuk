<template>
  <div id="app">
    <HeaderPart :key="headerComponent"/>
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
import HeaderPart from './components/HeaderPart.vue'

export default {
  name: 'App',
  components: {
    HeaderPart,
  },
  data() {
    return {
      headerComponent: 0,
      pageComponent:1,
    };
  },
  methods: {
    forceRerenderHeader() {
      this.headerComponent += 2;
    },
    forceRerenderPage() {
      this.$route.fullPath += 2;
    },
    search(search){
      this.$router.push({name:'userSearch', params:{headerSearch:search}})
    },
  },
  created() {
    this.forceRerenderPage()
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
