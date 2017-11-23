<template lang="pug">
#authenticator.center
  p 目前驗證碼： {{otp}}
  p
    button(v-clipboard:copy="otp") 點我複製
  p 有效時間剩餘：{{(expire_in|0)}} 秒
  p.small 你可以把這頁加入書籤，方便日後使用。
</template>

<script>
import speakeasy from 'speakeasy'

const INTERVAL = 180

export default {
  data () {
    return {
      key: this.$route.params.key,
      otp: '------',
      lastInterval: 0,
      expire_in: 0,
      timer: null
    }
  },
  created () {
    this.timer = setInterval(() => this.update(), 111)
  },
  beforeDestroy () {
    clearInterval(this.timer)
  },
  methods: {
    update () {
      this.expire_in = this.nextTime() - this.now()
      if (this.lastInterval === this.currentInterval()) return
      this.lastInterval = this.currentInterval()
      this.otp = speakeasy.hotp({
        secret: this.key,
        counter: this.lastInterval,
        encoding: 'base32'
      })
      this.expire_in = this.nextTime() - this.now()
    },
    now () {
      return new Date() / 1000
    },
    lastTime () {
      return this.lastInterval * INTERVAL
    },
    nextTime () {
      return this.nextInterval() * INTERVAL
    },
    currentInterval () {
      return (this.now() / INTERVAL) | 0
    },
    nextInterval () {
      return this.currentInterval() + 1
    }
  }
}
</script>

<style lang="sass">
#authenticator
  font-size: 1.5rem
  button
    font-size: 2rem
  .small
    font-size: 1rem
</style>
