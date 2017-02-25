# --- !Ups

ALTER TABLE `photos`
ADD COLUMN `photo` BLOB NULL AFTER `insertTS`;

# --- !Downs

ALTER TABLE `photos` DROP COLUMN `photo`;