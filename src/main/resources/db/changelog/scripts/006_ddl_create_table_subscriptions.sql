CREATE TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    follower_id INT REFERENCES users(id),
    following_id INT REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);