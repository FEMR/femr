# --- !Ups

ALTER TABLE `patients`
ADD COLUMN `is_dob_estimated` BOOL NULL DEFAULT NULL AFTER `age`;

# --- !Downs

ALTER TABLE `patients`
DROP COLUMN `is_dob_estimated`;
