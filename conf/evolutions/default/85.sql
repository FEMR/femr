# --- !Ups

ALTER TABLE `users`
ADD COLUMN `date_created` DATETIME NOT NULL AFTER `last_login`;
ADD COLUMN `created_by` DATETIME NOT NULL AFTER `date_created`;

UPDATE `users`
SET date_created = '9999-01-01'

# --- !Downs

ALTER TABLE `users`
DROP COLUMN `date_created`;
DROP COLUMN `created_by`;