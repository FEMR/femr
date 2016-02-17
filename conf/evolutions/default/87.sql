# --- !Ups

ALTER TABLE `patients`
ADD COLUMN `deleted_by_user_id` INT(11) NULL DEFAULT NULL  AFTER `isDeleted`;

# --- !Downs

ALTER TABLE `patients`
DROP COLUMN `deleted_by_user_id`;
