# --- !Ups

CREATE  TABLE `burn_rates`
(
    `id`              INT NOT NULL AUTO_INCREMENT,
    `med_id`          INT,
    `trip_id`          INT,
    `a_s`       VARCHAR(255) NULL ,
    `calculated_time` DATETIME,

    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

# --- !Downs

DROP TABLE IF EXISTS burn_rates;