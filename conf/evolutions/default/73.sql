# --- !Ups

ALTER TABLE `patient_encounters`
ADD COLUMN `patient_age_classification_id` INT(11) NULL AFTER `user_id_pharmacy`,
ADD INDEX `fk_patient_encounter_patient_age_classification_id_idx` (`patient_age_classification_id` ASC);
ALTER TABLE `patient_encounters`
ADD CONSTRAINT `fk_patient_encounter_patient_age_classification_id`
FOREIGN KEY (`patient_age_classification_id`)
REFERENCES `patient_age_classifications` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

# --- !Downs

ALTER TABLE `patient_encounters`
DROP FOREIGN KEY `fk_patient_encounter_patient_age_classification_id`;
ALTER TABLE `patient_encounters`
DROP COLUMN `patient_age_classification_id`,
DROP INDEX `fk_patient_encounter_patient_age_classification_id_idx` ;