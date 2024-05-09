# --- !Ups

CREATE  TABLE `measurement_categories` (
    `category` VARCHAR(16) PRIMARY KEY NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS `measurement_categories`;
