# --- !Ups

ALTER TABLE `patients`
ADD COLUMN `deleted_by_user_id` INT(11) NULL DEFAULT NULL  AFTER `isDeleted`;
ALTER TABLE `medication_inventories`
ADD COLUMN `isDeleted` DATETIME NULL DEFAULT NULL AFTER `medication_id`;

ALTER TABLE `patients`
ADD COLUMN `phone_no` VARCHAR (255)DEFAULT NULL AFTER `photo_id`;

# --- !Downs

ALTER TABLE `patients`
DROP COLUMN `deleted_by_user_id`;

ALTER TABLE `medication_inventories`
DROP COLUMN `isDeleted`;

ALTER TABLE `patients`
DROP COLUMN `phone_no`;


