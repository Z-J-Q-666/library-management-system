-- ============================================================
-- 01 建库脚本
-- 执行：mysql -uroot -p < sql/01-create-database.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS `lms`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

-- 如果团队成员各用各的账号，可在此建一个公共账号（可选）
-- CREATE USER IF NOT EXISTS 'lms'@'%' IDENTIFIED BY 'lms123456';
-- GRANT ALL PRIVILEGES ON lms.* TO 'lms'@'%';
-- FLUSH PRIVILEGES;

USE `lms`;
