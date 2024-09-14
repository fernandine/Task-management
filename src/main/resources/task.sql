CREATE DATABASE IF NOT EXISTS task;
USE task;

CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tb_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    completed BOOLEAN,
    highlighted BOOLEAN,
    priority INT,
    user_id BIGINT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(id)
);

INSERT INTO tb_user (name) VALUES ('Alice');
INSERT INTO tb_user (name) VALUES ('Bob');
INSERT INTO tb_user (name) VALUES ('Charlie');

INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES ('Task 1', false, 5, 1, 1);
INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES ('Task 2', true, 4, 1, 1);
INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES ('Task 3', false, 3, 1, 2);
INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES ('Task 4', true, 2, 1, 3);
INSERT INTO tb_task (name, completed, priority, highlighted, user_id) VALUES ('Task 5', false, 1, 1, 3);