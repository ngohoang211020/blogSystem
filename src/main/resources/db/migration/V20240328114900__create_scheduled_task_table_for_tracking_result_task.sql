create schema scheduled;
set search_path to scheduled;
create table scheduled_task
(
    name        varchar(255) primary key,
    type        int not null,
    start_at    timestamptz not null,
    ended_at    timestamptz not null,
    completed   bool      default false, -- 0 -> inactive, 1 -> active
    success     bool      default false, -- 0 -> inactive, 1 -> active
    stack_trace text
);
