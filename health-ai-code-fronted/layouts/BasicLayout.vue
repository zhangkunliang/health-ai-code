<template>
  <a-layout class="basic-layout">
    <!-- 顶部导航栏 -->
    <GlobalHeader />
    <!-- 主要内容区域 -->
    <a-layout-content class="main-content">
      <router-view />
    </a-layout-content>
    <!-- 底部版权信息 -->
    <GlobalFooter />
  </a-layout>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
import GlobalFooter from '@/components/GlobalFooter.vue'
</script>

<style scoped>
.basic-layout {
  background: #f5f5f5;
  min-height: 100vh;
}

.main-content {
  width: 100%;
  max-width: 1200px;
  padding: 24px;
  background: #fff;
  margin: 24px auto;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  flex: 1;
}

@media (max-width: 768px) {
  .main-content {
    padding: 12px;
    margin: 12px auto;
    border-radius: 0;
  }
}
</style>