set schema 'public';

create index category_index_name on public.category (name);
create index customer_index_document on public.customer (document);
create index product_index_name on public.product (name);