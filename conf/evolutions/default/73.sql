# --- !Ups

ALTER TABLE `users`
CHANGE COLUMN `last_login` `last_login` DATETIME NULL;

# --- !Downs

UPDATE `users`	SET `last_login` = now() WHERE `last_login` is NULL;

ALTER TABLE `users`
CHANGE COLUMN `last_login` `last_login` DATETIME NOT NULL;

