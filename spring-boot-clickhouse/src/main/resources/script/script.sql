
create table if not exists t_user (
id String ,
name String,
create_date date
)engine = MergeTree
partition by toYYYYMM(create_date)
order by create_date;

