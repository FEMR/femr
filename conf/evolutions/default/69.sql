# --- !Ups

ALTER TABLE `patient_encounters`
ADD COLUMN `patient_age_classification_id` INT(11) NULL AFTER `user_id_pharmacy`;

# --- !Downs

ALTER TABLE `patient_encounters`
DROP COLUMN `patient_age_classification_id`;