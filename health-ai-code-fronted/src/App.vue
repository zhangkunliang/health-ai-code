<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
</script>

<template>
  <BasicLayout />
</template>

<style>
/* 全局样式优化 */
:root {
  --primary-color: #3b82f6;
  --primary-dark: #2563eb;
  --primary-light: #60a5fa;
  --secondary-color: #1e293b;
  --secondary-light: #334155;
  --accent-color: #8b5cf6;
  --accent-dark: #7c3aed;
  --text-color: #1e293b;
  --text-light: #64748b;
  --background-light: #f8fafc;
  --background-dark: #0f172a;
  --success-color: #10b981;
  --warning-color: #f59e0b;
  --error-color: #ef4444;
  --gradient-primary: linear-gradient(135deg, var(--primary-color), var(--accent-color));
  --gradient-dark: linear-gradient(135deg, var(--secondary-color), var(--background-dark));
}

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
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
  color: var(--text-color);
  background-color: var(--background-light);
  line-height: 1.5;
}

html {
  overflow-x: hidden;
  font-size: 16px;
}

/* 确保图片不会溢出容器 */
img {
  max-width: 100%;
  height: auto;
  display: block;
}

/* 确保文本不会溢出容器 */
h1, h2, h3, h4, h5, h6, p, span, div {
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
}

/* 按钮样式统一 */
.ant-btn-primary {
  background: var(--gradient-primary) !important;
  border-color: var(--primary-color) !important;
  box-shadow: 0 2px 6px rgba(59, 130, 246, 0.3) !important;
  transition: all 0.3s ease !important;
}

.ant-btn-primary:hover, .ant-btn-primary:focus {
  background: linear-gradient(135deg, var(--primary-dark), var(--accent-dark)) !important;
  border-color: var(--primary-dark) !important;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4) !important;
  transform: translateY(-1px) !important;
}

/* 卡片样式优化 */
.ant-card {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.08);
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
  border: 1px solid rgba(226, 232, 240, 0.8);
  backdrop-filter: blur(8px);
}

.ant-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 12px 20px rgba(15, 23, 42, 0.12);
  border-color: rgba(59, 130, 246, 0.3);
}

.ant-card-head {
  border-bottom: 1px solid rgba(226, 232, 240, 0.8);
  background: linear-gradient(to right, rgba(248, 250, 252, 0.8), rgba(241, 245, 249, 0.8));
}

.ant-card-head-title {
  font-weight: 600;
  color: var(--secondary-color);
}

/* 输入框和表单元素样式优化 */
.ant-input, .ant-select-selector, .ant-picker {
  border-radius: 8px !important;
  border: 1px solid rgba(203, 213, 225, 0.8) !important;
  transition: all 0.3s ease !important;
  box-shadow: 0 1px 2px rgba(15, 23, 42, 0.05) !important;
}

.ant-input:hover, .ant-select-selector:hover, .ant-picker:hover {
  border-color: var(--primary-light) !important;
}

.ant-input:focus, .ant-select-selector:focus, .ant-picker:focus,
.ant-input-focused, .ant-select-focused, .ant-picker-focused {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2) !important;
}

/* 滚动条美化 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: rgba(241, 245, 249, 0.8);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(to bottom, var(--primary-light), var(--primary-color));
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(to bottom, var(--primary-color), var(--primary-dark));
}

/* 响应式布局辅助 */
@media (max-width: 768px) {
  html {
    font-size: 14px;
  }
  
  .hide-on-mobile {
    display: none !important;
  }
}
</style>
