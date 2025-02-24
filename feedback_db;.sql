CREATE DATABASE feedback_db;
USE feedback_db;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role ENUM('user', 'admin') DEFAULT 'user'
);

CREATE TABLE feedback (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    message TEXT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);