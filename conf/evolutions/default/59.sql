# --- !Ups

CREATE TABLE `system_settings` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `isActive` BIT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


ALTER TABLE system_settings
ADD COLUMN description VARCHAR (300);

UPDATE system_settings SET description =
'This setting would allow user to add multiple chief complaints instead of just one'
WHERE id = 1;

UPDATE system_settings SET description =
'This is a compulsory setting and cannot be unselected. '
WHERE id = 2;

UPDATE system_settings SET description =
'Medical photo tab is a compulsory setting and cannot be unselected. '
WHERE id = 3;

UPDATE system_settings SET description =
'This setting must be selected if medical HPI consolidation is to be permitted. '
WHERE id = 4;

UPDATE system_settings SET description =
'This is a compulsory setting and cannot be unselected. It makes sure that the metric system is being used '
WHERE id = 5;

UPDATE system_settings SET description =
'This setting must be selected, if you want to apply filter on mission countries. '
WHERE id = 6;

UPDATE system_settings SET description =
'This setting must be selected if you want to allow the research only option. '
WHERE id = 7;

UPDATE system_settings SET description =
'This setting must be selected to prompt for diabetes. '
WHERE id = 8;


# --- !Downs

DROP TABLE `system_settings`;