# --- !Ups

ALTER TABLE `patient_encounters`
ADD COLUMN `is_diabetes_screened` BOOL NULL DEFAULT NULL AFTER `mission_trip_id`;

# --- !Downs

ALTER TABLE `patient_encounters`
DROP COLUMN `is_diabetes_screened`;