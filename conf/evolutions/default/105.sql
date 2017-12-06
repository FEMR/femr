# --- !Ups

CREATE  TABLE `feedback` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `date` DATE NOT NULL,
  `feedback` VARCHAR(MAX) NOT NULL,
  PRIMARY KEY (`id`) );

# --- !Downs

DROP TABLE IF EXISTS feedback;











