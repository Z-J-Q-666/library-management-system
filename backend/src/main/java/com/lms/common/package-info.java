/**
 * 公共底座。负责人：C。
 *
 * <p>本包放全组共用的东西，不放任何业务逻辑。按下面五块组织，建议各自建子包：</p>
 *
 * <ul>
 *   <li>{@code result/} —— 统一响应体、状态码枚举、分页入参与返回结构</li>
 *   <li>{@code exception/} —— 业务异常、全局异常处理器</li>
 *   <li>{@code config/} —— MyBatis-Plus 分页插件、CORS、Jackson 等配置</li>
 *   <li>{@code util/} —— 通用工具（树构建、日期、枚举转换）</li>
 *   <li>{@code cache/} —— 缓存配置与 {@code BookCacheService}（缓存由 A 实现，落在本包便于 C、D 调用）</li>
 * </ul>
 *
 * <p>契约见《需求规格说明书》第 7 章：统一响应结构、九个状态码、路径命名规范、分页规范。
 * 本包一旦成型，五个人所有接口都必须按它返回，不允许各自定格式。</p>
 */
package com.lms.common;
