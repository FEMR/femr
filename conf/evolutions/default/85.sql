# --- !Ups
ALTER TABLE `users`
ADD COLUMN `passwordCreatedDate` DATETIME NOT NULL AFTER `password`;

UPDATE `users`
SET passwordCreatedDate = '9999-01-01';

INSERT INTO `vitals` (`name`, `data_type`, `unit_of_measurement`)
VALUES ('weeksPregnant', 'integer', 'weeks');

# --- !Downs
ALTER TABLE `users`
DROP COLUMN `passwordCreatedDate`;

DELETE FROM patient_encounter_vitals WHERE vital_id IN
(SELECT id FROM vitals WHERE name = 'weeksPregnant')

DELETE FROM vitals WHERE name = 'weeksPregnant'