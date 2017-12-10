# --- !Ups

CREATE  TABLE `feedback` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `date` DATE NOT NULL ,
  `feedback` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) );

# --- !Downs

DROP TABLE IF EXISTS feedback;











