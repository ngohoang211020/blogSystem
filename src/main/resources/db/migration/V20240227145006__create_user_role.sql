set search_path to blogsystem;

create extension if not exists pgcrypto;

create table role
(
    role_id     uuid primary key default gen_random_uuid(),
    code        varchar(255) not null,
    name        varchar(255) not null,
    description text,
    status      integer      not null, -- 0 -> inactive, 1 -> active
    created_at  timestamptz  not null,
    updated_at  timestamptz,
    unique (code)
);