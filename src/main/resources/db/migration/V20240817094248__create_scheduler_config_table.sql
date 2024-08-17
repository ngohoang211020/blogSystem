set search_path to blogsystem;

create table SCHEDULER_CONFIG
(
  id serial primary key,
  key varchar(100) not null ,
  value varchar(100) not null,
  status int default 0
);

insert into SCHEDULER_CONFIG (key,value,status) values ('test.task.1','* * * ? * *',1),('test.task.2','* * * ? * *',1);
