# --- !Ups

ALTER TABLE `patient_age_classifications`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ,
CHANGE COLUMN `sortOrder` `sortOrder` INT(11) NOT NULL ;

# --- !Downs

ALTER TABLE `patient_age_classifications`
CHANGE COLUMN `id` `id` INT(11) NOT NULL ,
CHANGE COLUMN `sortOrder` `sortOrder` INT(11) NOT NULL AUTO_INCREMENT ;