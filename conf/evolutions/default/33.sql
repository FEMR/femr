# --- !Ups

INSERT INTO `hpi_fields` (`name`)
VALUES ('narrative');


# --- !Downs

DELETE FROM `hpi_fields`
WHERE `id`='10';