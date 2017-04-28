# --- !Ups

ALTER TABLE `patient_encounter_tab_fields`
ADD COLUMN `IsDeleted` DATETIME NULL AFTER `chief_complaint_id`,
ADD COLUMN `DeletedByUserId` INTEGER NULL AFTER `IsDeleted`;

# --- !Downs

ALTER TABLE `patient_encounter_tab_fields`
DROP COLUMN `IsDeleted`,
DROP COLUMN `DeletedByUserId`;