
create table if not exists files
(
    id BIGSERIAL,
    name VARCHAR,
    type VARCHAR,
    path VARCHAR,
    author VARCHAR,
    creation_date VARCHAR,
    year INTEGER,
    is_base BOOLEAN,
    CONSTRAINT pk_files primary key (id)
);
