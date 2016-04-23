# --- !Ups

ALTER TABLE `femr`.`patients`
ADD COLUMN `reason_deleted` VARCHAR(255) NULL DEFAULT NULL AFTER `deleted_by_user_id`;


# --- !Downs

ALTER TABLE `femr`.`patients`
DROP COLUMN `reason_deleted`;