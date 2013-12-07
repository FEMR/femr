# --- !Ups

INSERT INTO `vitals` (`name`, `data_type`, `unit_of_measurement`)
VALUES ('pregnant', 'int', 'weeks');

ALTER TABLE `patient_encounters`
DROP COLUMN `weeks_pregnant` ;




# --- !Downs

DELETE FROM `vitals` WHERE `id`='10';

ALTER TABLE `patient_encounters`
ADD COLUMN `weeks_pregnant` INT NULL DEFAULT NULL  AFTER `is_pregnant` ;

