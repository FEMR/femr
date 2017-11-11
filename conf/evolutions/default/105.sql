# --- !Ups

ALTER TABLE `medication_inventories`
ADD COLUMN `timeAdded` DATETIME NOT NULL,
ADD COLUMN `createdBy` INT(11) NOT NULL;

# --- !Downs

ALTER TABLE `medication_inventories`
DROP COLUMN `timeAdded`,
DROP COLUMN `createdBy`;
