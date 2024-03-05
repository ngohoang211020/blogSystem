set search_path to blogsystem;

insert into users(email, username,full_name,password, status,created_at)
values ('hoanggg2110@gmail.com', 'hoanggg2110@gmail.com', 'Ngo Hoang', '',
        1, now()),
       ('nsikhoa@gmail.com', 'nsikhoa@gmail.com', 'Nguyen Dinh Si Khoa', '',
        1, now()),
       ('publicuser1@gmail.com', 'publicuser@gmail.com', 'User Public', '',
        1, now()),
       ('publicuser2@gmail.com', 'publicuser@gmail.com', 'User Public', '',
        1, now()),
       ('publicuser3@gmail.com', 'publicuser@gmail.com', 'User Public', '',
        1, now()),
       ('admin123@gmail.com', 'admin123@gmail.com', 'Admin Pro', '',
        1, now());

create or replace procedure insert_user_role()
    language plpgsql
as
$$
declare
    uid uuid;
    rid uuid;
begin

    select r.role_id into rid from role r where r.code = 'USER';
    for uid in (select u.user_id from users u where u.email like '%publicuser%')
        loop
            insert into user_role (user_id, role_id) values (uid, rid);
        end loop;


    select r.role_id into rid from role r where r.code = 'ADMIN';
    for uid in (select u.user_id from users u where u.email like '%admin123%')
        loop
            insert into user_role (user_id, role_id) values (uid, rid);
        end loop;


    select r.role_id into rid from role r where r.code = 'SUPER_ADMIN';
    for uid in (select u.user_id from users u where u.email in ('nsikhoa@gmail.com','hoanggg2110@gmail.com'))
        loop
            insert into user_role (user_id, role_id) values (uid, rid);
        end loop;

end;
$$;

call insert_user_role();