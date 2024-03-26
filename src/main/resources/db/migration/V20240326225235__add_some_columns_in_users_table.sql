set search_path to blogsystem;

alter table users
    add column phone_number varchar(128) not null default '';
alter table users
    add column profile_picture varchar(128) not null default '';
