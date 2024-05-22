# --- !Ups

CREATE  TABLE `pages` (
      `id` INT NOT NULL AUTO_INCREMENT ,
      `name` TEXT NOT NULL ,
      PRIMARY KEY (`id`) ,
      UNIQUE INDEX `id_UNIQUE` (`id` ASC) );

# --- !Downs

DROP TABLE IF EXISTS `pages`;