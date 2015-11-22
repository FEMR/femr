# --- !Ups

INSERT INTO vitals (name, data_type, unit_of_measurement)
  VALUES ('weeksPregnant', 'integer', 'weeks');

INSERT INTO patient_encounter_vitals (user_id, patient_encounter_id, vital_id, vital_value, date_taken)
      SELECT user_id_triage,
             id,
             ( SELECT id FROM vitals WHERE name = 'weeksPregnant') as vital_id,
             weeks_pregnant,
             date_of_triage_visit
      FROM patient_encounters
      WHERE weeks_pregnant IS NOT NULL;

ALTER TABLE patient_encounters DROP COLUMN weeks_pregnant;

# --- !Downs

ALTER TABLE `patient_encounters`
ADD COLUMN `weeks_pregnant` INT(255) NULL DEFAULT NULL  AFTER `date_of_triage_visit`;

UPDATE patient_encounters pe, patient_encounter_vitals pev
SET pe.weeks_pregnant = pev.vital_value
WHERE pe.id = pev.patient_encounter_id
AND pev.vital_id = (SELECT id FROM vitals WHERE name = 'weeksPregnant');

DELETE FROM patient_encounter_vitals WHERE vital_id IN
(SELECT id FROM vitals WHERE name = 'weeksPregnant');

DELETE FROM vitals WHERE name = 'weeksPregnant';