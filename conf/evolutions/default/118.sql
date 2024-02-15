# --- !Ups
ALTER TABLE `users`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `chief_complaints`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `concept_diagnoses`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `patient_prescriptions`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `patient_encounters`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `patient_age_classifications`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `concept_medication_forms`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `mission_teams`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

ALTER TABLE `photos`
    ADD COLUMN `language_code` VARCHAR(5) NULL;

# --- !Downs
ALTER TABLE `users`
    DROP COLUMN `language_code`;

ALTER TABLE `chief_complaints`
    DROP COLUMN `language_code`;

ALTER TABLE `concept_diagnoses`
    DROP COLUMN `language_code`;

ALTER TABLE `patient_prescriptions`
    DROP COLUMN  `language_code`;

ALTER TABLE `patient_encounters`
    DROP COLUMN  `language_code`;

ALTER TABLE `patient_age_classifications`
    DROP COLUMN  `language_code`;

ALTER TABLE `concept_medication_forms`
    DROP COLUMN  `language_code`;

ALTER TABLE `mission_teams`
    DROP COLUMN `language_code`;
ALTER TABLE `photos`
    DROP COLUMN `language_code`;