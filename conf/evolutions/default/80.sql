# --- !Ups

INSERT INTO `patient_prescription_replacement_reasons` (`name`, `description`) VALUES ('physician edit', 'the editing of a prescription as it\'s being prescribed by a physician');
INSERT INTO `patient_prescription_replacement_reasons` (`name`, `description`) VALUES ('pharmacist replacement', 'the replacement of a prescription by a pharmacist');
INSERT INTO `patient_prescription_replacement_reasons` (`name`, `description`) VALUES ('encounter edit', 'the editing of a prescription after the encounter has been closed');

# --- !Downs

DELETE FROM `patient_prescription_replacement_reasons` WHERE `description`="the editing of a prescription as it's being prescribed by a physician";
DELETE FROM `patient_prescription_replacement_reasons` WHERE `description`="the replacement of a prescription by a pharmacist";
DELETE FROM `patient_prescription_replacement_reasons` WHERE `description`="the editing of a prescription after the encounter has been closed";
