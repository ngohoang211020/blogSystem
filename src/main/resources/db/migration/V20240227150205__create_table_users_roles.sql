set search_path to blogsystem;

create table user_role
(
    user_role_id uuid primary key default gen_random_uuid(),
    user_id      uuid not null,
    role_id      uuid not null,
    created_at   timestamptz      default now()
);
