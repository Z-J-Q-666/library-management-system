/**
 * 登录与鉴权。负责人：B。
 *
 * <p>本包只解决两件事：你是谁（认证）、你能不能调这个接口（授权）。
 * 按下面五块组织，建议各自建子包：</p>
 *
 * <ul>
 *   <li>{@code jwt/} —— 令牌签发与解析、登录人对象</li>
 *   <li>{@code interceptor/} —— 请求拦截，缺令牌或令牌失效返回 401</li>
 *   <li>{@code annotation/} —— {@code @RequireRole} 等声明式注解</li>
 *   <li>{@code aspect/} —— 角色校验切面，越权返回 403</li>
 *   <li>{@code config/} —— 拦截器注册与放行白名单</li>
 * </ul>
 *
 * <p>分工口径：401 用拦截器（进 Controller 之前只知道"有没有令牌"），
 * 403 用切面（要知道"这个方法允许谁访问"，得拿到方法签名）。这个区分答辩会问。</p>
 *
 * <p>登录接口本身（{@code POST /api/auth/login}）也归本包，需要查 {@code t_sys_user} 并用 BCrypt 校验。</p>
 */
package com.lms.security;
