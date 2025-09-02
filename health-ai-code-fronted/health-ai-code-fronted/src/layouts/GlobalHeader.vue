<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
// 当前选中菜单
const selectedKeys = ref<string[]>(['/'])
// 监听路由变化，更新当前选中菜单
router.afterEach((to, from, next) => {
  selectedKeys.value = [to.path]
})
// 处理菜单点击
const handleMenuClick: MenuProps['onClick'] = (e) => {
  const key = e.key as string
  selectedKeys.value = [key]
  // 跳转到对应页面
  if (key.startsWith('/')) {
    router.push(key)
  }
}

// 定义props接口
defineProps<{
  menuItems: Array<{ key: string; title: string; path: string }>
}>()

// 登录状态（暂时用登录按钮替代用户头像和昵称）
const isLoggedIn = ref(false)
</script>

<template>
  <div class="global-header">
    <!-- 左侧Logo和标题 -->
    <div class="logo-container">
      <img src="@/assets/logo.png" alt="Logo" class="logo" />
      <h1 class="site-title">智慧医疗 AI 零代码应用生成平台</h1>
    </div>

    <!-- 中间导航菜单 -->
    <div class="menu-container">
      <a-menu mode="horizontal" :selectedKeys="[$route.path]">
        <a-menu-item v-for="item in menuItems" :key="item.path">
          <router-link :to="item.path">{{ item.title }}</router-link>
        </a-menu-item>
      </a-menu>
      <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="horizontal"
        :items="items"
        @click="handleMenuClick"
      />
    </div>

    <!-- 右侧用户信息/登录按钮 -->
    <div class="user-container">
      <template v-if="isLoggedIn">
        <a-dropdown>
          <a class="user-dropdown" @click.prevent>
            <a-avatar size="small" icon="user" />
            <span class="username">用户名</span>
          </a>
          <template #overlay>
            <a-menu>
              <a-menu-item key="profile">个人中心</a-menu-item>
              <a-menu-item key="settings">设置</a-menu-item>
              <a-menu-divider />
              <a-menu-item key="logout">退出登录</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </template>
      <template v-else>
        <a-button type="primary">登录</a-button>
      </template>
    </div>
  </div>
</template>

<style scoped>
.global-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  padding: 0 24px;
  width: 100%;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo {
  height: 32px;
  margin-right: 12px;
}

.site-title {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: rgba(0, 0, 0, 0.85);
  white-space: nowrap;
}

.menu-container {
  flex: 1;
  display: flex;
  justify-content: center;
}

.user-container {
  display: flex;
  align-items: center;
}

.user-dropdown {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 12px;
  color: rgba(0, 0, 0, 0.65);
}

.username {
  margin-left: 8px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .site-title {
    display: none;
  }

  .menu-container {
    justify-content: flex-start;
    margin-left: 20px;
  }
}

@media (max-width: 576px) {
  .global-header {
    padding: 0 12px;
  }

  .menu-container {
    margin-left: 10px;
  }
}
</style>
