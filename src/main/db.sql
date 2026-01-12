create database if not exists oj_database;
use oj_database;
drop table if exists oj_table;
create table oj_table(
    id int primary key auto_increment,
    title varchar(50),
    level varchar(50),
    description varchar(4096),
    templateCode varchar(4096),
    testCode varchar(4096)
);
drop table if exists oj_user;
create table oj_user(
    id int primary key auto_increment,
    username varchar(50) unique,
    password varchar(50) not null,
    isAdmin int
);
CREATE TABLE submission (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT NOT NULL,
                            problem_id INT NOT NULL,
                            contest_id INT DEFAULT NULL COMMENT '非比赛提交为空',
                            code TEXT NOT NULL,
                            language ENUM('C', 'C++', 'Java', 'Python', 'JavaScript') NOT NULL,
                            status ENUM(
        'Pending', 'Compiling', 'Running',
        'Accepted', 'Wrong Answer', 'Time Limit Exceeded',
        'Memory Limit Exceeded', 'Compile Error', 'Runtime Error'
    ) DEFAULT 'Pending',
                            time_used INT DEFAULT 0 COMMENT 'ms',
                            memory_used INT DEFAULT 0 COMMENT 'KB',
                            submit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            judge_message TEXT,
                            INDEX idx_user (user_id),
                            INDEX idx_problem (problem_id),
                            INDEX idx_contest (contest_id),
                            INDEX idx_submit_time (submit_time),
                            FOREIGN KEY (user_id) REFERENCES oj_user(id) ON DELETE CASCADE,
                            FOREIGN KEY (problem_id) REFERENCES oj_table(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE contest (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(100) NOT NULL,
                         description TEXT,
                         start_time DATETIME NOT NULL,
                         end_time DATETIME NOT NULL,
                         creator_id INT NOT NULL,
                         is_public BOOLEAN DEFAULT true,
                         password VARCHAR(50) DEFAULT NULL,
                         INDEX idx_time (start_time, end_time),
                         INDEX idx_creator (creator_id),
                         FOREIGN KEY (creator_id) REFERENCES oj_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE contest_problem (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 contest_id INT NOT NULL,
                                 problem_id INT NOT NULL,
                                 problem_order CHAR(2) NOT NULL COMMENT 'A,B,C...',
                                 UNIQUE KEY uniq_contest_problem (contest_id, problem_id),
                                 INDEX idx_contest (contest_id),
                                 INDEX idx_problem (problem_id),
                                 FOREIGN KEY (contest_id) REFERENCES contest(id) ON DELETE CASCADE,
                                 FOREIGN KEY (problem_id) REFERENCES oj_table(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE contest_participant (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     contest_id INT NOT NULL,
                                     user_id INT NOT NULL,
                                     join_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     UNIQUE KEY uniq_participation (contest_id, user_id),
                                     INDEX idx_contest (contest_id),
                                     INDEX idx_user (user_id),
                                     FOREIGN KEY (contest_id) REFERENCES contest(id) ON DELETE CASCADE,
                                     FOREIGN KEY (user_id) REFERENCES oj_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE testcase (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          problem_id INT NOT NULL,
                          input TEXT NOT NULL,
                          output TEXT NOT NULL,
                          is_sample BOOLEAN DEFAULT false,
                          score INT DEFAULT 10 COMMENT '该测试点分值',
                          INDEX idx_problem (problem_id),
                          FOREIGN KEY (problem_id) REFERENCES oj_table(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE discussion (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            problem_id INT DEFAULT NULL COMMENT '全局讨论可为空',
                            user_id INT NOT NULL,
                            content TEXT NOT NULL,
                            parent_id INT DEFAULT 0 COMMENT '0表示主帖',
                            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            INDEX idx_problem (problem_id),
                            INDEX idx_parent (parent_id),
                            INDEX idx_create_time (create_time),
                            FOREIGN KEY (problem_id) REFERENCES oj_table(id) ON DELETE SET NULL,
                            FOREIGN KEY (user_id) REFERENCES oj_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;