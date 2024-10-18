CREATE TABLE users
(
    id          serial primary key,
    full_name   VARCHAR not null,
    email       varchar unique not null,
    password    varchar not null,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);