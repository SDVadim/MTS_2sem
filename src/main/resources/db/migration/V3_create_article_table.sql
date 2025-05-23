CREATE TABLE IF NOT EXISTS articles (
      article_id BIGSERIAL PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      url VARCHAR(255) NOT NULL,
      category_id BIGINT NOT NULL,
      CONSTRAINT delete_category FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
);
