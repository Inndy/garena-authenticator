<template>
  <div id="app">
    <header class="center">
      <h1>Garena 驗證器</h1>
    </header>

    <nav class="center">
      <ul>
        <li>
          <router-link to="/">首頁</router-link>
        </li>
        <li>
          <router-link to="/about">關於</router-link>
        </li>
      </ul>
    </nav>

    <router-view></router-view>
  </div>
</template>

<script>
export default {
}
</script>

<style lang="scss">
html {
  background-color: #eee;
}

#app {
  font-family: 'Avenir', Helvetica, 'Noto Sans CJK TC', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  padding: 0 .75rem;
}

a {
  color: #0f7db7;
}

nav ul {
  padding: 0;

  li {
    list-style: none;
    display: inline-block;
    padding: 0;

    a { // router-link
      font-size: 1.25rem;
      padding: .5rem 2rem;
      color: #0f337d;
      text-decoration: none;
    }
  }
}

.center {
  text-align: center;
}
</style>
