# --- !Ups

ALTER TABLE `patient_encounters`
ADD COLUMN `isEncounterDeleted` DATETIME NULL DEFAULT NULL AFTER `is_diabetes_screened`;

ALTER TABLE `patient_encounters`
ADD COLUMN `deleted_encounter_by_user_id` INT(11) NULL DEFAULT NULL  AFTER `isEncounterDeleted`;

ALTER TABLE `patient_encounters`
ADD COLUMN `reason_encounter_deleted` VARCHAR(255) NULL DEFAULT NULL AFTER `deleted_encounter_by_user_id`;

# --- !Downs

ALTER TABLE `patient_encounters`
DROP COLUMN `isEncounterDeleted`;

ALTER TABLE `patient_encounters`
DROP COLUMN `deleted_encounter_by_user_id`;

ALTER TABLE `patient_encounters`
DROP COLUMN `reason_encounter_deleted`;