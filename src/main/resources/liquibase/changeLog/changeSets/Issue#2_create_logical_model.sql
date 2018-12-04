--liquibase formatted sql

--changeset Kondaltsev:1
CREATE TABLE fias_street (
fstr_id        BIGSERIAL,
fstr_guid      VARCHAR(1000),
fstr_name      VARCHAR(1000),
fstr_type_name VARCHAR(1000)
);

ALTER TABLE fias_street
ADD CONSTRAINT pk_fias_street PRIMARY KEY (fstr_id);

CREATE INDEX indx_fias_street_name on fias_street (fstr_name);

--rollback drop table fias_street;

--changeset Kondaltsev:2
CREATE TABLE flat (
fl_id            BIGSERIAL,
fl_description   VARCHAR(2000),
fl_price	     NUMERIC,
fl_creation_time TIMESTAMPTZ,
fl_metro         VARCHAR(1000),
fl_house_number  VARCHAR(1000),
fl_fstr_id	   BIGSERIAL
);

ALTER TABLE flat ADD CONSTRAINT PK_FLAT PRIMARY KEY (fl_id);
ALTER TABLE flat ADD CONSTRAINT FK_FLAT_TO_FIAS_STREET FOREIGN KEY (fl_fstr_id) REFERENCES Fias_Street (fstr_id);

CREATE INDEX INDX_FLAT_FSTR_ID on flat (fl_fstr_id);

--rollback drop table Flat;

--changeset Kondaltsev:3
CREATE sequence hibernate_sequence start with 11000;
--rollback drop sequence hibernate_sequence;

