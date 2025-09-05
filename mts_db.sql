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


-- 初始数据
INSERT into appointments (prisoner_id, prisoner_name, visitor_name, visitor_id, relation, phone, appointment_date, appointment_time, status, create_time) values
                                                                                                                                                              ('P2023001', '张伟', '李娜', '110101199001011234', '配偶', '13812345678', '2024-09-10', '09:30-10:00', 'approved', '2024-09-01 08:20:30'),
                                                                                                                                                              ('P2023002', '李强', '王芳', '110102198503154567', '母亲', '13987654321', '2024-09-10', '10:15-10:45', 'pending', '2024-09-01 09:15:22'),
                                                                                                                                                              ('P2023003', '王芳', '张伟', '110103199205207890', '兄弟', '13712345678', '2024-09-10', '14:00-14:30', 'rejected', '2024-09-01 10:05:11'),
                                                                                                                                                              ('P2023004', '赵伟', '赵敏', '110104198807072345', '姐妹', '13687654321', '2024-09-11', '09:30-10:00', 'completed', '2024-09-01 11:30:45'),
                                                                                                                                                              ('P2023005', '刘强', '刘军', '110105199511115678', '父亲', '13512345678', '2024-09-11', '10:15-10:45', 'approved', '2024-09-01 13:45:12'),
                                                                                                                                                              ('P2023006', '陈静', '陈明', '110106199308158901', '兄弟', '13487654321', '2024-09-11', '14:00-14:30', 'pending', '2024-09-01 15:20:33'),
                                                                                                                                                              ('P2023007', '杨丽', '杨明', '110107198704202345', '父亲', '13312345678', '2024-09-12', '09:30-10:00', 'approved', '2024-09-02 08:10:22'),
                                                                                                                                                              ('P2023008', '黄勇', '黄丽', '110108199109305678', '姐妹', '13287654321', '2024-09-12', '10:15-10:45', 'rejected', '2024-09-02 09:50:15'),
                                                                                                                                                              ('P2023009', '吴敏', '吴强', '110109198902158901', '兄弟', '13112345678', '2024-09-12', '14:00-14:30', 'completed', '2024-09-02 10:30:44'),
                                                                                                                                                              ('P2023010', '周杰', '周丽', '110110199406252345', '姐妹', '13087654321', '2024-09-13', '09:30-10:00', 'pending', '2024-09-02 14:15:33'),
                                                                                                                                                              ('P2023011', '郑华', '郑军', '110111198612105678', '父亲', '18912345678', '2024-09-13', '10:15-10:45', 'approved', '2024-09-03 08:40:22'),
                                                                                                                                                              ('P2023012', '孙丽', '孙明', '110112199003058901', '兄弟', '18887654321', '2024-09-13', '14:00-14:30', 'pending', '2024-09-03 09:25:11'),
                                                                                                                                                              ('P2023013', '马军', '马兰', '110113198807202345', '配偶', '18712345678', '2024-09-14', '09:30-10:00', 'completed', '2024-09-03 11:50:45'),
                                                                                                                                                              ('P2023014', '朱琳', '朱强', '110114199210055678', '兄弟', '18687654321', '2024-09-14', '10:15-10:45', 'rejected', '2024-09-03 13:30:22'),
                                                                                                                                                              ('P2023015', '胡敏', '胡刚', '110115198705158901', '父亲', '18512345678', '2024-09-14', '14:00-14:30', 'approved', '2024-09-03 15:10:11'),
                                                                                                                                                              ('P2023016', '林伟', '林芳', '110116199309302345', '姐妹', '18487654321', '2024-09-15', '09:30-10:00', 'pending', '2024-09-04 08:25:33'),
                                                                                                                                                              ('P2023017', '郭静', '郭军', '110117198901205678', '父亲', '18312345678', '2024-09-15', '10:15-10:45', 'approved', '2024-09-04 09:40:22'),
                                                                                                                                                              ('P2023018', '何勇', '何丽', '110118199106058901', '姐妹', '18287654321', '2024-09-15', '14:00-14:30', 'rejected', '2024-09-04 10:55:11'),
                                                                                                                                                              ('P2023019', '高敏', '高强', '110119198611152345', '兄弟', '18112345678', '2024-09-16', '09:30-10:00', 'completed', '2024-09-04 14:20:33'),
                                                                                                                                                              ('P2023020', '罗军', '罗丽', '110120199403255678', '姐妹', '18087654321', '2024-09-16', '10:15-10:45', 'pending', '2024-09-04 15:40:22');

create table categoies(
    id int primary key  auto_increment,
    category varchar(20) not null ,
    describe_info varchar(20) not null
);
insert into categories values(default, 'relation', '配偶'),
                            (default, 'relation', '父母'),
                            (default, 'relation', '子女'),
                            (default, 'relation', '兄弟'),
                            (default, 'relation', '姐妹'),
                            (default, 'relation', '其他亲属'),
                            (default, 'relation', '朋友');
insert into categories values(default, 'time_scope', '09:30-10:00'),
                            (default, 'time_scope', '10:15-10:45'),
                            (default, 'time_scope', '14:00-14:30'),
                            (default, 'time_scope', '15:00-15:30'),
                            (default, 'time_scope', '16:00-16:30');
