# --- !Ups

CREATE  TABLE `measurement_units`
(
    `id` INTEGER PRIMARY KEY NOT NULL,
    `unit` VARCHAR(16) NOT NULL, 
    `category` VARCHAR(16) NOT NULL,
    FOREIGN KEY (`category`) REFERENCES measurement_categories(`category`)
);

# --- !Downs

DROP TABLE IF EXISTS `measurement_units`;
