# --- !Ups

ALTER TABLE `patient_age_classifications`
CHANGE COLUMN `id` `id` INT(11) NOT NULL ,
ADD COLUMN `sortOrder` INT NOT NULL AUTO_INCREMENT AFTER `isDeleted`,
ADD UNIQUE INDEX `sortOrder_UNIQUE` (`sortOrder` ASC);

# --- !Downs

ALTER TABLE `patient_age_classifications`
DROP COLUMN `sortOrder`,
DROP INDEX `sortOrder_UNIQUE` ;