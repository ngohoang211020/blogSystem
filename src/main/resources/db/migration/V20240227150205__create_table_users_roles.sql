set search_path to blogsystem;

create table if not exists users_roles
(
    user_id    uuid,
    role_id    uuid,
    created_at timestamptz not null,
    primary key (user_id, role_id)
);
