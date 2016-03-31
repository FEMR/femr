# --- !Ups

ALTER TABLE `medication_administrations`
RENAME TO  `concept_prescription_administrations` ;


# --- !Downs

ALTER TABLE  `concept_prescription_administrations`
RENAME TO `medication_administrations`;