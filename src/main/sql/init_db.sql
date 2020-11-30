DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(2000) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

DROP TABLE IF EXISTS public.product_category;
CREATE TABLE public.product_category (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(2000) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product (
    id SERIAL NOT NULL PRIMARY KEY,
    product_category_id INTEGER REFERENCES product_category(id),
    supplier_id INTEGER REFERENCES supplier(id),
    name VARCHAR(2000),
    price FLOAT,
    currency VARCHAR(2000) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES public.product_category(id),
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES public.supplier(id);

