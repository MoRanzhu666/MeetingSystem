-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS mts_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mts_db;

-- 1. 预约信息表（存储会见预约记录）
CREATE TABLE IF NOT EXISTS appointments (
                                            id BIGINT AUTO_INCREMENT COMMENT '主键ID',
                                            prisoner_id VARCHAR(20) NOT NULL COMMENT '服刑人员编号',
    prisoner_name VARCHAR(50) NOT NULL COMMENT '服刑人员姓名',
    visitor_name VARCHAR(50) NOT NULL COMMENT '会见人姓名',
    visitor_id VARCHAR(20) NOT NULL COMMENT '会见人身份证号',
    relation VARCHAR(20) NOT NULL COMMENT '与服刑人员关系（配偶/父母/子女等）',
    phone VARCHAR(20) NOT NULL COMMENT '会见人联系电话',
    appointment_date DATE NOT NULL COMMENT '预约日期',
    appointment_time VARCHAR(20) NOT NULL COMMENT '预约时段（如：09:00-10:00）',
    status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '预约状态（pending:待审核, approved:已批准, rejected:已拒绝, completed:已完成, canceled:已取消）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    -- 索引：优化查询效率
    INDEX idx_visitor_id (visitor_id),
    INDEX idx_prisoner_id (prisoner_id),
    INDEX idx_appointment_date (appointment_date),
    INDEX idx_status (status),
    -- 唯一约束：同一服刑人员+会见人+日期只能预约一次
    UNIQUE KEY uk_duplicate_check (prisoner_id, visitor_id, appointment_date)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会见预约信息表';

-- 2. 管理员表（系统管理员账户）
CREATE TABLE IF NOT EXISTS admins (
                                      id BIGINT AUTO_INCREMENT COMMENT '主键ID',
                                      username VARCHAR(50) NOT NULL COMMENT '管理员用户名',
    password VARCHAR(100) NOT NULL COMMENT '管理员密码（建议加密存储）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username) COMMENT '用户名唯一'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理员表';

-- 初始化默认管理员（密码为明文"admin123"，生产环境需替换为加密后的值）
INSERT INTO admins (username, password)
VALUES ('admin', 'admin123')
    ON DUPLICATE KEY UPDATE username = username; -- 避免重复插入
