# --- !Ups
CREATE TABLE `page_element_translations` (
     `translation_id` INT NOT NULL AUTO_INCREMENT,
     `element_id` INT NOT NULL,
     `language_code` VARCHAR(5) NOT NULL,
     `translation` TEXT NOT NULL,
     PRIMARY KEY (`translation_id`),
     UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC),
     CONSTRAINT `fk_element_id` FOREIGN KEY (`element_id`) REFERENCES `page_elements` (`element_id`) ON DELETE CASCADE ON UPDATE NO ACTION
);

# --- !Downs

DROP TABLE IF EXISTS `page_element_translations`;