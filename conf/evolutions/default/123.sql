# --- !Ups

CREATE TABLE `page_element_translations` (
     `translation_id` INT NOT NULL AUTO_INCREMENT,
     `element_id` INT NOT NULL,
     `category` VARCHAR(5) NOT NULL,
     `translation` TEXT NOT NULL,
     PRIMARY KEY (`translation_id`),
     UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC)
);

# --- !Downs

DROP TABLE IF EXISTS `page_element_translations`;