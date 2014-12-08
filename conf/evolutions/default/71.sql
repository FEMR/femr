# --- !Ups

ALTER TABLE `patient_prescriptions`
ADD COLUMN `isCounseled` BIT(1) NOT NULL AFTER `special_instructions`;

ALTER TABLE `patient_prescriptions`
CHANGE COLUMN `isCounseled` `isCounseled` BIT(1) NOT NULL DEFAULT false ,
ADD COLUMN `isDispensed` BIT(1) NOT NULL DEFAULT false AFTER `isCounseled`;

# --- !Downs

ALTER TABLE `patient_prescriptions`
DROP COLUMN `isCounseled`;

ALTER TABLE `patient_prescriptions`
DROP COLUMN `isDispensed`;