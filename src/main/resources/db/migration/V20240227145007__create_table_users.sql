set search_path to blogsystem;
-- The citext extension is not installed by default in PostgreSQL. citext data type is a case-insensitive text type.
CREATE EXTENSION IF NOT EXISTS citext;

create table if not exists users
(
    user_id    uuid primary key     default gen_random_uuid(),
    email      citext      not null unique,
    username   varchar(127),
    password   varchar(255),
    full_name  varchar(255),
    status     integer     not null default 0,
    created_at timestamptz not null,
    updated_at timestamptz
);

create index if not exists users_email_idx on users using btree (email);

