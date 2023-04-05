CREATE SCHEMA IF NOT EXISTS short
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS short.urls
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    long_url text COLLATE pg_catalog."default" NOT NULL,
    short_url text COLLATE pg_catalog."default",
    CONSTRAINT urls_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS short.urls
    OWNER to postgres;