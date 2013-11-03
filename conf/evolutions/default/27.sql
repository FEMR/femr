# --- !Ups

ALTER TABLE `patient_prescriptions`
DROP FOREIGN KEY `fk_patient_prescriptions_medication_id_medications_id` ;

ALTER TABLE `patient_prescriptions`
DROP COLUMN `medication_id` , CHANGE
COLUMN `amount` `amount`
INT(11) NULL
, DROP INDEX `fk_patient_prescriptions_idx1` ;

ALTER TABLE `patient_prescriptions`
ADD COLUMN `medication_name` VARCHAR(255) NOT NULL
AFTER `replacement_id` ;

# --- !Downs

ALTER TABLE `patient_prescriptions`
ADD COLUMN `medication_id` INT(11) NOT NULL
AFTER `replacement_id` ;

ALTER TABLE `patient_prescriptions`
ADD CONSTRAINT `fk_patient_prescriptions_medication_id_medications_id`
FOREIGN KEY (`medication_id` )
REFERENCES `medications` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_patient_prescriptions_medication_id_medications_id_idx` (`medication_id` ASC) ;


ALTER TABLE `patient_prescriptions`
CHANGE COLUMN `amount` `amount`
INT(11) NOT NULL  ;

ALTER TABLE `patient_prescriptions`
DROP COLUMN `medication_name` ;
