# --- !Ups
ALTER TABLE `patients`
ADD COLUMN `birthday_is_fake` BOOLEAN;

# --- !Downs

ALTER TABLE `patients`
DROP COLUMN `birthday_is_fake`;