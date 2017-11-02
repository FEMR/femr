# --- !Ups

ALTER TABLE `medication_inventories`
ADD COLUMN `timeAdded` DATETIME NULL DEFAULT NULL,
ADD COLUMN `createdBy` VARCHAR (255) NULL DEFAULT NULL;


# --- !Downs

ALTER TABLE `medication_inventories`
DROP COLUMN `timeAdded`,
DROP COLUMN `createdBy`;
