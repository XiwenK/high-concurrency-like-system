-- User
create table if not exists `user`
(
    id       bigint auto_increment primary key,
    username varchar(128) not null
);

-- Blog
create table if not exists blog
(
    id         bigint auto_increment primary key,
    userId     bigint                             not null,
    title      varchar(512)                       null,
    coverImg   varchar(1024)                      null,
    content    text                               not null,
    thumbCount int      default 0                 not null,
    createTime datetime default CURRENT_TIMESTAMP not null,
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);
create index idx_userId on blog (userId);

-- Like record
create table if not exists thumb
(
    id         bigint auto_increment primary key,
    userId     bigint                             not null,
    blogId     bigint                             not null,
    createTime datetime default CURRENT_TIMESTAMP not null
);
create unique index idx_userId_blogId on thumb (userId, blogId);

