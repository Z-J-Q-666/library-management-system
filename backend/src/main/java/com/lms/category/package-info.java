/**
 * M5 分类管理 —— 分类树、推荐书目、分类下书目
 *
 * <p>负责人：E。本包内统一分层，各层职责如下：</p>
 * <ul>
 *   <li>{@code controller/} —— 接收 HTTP 请求，@Valid 校验入参，返回 Result</li>
 *   <li>{@code service/} —— 业务规则与事务边界；树形结构用 TreeUtils 组</li>
 *   <li>{@code mapper/} —— 数据访问，接口上加 @Mapper</li>
 *   <li>{@code dto/} —— 入参出参对象，JSR-303 注解写在这里</li>
 *   <li>{@code entity/} —— 与 t_category 一一对应</li>
 * </ul>
 *
 * <p>接口路径用名词复数，例如 {@code /api/category}。列表要带出其他模块的字段，不要只返回 id。</p>
 */
package com.lms.category;
