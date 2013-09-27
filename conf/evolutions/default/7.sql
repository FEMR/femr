# --- !Ups

CREATE  TABLE `vitals` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `data_type` VARCHAR(255) NULL ,
  `unit_of_measurement` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) );

# --- !Downs

DROP TABLE IF EXISTS `vitals`