# --- !Ups

DELETE FROM `patient_encounter_treatment_fields`
WHERE `treatment_field_id` = '5';

DELETE FROM `treatment_fields`
WHERE `id`='5';

ALTER TABLE `patient_prescriptions`
DROP FOREIGN KEY `fk_patient_prescriptions_medication_id_medications_id` ;

ALTER TABLE `patient_prescriptions`
DROP COLUMN `medication_id` , CHANGE
COLUMN `amount` `amount`
INT(11) NULL;

ALTER TABLE `patient_prescriptions`
ADD COLUMN `medication_name` VARCHAR(255) NOT NULL
AFTER `replacement_id` ;

# --- !Downs

INSERT INTO `treatment_fields` (`name`)
  VALUES ('prescription');

ALTER TABLE `patient_prescriptions`
ADD COLUMN `medication_id` INT(11) NOT NULL
AFTER `replacement_id` ;

ALTER TABLE `patient_prescriptions`
ADD CONSTRAINT `fk_patient_prescriptions_medication_id_medications_id`
FOREIGN KEY (`medication_id` )
REFERENCES `medications` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
;


ALTER TABLE `patient_prescriptions`
CHANGE COLUMN `amount` `amount`
INT(11) NOT NULL  ;

ALTER TABLE `patient_prescriptions`
DROP COLUMN `medication_name` ;
