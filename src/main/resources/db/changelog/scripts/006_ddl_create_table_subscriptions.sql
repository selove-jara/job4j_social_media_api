CREATE TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    follower_id INT REFERENCES users(id),
    following_id INT REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    is_subscriber BOOLEAN NOT NULL DEFAULT FALSE,
    is_friend BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (follower_id, following_id)
);