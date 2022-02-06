CREATE SCHEMA IF NOT EXISTS demo;
CREATE TABLE IF NOT EXISTS demo.demo_table (
    id uuid,
    demo_field varchar,
    creation_date date,
    CONSTRAINT demo_table_pk PRIMARY KEY (id),
    CONSTRAINT demo_field_uq UNIQUE (demo_field)
);
CREATE UNIQUE INDEX ON demo.demo_table (demo_field);