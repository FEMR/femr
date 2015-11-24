# --- !Ups

ALTER TABLE `patient_encounters`
ADD COLUMN `user_id_diabetes_screen` INT(11) NULL DEFAULT NULL,
ADD COLUMN `date_of_diabetes_screen` DATETIME NULL DEFAULT NULL,
ADD INDEX `fk_patient_encounters_user_id_users_id_diabetes_idx` (`user_id_diabetes_screen` ASC);
ALTER TABLE `patient_encounters`
ADD CONSTRAINT `fk_patient_encounters_user_id_users_id_diabetes`
  FOREIGN KEY (`user_id_diabetes_screen`)
  REFERENCES `users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

# --- !Downs

ALTER TABLE `patient_encounters`
DROP FOREIGN KEY `fk_patient_encounters_user_id_users_id_diabetes`;
ALTER TABLE `patient_encounters`
DROP COLUMN `date_of_diabetes_screen`,
DROP COLUMN `user_id_diabetes_screen`,
DROP INDEX `fk_patient_encounters_user_id_users_id_diabetes_idx` ;
