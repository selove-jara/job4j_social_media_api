CREATE TABLE post
(
    id              serial primary key,
    title           varchar not null,
    description     varchar not null,
    created         TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    users_id        int references users(id)  not null
);