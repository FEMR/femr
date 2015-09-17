# --- !Ups

ALTER TABLE `patient_prescriptions`
ADD COLUMN `date_dispensed` DATETIME NULL DEFAULT NULL AFTER `date_taken`;

# --- !Downs

ALTER TABLE `patient_prescriptions`
DROP COLUMN `date_dispensed`;