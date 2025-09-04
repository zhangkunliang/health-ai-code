<template>
  <a-layout-footer class="footer">
    <div class="footer-content">
      <p class="copyright">
        <a
          target="_blank"
          rel="noopener noreferrer"
          class="author-link"
        >
          天王星数字科技有限公司
        </a>
      </p>
    </div>
  </a-layout-footer>
</template>

<script setup lang="ts">
// 无需额外的响应式数据
</script>

<style scoped>
.footer {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  text-align: center;
  padding: 20px;
  margin-top: 40px;
  border-top: 1px solid rgba(102, 126, 234, 0.1);
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);
  min-height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-content {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.copyright {
  margin: 0;
  color: #666;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.author-link {
  color: #666;
  text-decoration: none;
  transition: color 0.3s;
}

.author-link:hover {
  color: #1890ff;
}

@media (max-width: 768px) {
  .footer {
    padding: 15px 10px;
  }

  .copyright {
    font-size: 12px;
  }
}
</style>
