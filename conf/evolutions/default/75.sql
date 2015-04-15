# --- !Ups

ALTER TABLE patient_prescriptions MODIFY medication_administrations_id INT(11) NOT NULL;
ALTER TABLE medication_administrations ADD daily_modifier DECIMAL(5, 2) NOT NULL;

# --- !Downs

ALTER TABLE patient_prescriptions MODIFY medication_administrations_id INT(11);
ALTER TABLE medication_administrations DROP COLUMN daily_modifier;
