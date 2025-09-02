<script setup lang="ts">
import { ref } from 'vue'
import GlobalHeader from './GlobalHeader.vue'
import GlobalFooter from './GlobalFooter.vue'

// 菜单配置，可以根据需要扩展
const menuItems = ref([
  { key: 'home', title: '首页', path: '/' },
  { key: 'about', title: '关于', path: '/about' }
])
</script>

<template>
  <a-layout class="layout-container">
    <!-- 顶部导航栏 -->
    <a-layout-header class="layout-header">
      <GlobalHeader :menu-items="menuItems" />
    </a-layout-header>
    
    <!-- 内容区域 -->
    <a-layout-content class="layout-content">
      <div class="content-container">
        <router-view />
      </div>
    </a-layout-content>
    
    <!-- 底部版权信息 -->
    <a-layout-footer class="layout-footer">
      <GlobalFooter />
    </a-layout-footer>
  </a-layout>
</template>

<style scoped>
.layout-container {
  min-height: 100vh;
}

.layout-header {
  padding: 0;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  position: fixed;
  width: 100%;
  z-index: 1000;
}

.layout-content {
  padding: 0 50px;
  margin-top: 64px; /* 顶部导航栏高度 */
}

.content-container {
  background: #fff;
  padding: 24px;
  min-height: calc(100vh - 64px - 70px); /* 减去头部和底部高度 */
}

.layout-footer {
  text-align: center;
  background: #f0f2f5;
  padding: 24px 50px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .layout-content {
    padding: 0 20px;
  }
  
  .content-container {
    padding: 16px;
  }
  
  .layout-footer {
    padding: 16px 20px;
  }
}
</style>