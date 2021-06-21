create extension if not exists "uuid-ossp";

create table if not exists articles
(
    id           uuid primary key not null default uuid_generate_v4(),
    author_id    uuid             not null,
    title        varchar          not null,
    slug         varchar unique   not null,
    src_markdown text             not null,
    posted_on    timestamp        not null,
    edited_on    timestamp                 default null
);

create table if not exists organizations
(
    id   uuid primary key not null default uuid_generate_v4(),
    name varchar          not null,
    slug varchar unique   not null
)
