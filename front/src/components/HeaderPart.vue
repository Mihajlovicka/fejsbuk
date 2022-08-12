<template>
<header>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Lazni fjesbuk</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <div class="navbar-nav mr-auto">

      </div>
      <div class="form-inline my-2 my-lg-0" >
        <a class="nav-link" href="#">Registracija</a>
        <router-link to="/login">Prijava</router-link>
      </div>
    </div>
  </nav>
</header>
</template>

<script>
export default {
  name: "HeaderPart"
}
</script>

<style scoped>

</style>