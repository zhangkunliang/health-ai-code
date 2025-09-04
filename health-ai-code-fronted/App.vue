<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
</script>

<template>
  <BasicLayout />
</template>

<style>
/* 全局样式优化 */
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
  font-family:
    -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell',
    'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  overflow-x: hidden;
}

html {
  overflow-x: hidden;
}

/* 确保图片不会溢出容器 */
img {
  max-width: 100%;
  height: auto;
  object-fit: contain;
}

/* 文字内容不会被截断 */
p, h1, h2, h3, h4, h5, h6, div, span {
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
}

/* 响应式设计优化 */
@media (max-width: 768px) {
  body {
    font-size: 14px;
  }
  
  h1 {
    font-size: 24px;
  }
  
  h2 {
    font-size: 20px;
  }
  
  h3 {
    font-size: 18px;
  }
}
</style>