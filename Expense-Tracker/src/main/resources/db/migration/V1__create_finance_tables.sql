CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
                            id UUID PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            user_id UUID NOT NULL,
                            CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT uk_categories_name_user UNIQUE (name, user_id)
);

CREATE TABLE transactions (
                              id UUID PRIMARY KEY,
                              description VARCHAR(255) NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL,
                              date DATE NOT NULL,
                              type VARCHAR(20) NOT NULL,
                              category_id UUID,
                              user_id UUID NOT NULL,
                              CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
                              CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id)
);