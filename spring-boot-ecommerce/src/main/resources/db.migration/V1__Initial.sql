CREATE TABLE  product
(
    id SERIAL,
    sku character varying(255),
    name character varying(255),
    description character varying(255),
    unit_price numeric(38,2),
    image_url character varying(255),
    active boolean,
    units_in_stock integer,
    date_created timestamp(6) without time zone,
    last_updated timestamp(6) without time zone,
    category_id integer NOT NULL
);

CREATE TABLE product_category
(
    id SERIAL,
    category_name character varying(255)
);

ALTER TABLE product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
ALTER TABLE product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);
ALTER TABLE product
    ADD CONSTRAINT product_category_fk FOREIGN KEY (category_id) REFERENCES product_category (id);


INSERT INTO product_category(category_name) VALUES ('BOOKS');

INSERT INTO product (active, date_created, description, image_url, name,sku,unit_price, units_in_stock, category_id)
VALUES
    (true,NOW(),'Learn JavaScript','assets/images/products/placeholder.png','JavaScript - The Fun Parts','BOOK-TECH-1000',19.99,100,1),
    (true,NOW(),'Learn Spring','assets/images/products/placeholder.png','Spring Framework Tutorial','BOOK-TECH-1001',29.99,100,1),
    (true,NOW(),'Learn Kubernetes','assets/images/products/placeholder.png','Kubernetes - Deploying Containers','BOOK-TECH-1002',24.99, 100,1),
    (true, NOW(),'Learn IoT','assets/images/products/placeholder.png','Internet of Things (IoT) - Getting Started','BOOK-TECH-1003',29.99,100,1),
    (true,NOW(),'Learn Go','assets/images/products/placeholder.png','The Go Programming Language: A to Z','BOOK-TECH-1004',24.99,100,1);



