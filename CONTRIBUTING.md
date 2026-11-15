# 提交规范

5 人项目组 · 图书馆管理系统 · V1.0

---

## 一、分支

| 分支 | 谁能推 | 说明 |
|---|---|---|
| `main` | **谁都不能直接推**，只走 PR | 永远可运行，答辩用这条 |
| `feature/book` | A 张嘉奇 | M3 图书 + 缓存 |
| `feature/reader` | B 范展 | M2 读者 |
| `feature/borrow` | C 强心雨 | M1 借阅 |
| `feature/reservation` | D 廖雯洁 | M4 预约 + 前端 |
| `feature/category` | E 岑升旺 | M5 分类 |

只推自己那条，不推到别人分支上。分支名用小写英文加斜杠，**不带空格、不带中文**（`dev - 张三` 这种 Git 会直接拒绝）。

## 二、提交信息

```
类型(范围): 做了什么
```

| 类型 | 用于 |
|---|---|
| `feat` | 新增功能或接口 |
| `fix` | 修 bug |
| `refactor` | 重构，行为不变 |
| `docs` | 文档、README、SQL 注释 |
| `chore` | 配置、依赖、构建 |

```
feat(book): 图书列表分页与分类筛选
feat(borrow): 归还接口 + 库存回补事务
fix(auth): 修正读者越权未被拦截
```

**禁止** `update`、`修改`、`提交`、`fix bug`。报告要附提交记录截图，这些字眼一看就是凑数。

## 三、提交粒度

按"一个可验收的动作"提交，写完一个接口提交一次，不要攒到周末一次提交两千行。

**每人不少于 5 次提交**，且集中在自己的模块与兼责范围内。整组只有组长在提交的，按挂名处理。

## 四、不提交这些

`.gitignore` 已覆盖，不要用 `git add -f` 绕过：

- `backend/target/`、`node_modules/`、`dist/`
- `.idea/`、`*.iml`
- `application-local.yml`
- 真实密码、密钥、token
- 临时文件（`~$*.docx`、`*.log`、临时测试类）

## 五、合并到 main

**合并前必须过的三道关**

1. 已 `git fetch && git rebase origin/main`，PR 页面显示"可合并"而非"存在冲突"；
2. 本地 `mvn clean package` 通过，应用能启动；
3. 没夹带第四节列的那些文件。

**合并方式必须选「变基（Rebase）」**，禁用「压缩（Squash）」——后者会把多个提交压成一个，"每人至少 5 次提交"直接作废。

**谁来点合并**

| 改动范围 | 找谁 |
|---|---|
| 自己模块 | 自己点，群里说一声 |
| `common/` | C |
| `security/` | B |
| `pom.xml`、`application*.yml` | A |
| `sql/` | E |

## 六、红线

1. 不直接推 `main`。
2. 不用 Squash 合并。
3. 不提交真实密码、密钥、token。
4. 不删改别人的代码和表结构。
