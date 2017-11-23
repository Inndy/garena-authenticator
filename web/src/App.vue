<template lang="pug">
#app
  header.center
    h1 Garena 驗證器

  nav.center
    ul
      li
        router-link(to="/") 首頁
      li
        router-link(to="/about") 關於

  router-view
</template>

<script>
export default {}
</script>

<style lang="sass">
html
  background-color: #eee

#app
  font-family: 'Avenir', Helvetica, 'Noto Sans CJK TC', Arial, sans-serif
  -webkit-font-smoothing: antialiased
  -moz-osx-font-smoothing: grayscale
  color: #2c3e50
  padding: 0 .75rem

a
  color: #0f7db7

nav ul
  padding: 0

  li
    list-style: none
    display: inline-block
    padding: 0

    a // router-link
      font-size: 1.25rem
      padding: .5rem 2rem
      color: #0f337d
      text-decoration: none

.center
  text-align: center
</style>
