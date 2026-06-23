CREATE TABLE IF NOT EXISTS department (
 code varchar(10) NOT NULL,
 name varchar(20) NOT NULL,
 CONSTRAINT department_pk PRIMARY KEY (code),
 CONSTRAINT department_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS city (
 code varchar(10) NOT NULL,
 name varchar(30) NOT NULL,
 department_code varchar(10) NOT NULL,
 CONSTRAINT city_pk PRIMARY KEY (code),
 CONSTRAINT city_unique UNIQUE (name),
 CONSTRAINT city_department_fk FOREIGN KEY (department_code) REFERENCES department(code)
);

CREATE TABLE IF NOT EXISTS material (
 id serial4 NOT NULL,
 name varchar(30) NOT NULL,
 description varchar(100) NULL,
 type varchar(59) NOT NULL,
 price numeric NOT NULL,
 buy_date timestamp NOT NULL,
 sold_date timestamp NULL,
 state varchar(20) NOT NULL,
 city_code varchar(10) NOT NULL,
 CONSTRAINT material_pk PRIMARY KEY (id),
 CONSTRAINT material_unique UNIQUE (name),
 CONSTRAINT material_city_fk FOREIGN KEY (city_code) REFERENCES city(code)
);