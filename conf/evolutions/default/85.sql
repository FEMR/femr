# --- !Ups

ALTER TABLE `users`
ADD COLUMN `passwordCreatedDate` DATETIME NOT NULL AFTER `password`;

UPDATE `users`
SET passwordCreatedDate = '9999-01-01';

# --- !Downs

ALTER TABLE `users`
DROP COLUMN `passwordCreatedDate`;