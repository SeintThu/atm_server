
drop  TABLE IF  EXISTS Users;

CREATE TABLE IF NOT EXISTS Users  (
                       user_id INT PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       address VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP,
                           updated_at TIMESTAMP,
                           deleted_at TIMESTAMP
);