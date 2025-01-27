create table category
(
    id            serial not null,
    active        boolean default true,
    register_date timestamp,
    updated_date  timestamp,
    description   varchar(255),
    name          varchar(255),
    uuid          uuid,
    primary key (id)
);

create table customer
(
    id            serial not null,
    active        boolean default true,
    register_date timestamp,
    updated_date  timestamp,
    document      varchar(20),
    email         varchar(255),
    name          varchar(255),
    uuid          uuid,
    primary key (id)
);

create table product
(
    id            serial not null,
    active        boolean default true,
    register_date timestamp,
    updated_date  timestamp,
    description   varchar(255),
    name          varchar(255),
    price         numeric(19, 2),
    uuid          uuid,
    id_category   int4   not null,
    primary key (id)
);

alter table customer
    add constraint UK_phlle50dp6ivt0paa1d5gkvk2 unique (document);

alter table product
    add constraint FK5cxv31vuhc7v32omftlxa8k3c
        foreign key (id_category)
            references category;