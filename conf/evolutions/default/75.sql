# --- !Ups

ALTER TABLE `medication_administrations` ADD `daily_modifier` DECIMAL(5, 2) NOT NULL;

ALTER TABLE `patient_prescriptions`
DROP FOREIGN KEY `fk_patient_prescriptions_medication_administrations`;

ALTER TABLE `medication_administrations`
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ,
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

ALTER TABLE `patient_prescriptions`
CHANGE COLUMN `medication_administrations_id` `medication_administrations_id` INT(11) NULL DEFAULT NULL ;

ALTER TABLE `patient_prescriptions`
ADD CONSTRAINT `fk_patient_prescriptions_medication_administrations`
FOREIGN KEY (`medication_administrations_id`)
REFERENCES `medication_administrations` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

# --- !Downs

ALTER TABLE `medication_administrations` DROP COLUMN `daily_modifier`;

  ALTER TABLE `patient_prescriptions`
DROP FOREIGN KEY `fk_patient_prescriptions_medication_administrations`;

ALTER TABLE `medication_administrations`
CHANGE COLUMN `id` `id` INT(10) UNSIGNED NOT NULL ,
DROP INDEX `id_UNIQUE` ;

ALTER TABLE `patient_prescriptions`
CHANGE COLUMN `medication_administrations_id` `medication_administrations_id` INT(11) UNSIGNED NULL ;


  ALTER TABLE `patient_prescriptions`
ADD CONSTRAINT `fk_patient_prescriptions_medication_administrations`
FOREIGN KEY (`medication_administrations_id`)
REFERENCES `medication_administrations` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
