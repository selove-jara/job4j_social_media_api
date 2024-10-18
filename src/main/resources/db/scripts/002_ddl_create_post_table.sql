CREATE TABLE post
(
    id              serial primary key,
    title           varchar not null,
    description     varchar not null,
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    users_id        int references users(id)  not null
);