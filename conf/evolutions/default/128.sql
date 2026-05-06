# --- !Ups

CREATE TABLE IF NOT EXISTS `concept_who_health_events` (
    `id`       INT(11) NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `concept_who_health_events` (`name`, `category`)
SELECT data.`name`, data.`category`
FROM (
    SELECT 'Major head/spine injury' AS `name`, 'Trauma' AS `category` UNION ALL
    SELECT 'Major torso injury', 'Trauma' UNION ALL
    SELECT 'Major extremity injury', 'Trauma' UNION ALL
    SELECT 'Moderate injury', 'Trauma' UNION ALL
    SELECT 'Minor injury', 'Trauma' UNION ALL
    SELECT 'Acute respiratory infection', 'Infectious disease' UNION ALL
    SELECT 'Acute watery diarrhea', 'Infectious disease' UNION ALL
    SELECT 'Acute bloody diarrhea', 'Infectious disease' UNION ALL
    SELECT 'Acute jaundice syndrome', 'Infectious disease' UNION ALL
    SELECT 'Suspected measles', 'Infectious disease' UNION ALL
    SELECT 'Suspected meningitis', 'Infectious disease' UNION ALL
    SELECT 'Suspected tetanus', 'Infectious disease' UNION ALL
    SELECT 'Acute flaccid paralysis', 'Infectious disease' UNION ALL
    SELECT 'Acute haemorrhagic fever', 'Infectious disease' UNION ALL
    SELECT 'Fever of unknown origin', 'Infectious disease' UNION ALL
    SELECT 'Surgical emergency (Non-trauma)', 'Emergency' UNION ALL
    SELECT 'Medical emergency (Non-infectious)', 'Emergency' UNION ALL
    SELECT 'Skin disease', 'Other key diseases' UNION ALL
    SELECT 'Acute mental health problem', 'Other key diseases' UNION ALL
    SELECT 'Obstetric complications', 'Other key diseases' UNION ALL
    SELECT 'Severe Acute Malnutrition (SAM)', 'Other key diseases' UNION ALL
    SELECT 'Other diagnosis, not specified above', 'Other key diseases'
) AS data
WHERE NOT EXISTS (
    SELECT 1
    FROM `concept_who_health_events` existing
    WHERE existing.`name` = data.`name`
      AND existing.`category` = data.`category`
);

CREATE TABLE IF NOT EXISTS `concept_who_procedures` (
    `id`   INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `concept_who_procedures` (`name`)
SELECT data.`name`
FROM (
    SELECT 'Major procedure (excluding MDS31)' AS `name` UNION ALL
    SELECT 'Limb amputation excluding digits' UNION ALL
    SELECT 'Minor surgical procedure' UNION ALL
    SELECT 'Normal Vaginal Delivery (NVD)' UNION ALL
    SELECT 'Caesarean section' UNION ALL
    SELECT 'Obstetrics others'
) AS data
WHERE NOT EXISTS (
    SELECT 1
    FROM `concept_who_procedures` existing
    WHERE existing.`name` = data.`name`
);

SET @who_health_event_col := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE table_schema = DATABASE()
      AND table_name = 'patient_encounter_tab_fields'
      AND column_name = 'who_health_event'
);
SET @sql := IF(@who_health_event_col = 0,
    'ALTER TABLE `patient_encounter_tab_fields` ADD COLUMN `who_health_event` VARCHAR(255) NULL DEFAULT NULL',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @who_health_event_id_col := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE table_schema = DATABASE()
      AND table_name = 'patient_encounter_tab_fields'
      AND column_name = 'who_health_event_id'
);
SET @sql := IF(@who_health_event_id_col = 0,
    'ALTER TABLE `patient_encounter_tab_fields` ADD COLUMN `who_health_event_id` INT(11) NULL DEFAULT NULL',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @who_procedure_id_col := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE table_schema = DATABASE()
      AND table_name = 'patient_encounter_tab_fields'
      AND column_name = 'who_procedure_id'
);
SET @sql := IF(@who_procedure_id_col = 0,
    'ALTER TABLE `patient_encounter_tab_fields` ADD COLUMN `who_procedure_id` INT(11) NULL DEFAULT NULL',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk_who_health_event := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'patient_encounter_tab_fields'
      AND CONSTRAINT_NAME = 'fk_petf_who_health_event'
);
SET @sql := IF(@fk_who_health_event = 0,
    'ALTER TABLE `patient_encounter_tab_fields` ADD CONSTRAINT `fk_petf_who_health_event` FOREIGN KEY (`who_health_event_id`) REFERENCES `concept_who_health_events` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk_who_procedure := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'patient_encounter_tab_fields'
      AND CONSTRAINT_NAME = 'fk_petf_who_procedure'
);
SET @sql := IF(@fk_who_procedure = 0,
    'ALTER TABLE `patient_encounter_tab_fields` ADD CONSTRAINT `fk_petf_who_procedure` FOREIGN KEY (`who_procedure_id`) REFERENCES `concept_who_procedures` (`id`)',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

INSERT INTO `tab_fields` (`name`, `isDeleted`, `tab_id`, `type_id`)
SELECT 'whoProcedure', 0, t.`id`, tft.`id`
FROM `tabs` t, `tab_field_types` tft
WHERE t.`name` = 'Treatment'
  AND tft.`name` = 'text'
  AND NOT EXISTS (
      SELECT 1 FROM `tab_fields` existing WHERE existing.`name` = 'whoProcedure'
  )
LIMIT 1;

INSERT INTO `system_settings` (`name`, `isActive`, `description`)
SELECT 'WHO Reporting', 0, 'Enables WHO MDS daily reporting for managers'
FROM dual
WHERE NOT EXISTS (
    SELECT 1 FROM `system_settings` existing WHERE existing.`name` = 'WHO Reporting'
);

# --- !Downs

DELETE FROM `system_settings` WHERE `name` = 'WHO Reporting';
DELETE FROM `tab_fields` WHERE `name` = 'whoProcedure';

ALTER TABLE `patient_encounter_tab_fields`
DROP FOREIGN KEY `fk_petf_who_health_event`,
DROP FOREIGN KEY `fk_petf_who_procedure`,
DROP COLUMN `who_health_event`,
DROP COLUMN `who_health_event_id`,
DROP COLUMN `who_procedure_id`;

DROP TABLE IF EXISTS `concept_who_procedures`;
DROP TABLE IF EXISTS `concept_who_health_events`;
