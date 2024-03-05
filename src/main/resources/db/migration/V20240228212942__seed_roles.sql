set search_path to blogsystem;

-- Role
insert into role(code, name, description, status, created_at)
values('USER', 'User', 'User', 1, now());
insert into role(code, name, description, status, created_at)
values('ADMIN', 'Admin', 'Admin', 1, now());
insert into role(code, name, description, status, created_at)
values('SUPER_ADMIN', 'Super Admin', 'Super Admin', 1, now());

