# --- !Ups

INSERT INTO `vitals` (`id`, `name`, `data_type`, `unit_of_measurement`)
  VALUES ('12', 'smoker', 'int', 'True/False');

INSERT INTO `vitals` (`id`, `name`, `data_type`, `unit_of_measurement`)
  VALUES ('13', 'diabetic', 'int', 'True/False');

INSERT INTO `vitals` (`id`, `name`, `data_type`, `unit_of_measurement`)
  VALUES ('14', 'alcohol', 'int', 'True/False');

# --- !Downs

DELETE FROM `patient_encounter_vitals`
WHERE `name` = 'smoker';

DELETE FROM `vitals`
WHERE `name` ='smoker';

DELETE FROM `patient_encounter_vitals`
WHERE `name` = 'diabetic';

DELETE FROM `vitals`
WHERE `name` ='diabetic';

DELETE FROM `patient_encounter_vitals`
WHERE `name` = 'alcohol';

DELETE FROM `vitals`
WHERE `name` ='alcohol';