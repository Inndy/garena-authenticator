<template lang="pug">
#home.center
  div
    h3 輸入16位密鑰：
    input.center(type="text", v-model="key", placeholder="XXXXYYYY55556666", maxlength="16")
  div
    button(@click="start", :disabled="verified") 現在就開始使用
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

<style lang="sass">
#home
  padding-top: 3rem

  input
    width: 18rem
    max-width: 100%
    font-size: 1.6rem
    font-family: 'Source Code Pro', Consolas, monospace
    margin-bottom: 1.2em

  button
    width: 18rem
    font-size: 2rem
</style>
