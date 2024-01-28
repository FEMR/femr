# --- !Ups

CREATE  TABLE `measurement_categories`
(
    `category` VARCHAR(16) NOT NULL PRIMARY KEY
);

# --- !Downs

DROP TABLE IF EXISTS `measurement_categories`;