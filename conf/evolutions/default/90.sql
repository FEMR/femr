# --- !Ups

ALTER TABLE `patients`
ADD COLUMN `phone_no` VARCHAR (255)DEFAULT NULL AFTER `photo_id`;

# --- !Downs

ALTER TABLE `patients`
DROP COLUMN `phone_no`;
