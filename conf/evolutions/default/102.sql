# --- !Ups
ALTER TABLE `patients`
ADD COLUMN `age_calculated` INT(1) NULL DEFAULT NULL AFTER `reason_deleted`;

# --- !Downs
