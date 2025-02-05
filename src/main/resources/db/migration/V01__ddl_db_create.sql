set schema 'public';
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

create table product_image (
   id  serial not null,
   active boolean default true,
   register_date timestamp,
   updated_date timestamp,
   description varchar(255),
   image text,
   name varchar(255),
   uuid uuid,
   id_product int4 not null,
   primary key (id)
);

alter table product_image
    add constraint FKctqn46eat9xvm9qq5wvtwc13d
        foreign key (id_product)
            references product;

create index category_index_name on public.category (name);
create index customer_index_document on public.customer (document);
create index product_index_name on public.product (name);