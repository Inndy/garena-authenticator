<template>
  <div class="center" id="home">
    <div>
      <h3>輸入16位密鑰：</h3>
      <input type="text" class="center" v-model="key"
        placeholder="XXXXYYYY55556666" maxlength="16">
    </div>
    <div>
      <button @click="start" :disabled="verified">現在就開始使用</button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      key: ''
    }
  },
  methods: {
    start () {
      this.$router.push(`/authenticator/${this.key}`)
    }
  },
  computed: {
    verified () {
      return !this.key.match(/^[0-9a-zA-Z]{16}$/)
    }
  }
}
</script>

<style lang="scss">
#home {
  padding-top: 3rem;

  input {
    width: 18rem;
    font-size: 1.6rem;
    font-family: 'Source Code Pro', Consolas, monospace;
    margin-bottom: 1.2em;
  }

  button {
    width: 18rem;
    font-size: 2rem;
  }
}
</style>
