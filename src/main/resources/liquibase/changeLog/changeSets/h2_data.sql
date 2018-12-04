--liquibase formatted sql

--changeset Kondaltsev:100
ALTER SEQUENCE hibernate_sequence RESTART WITH 21;
--rollback drop sequence hibernate_sequence;

--changeset Kondaltsev:101
--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM flat
INSERT INTO flat (fl_id, fl_description, fl_price, fl_creation_time, fl_metro, fl_house_number, fl_fstr_id)
VALUES (22, 'Best flat', 55.3, current_date , 'Kutuzovkay', 2, 2);

INSERT INTO flat (fl_id, fl_description, fl_price, fl_creation_time, fl_metro, fl_house_number, fl_fstr_id)
VALUES (23, 'Simple flat', 10.2, current_date , 'Kutuzovkay', 3, 2);
--rollback delete from flat where id = 22;



