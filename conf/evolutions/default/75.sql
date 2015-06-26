# --- !Ups

CREATE TABLE `login_attempts` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NULL,
  `ip_address` BINARY(16) NOT NULL,
  `date` DATETIME NOT NULL,
  `isSuccessful` BIT(1) NOT NULL,
  `username_attempt` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `login_attempts_user_id_users_id`
    FOREIGN KEY (`id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


# --- !Downs

DROP TABLE `login_attempts`;
