# --- !Ups

INSERT INTO `roles` (`name`) VALUES ('Manager');

# --- !Downs

DELETE FROM `roles`
WHERE `name` = "Manager";

