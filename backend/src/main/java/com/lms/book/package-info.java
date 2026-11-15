/**
 * M3 图书管理 —— 书目与库存、入库与下架、图书详情缓存
 *
 * <p>负责人：A。本包内统一分层，各层职责如下：</p>
 * <ul>
 *   <li>{@code controller/} —— 接收 HTTP 请求，@Valid 校验入参，返回 Result</li>
 *   <li>{@code service/} —— 业务规则与事务边界；@Cacheable 加在详情查询上</li>
 *   <li>{@code mapper/} —— 数据访问，接口上加 @Mapper</li>
 *   <li>{@code dto/} —— 入参出参对象，JSR-303 注解写在这里</li>
 *   <li>{@code entity/} —— 与 t_book 一一对应</li>
 *   <li>{@code cache/} —— BookCacheService，负责 book:detail:{id} 的失效，供 C、D 调用</li>
 * </ul>
 *
 * <p>接口路径用名词复数，例如 {@code /api/book}。列表要带出其他模块的字段，不要只返回 id。</p>
 */
package com.lms.book;
