--liquibase formatted sql

--changeset user:0.1.0
CREATE TABLE foo (
  id                 INT8 NOT NULL,
  num                INT8 NOT NULL,
  PRIMARY KEY (id)
);

--rollback DROP TABLE foo;