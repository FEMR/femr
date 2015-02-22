# --- !Ups

ALTER TABLE `femr`.`users`
CHANGE COLUMN `last_login` `last_login` DATETIME NULL;

# --- !Downs

ALTER TABLE `femr`.`users`
CHANGE COLUMN `last_login` `last_login` DATETIME NOT NULL;