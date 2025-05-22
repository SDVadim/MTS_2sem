CREATE TABLE IF NOT EXISTS categories (
    category_id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT delete_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);