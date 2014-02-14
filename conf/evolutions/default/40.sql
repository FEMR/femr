# --- !Ups

ALTER TABLE `users`
ADD COLUMN `last_login` DATETIME NOT NULL AFTER `password`;

# --- !Downs

ALTER TABLE `users`
DROP COLUMN `last_login`;
