# 前端工程 lms-web

**负责人：D**。技术栈全组统一，已由分工定死：**Vue 3 + Vite + Element Plus**。

课程不看前端质量，但"前端质量不计分"不等于可以各写各的——五个模块的页面必须长一个样，
否则报告"系统实现"那一章的截图会很难看，答辩演示切页面也会跳。

## 一、初始化（由 D 执行一次）

在 `frontend/` 目录下：

```bash
npm create vite@latest . -- --template vue
npm install
npm install element-plus axios vue-router pinia
npm run dev
```

Node 用 18 以上。国内下载慢就配一下镜像：

```bash
npm config set registry https://registry.npmmirror.com
```

## 二、目标目录结构

脚手架生成后按这个结构整理，五个人往 `views/<模块>/` 里放自己的页面。

```
frontend/
├── index.html
├── package.json
├── vite.config.js          # 代理 /api 到后端，见第三节
├── .env.development        # VITE_API_BASE=/api
├── .env.production
└── src/
    ├── main.js             # 挂载 router / pinia / element-plus
    ├── App.vue
    ├── router/
    │   └── index.js        # 路由表 + 全局前置守卫（D）
    ├── stores/
    │   └── user.js         # 令牌与当前用户（D）
    ├── utils/
    │   └── request.js      # Axios 封装：注入令牌、401 跳登录（D）
    ├── layout/
    │   └── BasicLayout.vue # 侧边栏 + 顶栏 + 面包屑（D）
    ├── components/         # 通用表格、表单、分页组件（D）
    ├── api/                # 按模块拆：book.js / borrow.js / ...
    └── views/
        ├── login/Login.vue           # 登录页（D）
        ├── book/                     # M3 图书（A）
        ├── reader/                   # M2 读者（B）
        ├── borrow/                   # M1 借阅（C）
        ├── reservation/              # M4 预约（D）
        └── category/                 # M5 分类（E）
```

**约定**：`api/` 下按模块拆文件，谁写哪个模块的页面就用谁的同名文件；
不要把所有请求塞进一个 `api.js`，五个人同时改会天天冲突。

## 三、联调代理

后端跑在 **8081**（不是 8080，那台机器上常有别的项目占着）。在 `vite.config.js` 里配代理，
避免跨域，也不用改后端的 CORS：

```js
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    }
  }
})
```

## 四、鉴权约定

1. 登录成功后令牌存在 `localStorage`，字段名 `lms_token`。
2. 每个请求由 Axios 请求拦截器自动加 `Authorization: Bearer <token>`。
3. 响应拦截器遇到 `code === 401` 时清空令牌并跳 `/login`，不要在每个页面里重复判断。
4. 路由表的 `meta.roles` 声明哪些角色可进，全局前置守卫据此拦人。
   **这只是体验优化**，真正的权限判断在后端，前端拦不住的请求后端会返回 403。

## 五、给五个人的两条硬要求

1. 页面用 `layout/BasicLayout.vue` 做壳，不要新开一套侧边栏。
2. 列表页用 `components/` 里的通用表格与分页组件，不要自己写一套分页器。
