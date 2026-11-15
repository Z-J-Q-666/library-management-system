## 改了什么

<!-- 一两句话，写清这次做了什么，不要写"完成了 XX 功能"这种空话 -->

## 怎么测的

<!-- 列出你实际跑过的操作，例如：
1. 启动后端，用 admin 登录拿到令牌
2. 调 GET /api/books?pageNum=1&pageSize=10，返回 10 条
3. 传 pageSize=1000，被截断为 100
-->

## 影响范围

- [ ] 只改了本模块的包
- [ ] 动了 `common/`（需要 C 看过）
- [ ] 动了 `security/`（需要 B 看过）
- [ ] 动了 `pom.xml` / `application*.yml`（需要 A 看过）
- [ ] 动了 `sql/`（需要 E 看过）

## 自查

- [ ] 已 `git rebase origin/main`，Gitee 显示可自动合并
- [ ] 本地 `mvn clean package` 通过
- [ ] 应用能启动，接口能通
- [ ] 没有夹带 `target/`、`.idea/`、`application-local.yml`、临时测试类
- [ ] 提交信息符合 `类型(范围): 描述`
