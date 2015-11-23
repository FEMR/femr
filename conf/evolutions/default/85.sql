# --- !Ups

INSERT INTO `vitals` (`name`, `data_type`, `unit_of_measurement`)
  VALUES ('weeksPregnant', 'integer', 'weeks');

# --- !Downs

DELETE FROM patient_encounter_vitals WHERE vital_id IN
(SELECT id FROM vitals WHERE name = 'weeksPregnant')

DELETE FROM vitals WHERE name = 'weeksPregnant'