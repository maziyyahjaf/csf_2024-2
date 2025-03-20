-- TODO Task 3
DROP DATABASE IF EXISTS emart2;
CREATE DATABASE emart2;
USE emart2;

CREATE TABLE orders (
    order_id CHAR(26) NOT NULL,
    order_date DATE NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    ship_address VARCHAR(500) NOT NULL,
    priority BOOLEAN NOT NULL DEFAULT FALSE,
    comments VARCHAR(1000),

    CONSTRAINT pk_order_id PRIMARY KEY (order_id)

);

CREATE TABLE line_items(
    id INT AUTO_INCREMENT NOT NULL,
    product_id VARCHAR(30) NOT NULL,
    product_name VARCHAR(150) NOT NULL,
    quantity INT DEFAULT 1,
    price FLOAT NOT NULL,
    order_id CHAR(26) NOT NULL,

    CONSTRAINT pk_id PRIMARY KEY(id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

grant all privileges on emart2.* to 'fred'@'%';
flush privileges;

