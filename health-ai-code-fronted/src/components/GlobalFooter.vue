<template>
  <a-layout-footer class="footer">
    <div class="footer-content">
      <p class="copyright">
        <a
          target="_blank"
          rel="noopener noreferrer"
          class="author-link"
        >
          © 2025 天王星数字科技有限公司
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
  background: var(--gradient-dark);
  text-align: center;
  padding: 24px;
  margin-top: 40px;
  border-top: 1px solid rgba(59, 130, 246, 0.3);
  color: #ffffff;
  box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.1);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
}

.copyright {
  margin: 0;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

.author-link {
  color: var(--primary-light);
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
  padding: 4px 8px;
  border-radius: 4px;
}

.author-link:hover {
  color: #ffffff;
  background-color: rgba(59, 130, 246, 0.2);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}
</style>
