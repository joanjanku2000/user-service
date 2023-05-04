CREATE TABLE public."user"
(
    id            serial                      NOT NULL,
    username      character varying(25)       NOT NULL,
    name          character varying(100)      NOT NULL,
    last_name     character varying(100)      NOT NULL,
    email         character varying(255)      NOT NULL,
    birthday      timestamp without time zone,
    password      text                        NOT NULL,
    deleted       boolean,
    created_at    timestamp without time zone NOT NULL,
    last_modified timestamp without time zone,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_data_key
(
    id         serial                                         NOT NULL,
    key_name   character varying(255)                              NOT NULL,
    deleted    boolean                                        NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_data
(
    id         serial                 NOT NULL,
    key_value      character varying(255) NOT NULL,
    deleted    boolean  NOT NULL,
    created_at timestamp without time zone,
    updated_at  timestamp without time zone,
    PRIMARY KEY (id)
);

CREATE TABLE public.role
(
    id          serial                 NOT NULL,
    name        character varying(100) NOT NULL,
    description character varying(255),
    deleted     boolean                NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.user_role
(
    id      serial  NOT NULL,
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    deleted boolean,
    CONSTRAINT pk PRIMARY KEY (id),
    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES public."user" (id)
);


ALTER TABLE IF EXISTS public.user_data
    ADD COLUMN user_id integer NOT NULL;

ALTER TABLE IF EXISTS public.user_data
    ADD COLUMN data_key_id integer NOT NULL;

ALTER TABLE IF EXISTS public.user_data
    ADD CONSTRAINT uid_fk FOREIGN KEY (user_id)
        REFERENCES public."user" (id) ;

ALTER TABLE IF EXISTS public.user_data
    ADD CONSTRAINT dk_fk FOREIGN KEY (data_key_id)
        REFERENCES public.user_data_key (id);

ALTER table public.role
    add column last_modified timestamp;

ALTER table public.role
    add column created_at timestamp;
