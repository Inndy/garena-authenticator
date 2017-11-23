<template lang="pug">
div
  h2 這是什麼？

  p
    a(href="https://www.garena.tw/") Garena
    span 出的驗證 App 實在是太難用，所以寫一個網頁版。

  h2 用你的驗證器安全嗎？

  p
    span 我們不會在伺服器上儲存任何使用者的資料，程式原始碼在
    a(href="https://github.com/Inndy/garena-authenticator") GitHub
    span 上面可以找到。

  h2 你怎麼知道驗證碼計算方式？

  p.strike 這個不好說。

  p
    | 原理是基於一組 16 字的英數混合密鑰，以及時間（180秒爲單位），來計算出一組驗證碼。
    | 並且 Garena 採用的是一種叫做 HOTP 的公開標準，就算是猜到的應該也不奇怪（？）
</template>

<style lang="sass">
.strike
  text-decoration: line-through
</style>
