# --- !Ups

ALTER TABLE `users`
ADD COLUMN `creation_date` DATETIME NOT NULL AFTER `last_login`;
ADD COLUMN `user_created` DATETIME NOT NULL AFTER `creation_date`;

UPDATE `users`
SET creation_date = '9999-01-01'

# --- !Downs

ALTER TABLE `users`
DROP COLUMN `creation_date`;
DROP COLUMN `user_created`;