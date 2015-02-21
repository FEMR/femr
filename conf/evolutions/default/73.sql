# --- !Ups

ALTER TABLE `chief_complaints`
ADD COLUMN `sort_order` INT NULL AFTER `patient_encounter_id`;

# --- !Downs

ALTER TABLE `chief_complaints`
DROP COLUMN `sort_order`;