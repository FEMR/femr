# --- !Ups

INSERT INTO `vitals` (`id`, `name`, `data_type`, `unit_of_measurement`)
  VALUES ('11', 'weeksPregnant', 'float', 'weeks');

# --- !Downs

DELETE FROM `patient_encounter_vitals`
WHERE `vital_id` = 11;

DELETE FROM `vitals`
WHERE `id` =11;