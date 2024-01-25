# --- !Ups

CREATE TABLE `page_elements` (
     `element_id` INT NOT NULL AUTO_INCREMENT,
     `description` TEXT NOT NULL,
     PRIMARY KEY (`element_id`),
     UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC)
);

ALTER TABLE `page_elements`
    CHANGE COLUMN `element_id` `element_id` INT NOT NULL AUTO_INCREMENT,
    ADD COLUMN `page_id` INT NOT NULL,
    ADD INDEX `fk_page_1_idx` (`page_id` ASC);

ALTER TABLE `page_elements`
    ADD CONSTRAINT `fk_page_1`
        FOREIGN KEY (`page_id`)
            REFERENCES `pages` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;


# --- !Downs

DROP TABLE IF EXISTS `page_elements`;