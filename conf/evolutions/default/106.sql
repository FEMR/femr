# --- !Ups

ALTER TABLE `medication_inventories`
ADD FOREIGN KEY (`createdBy`)
REFERENCES `users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

# --- !Downs

ALTER TABLE `medication_inventories`
DROP FOREIGN KEY `createdBy`;