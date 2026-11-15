/**
 * M1 借阅管理 —— 借阅记录、借出与归还、续借、逾期判定
 *
 * <p>负责人：C。本包内统一分层，各层职责如下：</p>
 * <ul>
 *   <li>{@code controller/} —— 接收 HTTP 请求，@Valid 校验入参，返回 Result</li>
 *   <li>{@code service/} —— 业务规则与事务边界，@Transactional 加在这一层</li>
 *   <li>{@code mapper/} —— 数据访问，接口上加 @Mapper；手写 SQL 写在 resources/mapper/xxx.xml</li>
 *   <li>{@code dto/} —— 入参出参对象，JSR-303 注解写在这里，不要直接暴露 entity</li>
 *   <li>{@code entity/} —— 与 t_borrow 一一对应</li>
 * </ul>
 *
 * <p>接口路径用名词复数，例如 {@code /api/borrow}。列表要带出其他模块的字段，不要只返回 id。</p>
 */
package com.lms.borrow;
