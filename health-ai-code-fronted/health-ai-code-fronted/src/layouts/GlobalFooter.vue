<script setup lang="ts">
// 无需额外逻辑
</script>

<template>
  <div class="global-footer">
    <div class="copyright">
      © {{ new Date().getFullYear() }} 天王星数字科技有限公司
    </div>
  </div>
</template>

<style scoped>
.global-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.copyright {
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
}
</style>