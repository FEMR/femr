# --- !Ups

ALTER TABLE `patient_prescriptions`
ADD COLUMN `date_taken` DATETIME NOT NULL  AFTER `medication_name` ;


# --- !Downs

ALTER TABLE `patient_prescriptions`
DROP COLUMN `date_taken` ;
