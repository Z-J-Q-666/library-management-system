# 图书馆管理系统（Library Management System / LMS）

JAVA EE 框架技术课程大作业 · 5 人项目组

---

## 一、环境要求

| 项 | 版本 | 说明 |
|---|---|---|
| JDK | 17 | 必须 17，不能用 21 编译（课程要求写死 17） |
| Maven | 3.9+ | IDEA 自带即可 |
| MySQL | 8.0 | 字符集 utf8mb4 |
| Node | 18+ | 只给前端用，后端不依赖 |
| Redis | 5.0+ | 仅第三周做缓存时用，前两周不需要装 |
| 端口 | 后端 8081 / 前端 5173 | 默认避开 8080（常被别的项目占用），要改只动 `application.yml` 一行 |

**前端技术栈固定为 Vue 3 + Vite + Element Plus**，全组统一，细节见 `frontend/README.md`。

## 二、建库脚本位置

```
sql/
└── 01-create-database.sql   # 建库 + 建账号（先执行这个）
```

`schema.sql`（建表）与 `data.sql`（初始化数据）**由 E 在第一批次提供**，尚未提交。
五张主表的字段定义见《需求规格说明书》第 6.2 节，E 按那份写 DDL，其他人在 E 提交前不要各自建表。

E 提交后的执行顺序：`01-create-database.sql` → `schema.sql` → `data.sql`。

```bash
mysql -uroot -p123456 < sql/01-create-database.sql
mysql -uroot -p123456 lms < sql/schema.sql     # 等 E 提交后才能执行
mysql -uroot -p123456 lms < sql/data.sql       # 等 E 提交后才能执行
```

也可以直接在 IDEA 的 Database 面板里，右键 SQL 文件选择 Run。

## 三、初始化配置

后端配置文件在 `backend/src/main/resources/`：

| 文件 | 用途 | 是否提交 |
|---|---|---|
| `application.yml` | 公共配置（端口、MyBatis、Jackson、JWT 密钥） | 提交 |
| `application-dev.yml` | 本地开发配置（数据库连接、缓存方式） | 提交，密码是课程用的弱口令 |
| `application-prod.yml` | 生产配置，全部走环境变量 | 提交，**不含任何明文密码** |

如果你本机的 MySQL 密码不是 `123456`，不要改 `application-dev.yml`（会影响别人），
新建一个 `application-local.yml` 覆盖即可，这个文件已在 `.gitignore` 里。

```yaml
# backend/src/main/resources/application-local.yml
spring:
  datasource:
    password: 你的密码
```

然后在 IDEA 的 Run Configuration 里把 Active profiles 设为 `dev,local`。

## 四、启动方式

**方式一：IDEA**

1. `File → Open`，选 `backend` 目录最省事；直接开仓库根目录也行，IDEA 会自动识别
   `backend/pom.xml` 并导入为 Maven 模块。第一次等依赖下载，约 3~5 分钟。
2. 确认 `Project Structure → Project SDK` 是 JDK 17。
3. 打开 `com.lms.LmsApplication`，点左侧绿色三角运行。
4. 控制台出现 `Started LmsApplication in x.x seconds` 即启动成功。

**方式二：命令行**

```bash
cd backend
mvn spring-boot:run
```

**当前工程里没有任何接口**，访问任何 `/api/**` 都会 404，这是正常的——公共底座与登录接口由
C、B 在第一批次实现，第一批接口写出来之后才会有可访问的路径。

**前端（Vue 3 + Vite）** 目前只有目录与约定，工程由 D 在第二周用 `npm create vite` 初始化，
启动方式、代理配置、目录规划都写在 `frontend/README.md`，这里不重复。

## 五、默认账号密码

| 账号 | 密码 | 角色 | 说明 |
|---|---|---|---|
| admin | 123456 | ADMIN | 馆员，全模块读写 |
| reader01 | 123456 | READER | 读者，只读 + 查本人数据 |

> 这是**约定的初始账号**，由 E 写进 `sql/data.sql`（密码存 BCrypt 密文），
> 由 B 在登录接口里按 `t_sys_user` 校验。当前登录接口尚未实现，这批账号暂时用不了。
> 密码规则确定后如果改了，要同步回来改这张表。

## 五之二、常见启动问题

**端口被占用**：报 `Port 8081 was already in use`。改 `application.yml` 里的 `server.port`，
或者运行参数加 `--server.port=8082`。前端代理地址要同步改。

**连接数据库失败**：先确认 `mysql -uroot -p123456 -e "select 1"` 能连通，
再确认 `lms` 库存在（执行过 `sql/01-create-database.sql`）。

**控制台出现 `No MyBatis mapper was found in '[com.lms]'`**：这是正常的，
第一个 Mapper 接口写出来后这条告警就消失。

## 六、目录结构

```
backend/src/main/java/com/lms/
├── LmsApplication.java          启动类
├── common/                      公共底座                    负责人：C
├── security/                    登录与鉴权                  负责人：B
├── borrow/                      M1 借阅                     负责人：C
├── reader/                      M2 读者                     负责人：B
├── book/                        M3 图书（含缓存）           负责人：A
├── reservation/                 M4 预约                     负责人：D
└── category/                    M5 分类                     负责人：E
```

`common/` 与 `security/` **目前各只有一个 `package-info.java`，没有任何实现代码**，
由 C、B 在第一批次从零写。两个包的 `package-info.java` 里写清了各自要放哪五块、
契约出处（《需求规格说明书》第 7 章）、以及 401 与 403 分别用拦截器和切面的原因。

| 包 | 负责人 | 必须提供的东西 |
|---|---|---|
| `common/` | C | 统一响应体、状态码枚举、业务异常、全局异常处理、分页入参与返回、MyBatis-Plus 分页插件、CORS 配置 |
| `security/` | B | 令牌签发与解析、登录拦截（401）、`@RequireRole` 与切面（403）、当前登录人上下文、登录接口 |

五个业务模块的包已经在仓库里，每个包里有一个 `package-info.java` 说明本模块的分层约定和负责人。
新建类时按下面的结构放：

```
borrow/                       # 以借阅模块为例
├── controller/   接收请求、参数校验、返回统一响应体
├── service/      业务逻辑、事务边界（@Transactional 加在这一层）
├── mapper/       数据访问接口（记得加 @Mapper）
├── dto/          入参与出参对象，加 JSR-303 注解
└── entity/       与表一一对应的实体
```

## 七、当前进度

| 批次 | 状态 | 内容 |
|---|---|---|
| 骨架 | ✅ 已完成 | **只搭架子**：工程结构、依赖、配置分档、启动类、模块包、SQL 建库脚本、Git 规范 |
| 第一批次 | ⏳ 进行中 | **C 从零写**统一响应/错误码/全局异常/分页；**B 从零写**登录与鉴权；**E 从零写** schema 与初始化数据；A 建仓库分支 |
| 第二批次 | 待开始 | 五人并行开发各自模块 |

工程里没有任何业务代码，也没有公共底座——这是**故意**的：公共底座是 C 与 B 第一批次的交付物，
写完之后才有 `/api/**` 可访问，才有统一响应结构可用。谁写的谁能在答辩上讲清楚。

`package-info.java` 里有各模块的分层约定与负责人，新建类前先看一眼。

## 八、Git 分支与推送规范

> 提交规范（分支、提交信息、合并、红线）的独立文档见根目录 `CONTRIBUTING.md`。

仓库：`https://gitee.com/jiaqi-the-visionary/library-management-system.git`（私有，教师已加为协作者）

### 8.1 分支模型

| 分支 | 谁能推 | 说明 |
|---|---|---|
| `main` | 任何人**不能直接推**，只能通过 PR 合并 | 永远可运行，答辩用这个分支 |
| `feature/book` | A | M3 图书 + 缓存 |
| `feature/reader` | B | M2 读者 |
| `feature/borrow` | C | M1 借阅 |
| `feature/reservation` | D | M4 预约 + 前端 |
| `feature/category` | E | M5 分类 |

一个人只推自己那条 `feature/`，不要推到别人的分支上。

**分支名规则**：用小写英文 + 斜杠，不带空格、不带中文。`dev - 张三` 这种写法 Git 直接拒绝（分支名不允许空格）。
如果以后有人要按人命名，只能写成 `dev/zhangsan`。本项目已定按模块命名，不再改。

**不设 `dev` 汇总分支**。五个人、四周，多一层 `feature → dev → main` 就多一次同步和一轮冲突要处理；
`main` 本身就是集成分支，谁的 PR 通过谁合。

### 8.2 日常流程

```bash
# 1. 开工前同步主干
git checkout main && git pull
git checkout feature/book          # 换成自己那条
git rebase main                    # 把主干新提交垫到自己下面

# 2. 写代码、提交
git add -A
git commit -m "feat(book): 图书列表分页与分类筛选"

# 3. 推自己的分支（首次加 -u）
git push -u origin feature/book
```

做完一个可验收的功能，在 Gitee 上向 `main` 提 **Pull Request**。仓库里有 PR 模板
（`.gitee/PULL_REQUEST_TEMPLATE.md`），提的时候会带出来，按它填就不会漏。

**谁来审**

| 改动范围 | 找谁 |
|---|---|
| 自己模块的包 | 自己确认，组长抽查 |
| `common/` | C（统一响应、异常、分页都在这，一改全组要重测） |
| `security/` | B（鉴权、令牌） |
| `pom.xml`、`application*.yml` | A（影响所有人的构建与启动） |
| `sql/` | E（建表脚本只有一份，不能各自改） |

**合并前必须过的四道关**

1. 已 `rebase origin/main`，Gitee 上显示"可自动合并"，没有冲突；
2. 本地 `mvn clean package` 通过，应用能启动；
3. 代码里引用的别人的类，在远程确实存在（不是"我本地有"，而是"main 上有"）；
4. 没有夹带 `target/`、`.idea/`、`application-local.yml`、临时测试类。

任何一条没过就别点合并。宁可让 PR 挂着，也不要让 `main` 变成跑不起来的状态——
`main` 是答辩演示用的分支。

### 8.3 提交信息格式

`类型(范围): 做了什么`

| 类型 | 用于 |
|---|---|
| `feat` | 新增功能或接口 |
| `fix` | 修 bug |
| `refactor` | 重构，行为不变 |
| `docs` | 文档、README、SQL 注释 |
| `chore` | 配置、依赖、构建 |

例：`feat(borrow): 归还接口 + 库存回补事务`、`fix(auth): 修正读者越权未拦截`。
**禁止** `update`、`修改`、`提交`、`fix bug` 这类信息——报告里要附提交记录截图，这些字眼一看就是凑数。

### 8.4 六条禁令

1. 不直接推 `main`。
2. 不对 `main` 使用 `git push --force`。自己的 `feature/` 分支 rebase 后需要强推，用 `--force-with-lease`。
3. 不提交 `target/`、`node_modules/`、`.idea/`、`application-local.yml`（`.gitignore` 已覆盖，别用 `git add -f` 绕过）。
4. 不提交任何真实密码、密钥、个人 token。
5. 不把整个模块攒成一次提交。每人**不少于 5 次提交**，且集中在自己的模块与兼责范围内。
6. 改 `common/` 或 `security/` 前先在群里说一声，这两处一改，五个人的接口都要重测。

### 8.5 冲突怎么处理

`git rebase main` 报冲突时，别慌也别直接 `--abort` 了事：

1. 冲突文件里搜 `<<<<<<<`，逐段决定留谁。
2. `git add <文件>` 然后 `git rebase --continue`。
3. 拿不准的冲突，找另一边的负责人一起看 5 分钟，比事后查半天强。
4. 解决完先本地跑一次 `mvn spring-boot:run`，能起来再推。

### 8.6 怎么合并到 main

**只走 PR，不在网页上直接改 `main`。**

Gitee 页面：`Pull Requests → 新建 Pull Request` → 源分支选 `feature/xxx`，目标分支选 `main`。

#### 合并方式怎么选

Gitee 新建 PR 时可以选三种，**统一用「变基（Rebase）」**：

| 方式 | 用不用 | 原因 |
|---|---|---|
| 变基（Rebase） | ✅ 用这个 | `main` 保持一条直线，每个人的提交清晰罗列，报告附截图最清楚 |
| 合并（Merge commit） | ❌ 不用 | 每合一次多一个 `Merge branch...` 节点，五次之后历史变成一团 |
| 压缩（Squash） | ❌ 禁用 | 会把 8 个提交压成 1 个，**「每人至少 5 次提交」这条要求直接作废** |

#### 合并顺序（按依赖，不能乱）

第一批次的三块必须按这个顺序进 `main`，后面的人才能开工：

| 顺序 | 谁合什么 | 为什么排这里 |
|---|---|---|
| 1 | E：`sql/schema.sql` + `data.sql` | 纯 SQL，不依赖任何 Java 代码；别人要先能建库 |
| 2 | C：`common/`（Result、错误码、全局异常、分页） | 所有接口的返回格式都靠它，不先进 `main` 别人没法写 Controller |
| 3 | B：`security/`（令牌、拦截器、切面、登录接口） | 依赖 C 的 Result，且越权测试要等它 |
| 4 | 五人各自的模块 | 以上三块就绪后，谁先做完谁先合，互相之间不阻塞 |
| 5 | D：前端 `lms-web` | 依赖全部接口，最后合 |

#### 合并之后必须做的事

**每次有人合并，其他人都要拉一次：**

```bash
git checkout feature/xxx
git fetch origin
git rebase origin/main
```

不拉就会出现「我的分支里没有 A 写的 BookMapper，编译不过」。

#### 提示「存在冲突」怎么办

PR 页面显示冲突时**不能合并**，按这个顺序处理：

```bash
git checkout feature/xxx
git fetch origin
git rebase origin/main          # 这里会报冲突
# 打开冲突文件，搜 <<<<<<< 逐段决定留谁
git add <冲突文件>
git rebase --continue
mvn -q compile                  # 确认还能编译
git push --force-with-lease origin feature/xxx
```

推完刷新 PR 页面，冲突提示会自动消失。**不要**在网页上点「解决冲突」然后手写代码，
网页编辑器没有语法检查，很容易把别人的方法删掉。
