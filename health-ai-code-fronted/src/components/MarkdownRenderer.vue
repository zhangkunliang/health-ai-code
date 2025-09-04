<template>
  <div class="markdown-content" v-html="renderedMarkdown"></div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'

// 引入代码高亮样式
import 'highlight.js/styles/github.css'

interface Props {
  content: string
}

const props = defineProps<Props>()

// 配置 markdown-it 实例
const md: MarkdownIt = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
  highlight: function (str: string, lang: string): string {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return (
          '<pre class="hljs"><code>' +
          hljs.highlight(str, { language: lang, ignoreIllegals: true }).value +
          '</code></pre>'
        )
      } catch {
        // 忽略错误，使用默认处理
      }
    }

    return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>'
  },
})

// 计算渲染后的 Markdown
const renderedMarkdown = computed(() => {
  return md.render(props.content)
})
</script>

<style scoped>
.markdown-content {
  line-height: 1.7;
  color: var(--text-color);
  word-wrap: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
  font-size: 1rem;
}

/* 全局样式，影响 v-html 内容 */
.markdown-content :deep(h1),
.markdown-content :deep(h2),
.markdown-content :deep(h3),
.markdown-content :deep(h4),
.markdown-content :deep(h5),
.markdown-content :deep(h6) {
  margin: 1.5em 0 0.8em 0;
  font-weight: 600;
  line-height: 1.3;
  color: var(--secondary-color);
  overflow-wrap: break-word;
  word-wrap: break-word;
  hyphens: auto;
}

.markdown-content :deep(h1) {
  font-size: 1.8em;
  border-bottom: 1px solid rgba(79, 209, 197, 0.2);
  padding-bottom: 0.3em;
}

.markdown-content :deep(h2) {
  font-size: 1.5em;
  border-bottom: 1px solid rgba(79, 209, 197, 0.2);
  padding-bottom: 0.3em;
}

.markdown-content :deep(h3) {
  font-size: 1.3em;
}

.markdown-content :deep(h4) {
  font-size: 1.1em;
}

.markdown-content :deep(p) {
  margin: 1em 0;
  line-height: 1.7;
  overflow-wrap: break-word;
  word-wrap: break-word;
}

.markdown-content :deep(ul),
.markdown-content :deep(ol) {
  margin: 1em 0;
  padding-left: 1.8em;
}

.markdown-content :deep(li) {
  margin: 0.5em 0;
  line-height: 1.6;
}

.markdown-content :deep(blockquote) {
  margin: 1.2em 0;
  padding: 0.8em 1.2em;
  border-left: 4px solid var(--primary-color);
  background-color: rgba(79, 209, 197, 0.05);
  border-radius: 0 4px 4px 0;
  color: #666;
}

.markdown-content :deep(code) {
  background-color: rgba(79, 209, 197, 0.1);
  padding: 0.2em 0.4em;
  border-radius: 3px;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 0.9em;
  color: var(--secondary-color);
}

.markdown-content :deep(pre) {
  background-color: #f8f8f8;
  border: 1px solid rgba(79, 209, 197, 0.2);
  border-radius: 8px;
  padding: 1em;
  overflow-x: auto;
  margin: 1.2em 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.markdown-content :deep(pre code) {
  background-color: transparent;
  padding: 0;
  border-radius: 0;
  font-size: 0.9em;
  line-height: 1.5;
  display: block;
  overflow-x: auto;
}

.markdown-content :deep(table) {
  border-collapse: collapse;
  margin: 1.2em 0;
  width: 100%;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.markdown-content :deep(table th),
.markdown-content :deep(table td) {
  border: 1px solid rgba(79, 209, 197, 0.2);
  padding: 0.7em 1em;
  text-align: left;
}

.markdown-content :deep(table th) {
  background-color: rgba(26, 54, 93, 0.05);
  font-weight: 600;
  color: var(--secondary-color);
}

.markdown-content :deep(table tr:nth-child(even)) {
  background-color: rgba(79, 209, 197, 0.03);
}

.markdown-content :deep(a) {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s ease;
}

.markdown-content :deep(a:hover) {
  color: var(--primary-dark);
  text-decoration: underline;
}

.markdown-content :deep(img) {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin: 1em 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: block;
}

.markdown-content :deep(hr) {
  border: none;
  border-top: 1px solid rgba(79, 209, 197, 0.2);
  margin: 2em 0;
}

/* 代码高亮样式优化 */
.markdown-content :deep(.hljs) {
  background-color: #f8f8f8 !important;
  border-radius: 6px;
  font-family: 'Monaco', 'Menlo', 'Ubuntu Mono', monospace;
  font-size: 0.9em;
  line-height: 1.5;
  padding: 1em;
}

/* 特定语言的代码块样式 */
.markdown-content :deep(.hljs-keyword) {
  color: #2a4365;
  font-weight: 600;
}

.markdown-content :deep(.hljs-string) {
  color: #319795;
}

.markdown-content :deep(.hljs-comment) {
  color: #718096;
  font-style: italic;
}

.markdown-content :deep(.hljs-number) {
  color: #4fd1c5;
}

.markdown-content :deep(.hljs-function) {
  color: #6f42c1;
}

.markdown-content :deep(.hljs-tag) {
  color: #22863a;
}

.markdown-content :deep(.hljs-attr) {
  color: #6f42c1;
}

.markdown-content :deep(.hljs-title) {
  color: #6f42c1;
  font-weight: 600;
}
</style>
