
create table if not exists users
(
    id BIGSERIAL,
    email VARCHAR,
    password VARCHAR,
    role VARCHAR,
    CONSTRAINT pk_users primary key (id)
);

create table if not exists tokens
(
    id BIGSERIAL,
    access_token VARCHAR,
    logged_out BOOLEAN,
    user_id BIGINT,
    CONSTRAINT pk_tokens primary key (id),
    CONSTRAINT fk_token_user_id  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

