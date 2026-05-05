# --- !Ups

CREATE TABLE `concept_who_health_events` (
    `id`       INT(11) NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `concept_who_health_events` (`name`, `category`) VALUES
('Major head/spine injury',                'Trauma'),
('Major torso injury',                     'Trauma'),
('Major extremity injury',                 'Trauma'),
('Moderate injury',                        'Trauma'),
('Minor injury',                           'Trauma'),
('Acute respiratory infection',            'Infectious disease'),
('Acute watery diarrhea',                  'Infectious disease'),
('Acute bloody diarrhea',                  'Infectious disease'),
('Acute jaundice syndrome',               'Infectious disease'),
('Suspected measles',                      'Infectious disease'),
('Suspected meningitis',                   'Infectious disease'),
('Suspected tetanus',                      'Infectious disease'),
('Acute flaccid paralysis',                'Infectious disease'),
('Acute haemorrhagic fever',               'Infectious disease'),
('Fever of unknown origin',                'Infectious disease'),
('Surgical emergency (Non-trauma)',        'Emergency'),
('Medical emergency (Non-infectious)',     'Emergency'),
('Skin disease',                           'Other key diseases'),
('Acute mental health problem',            'Other key diseases'),
('Obstetric complications',               'Other key diseases'),
('Severe Acute Malnutrition (SAM)',        'Other key diseases'),
('Other diagnosis, not specified above',   'Other key diseases');

CREATE TABLE `concept_who_procedures` (
    `id`   INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `concept_who_procedures` (`name`) VALUES
('Major procedure (excluding MDS31)'),
('Limb amputation excluding digits'),
('Minor surgical procedure'),
('Normal Vaginal Delivery (NVD)'),
('Caesarean section'),
('Obstetrics others');

ALTER TABLE `patient_encounter_tab_fields`
ADD COLUMN `who_health_event`    VARCHAR(255) NULL DEFAULT NULL,
ADD COLUMN `who_health_event_id` INT(11)      NULL DEFAULT NULL,
ADD COLUMN `who_procedure_id`    INT(11)      NULL DEFAULT NULL,
ADD CONSTRAINT `fk_petf_who_health_event` FOREIGN KEY (`who_health_event_id`) REFERENCES `concept_who_health_events` (`id`),
ADD CONSTRAINT `fk_petf_who_procedure`    FOREIGN KEY (`who_procedure_id`)    REFERENCES `concept_who_procedures` (`id`);

INSERT INTO `tab_fields` (`name`, `isDeleted`, `tab_id`, `type_id`)
SELECT 'whoProcedure', 0, t.`id`, tft.`id`
FROM `tabs` t, `tab_field_types` tft
WHERE t.`name` = 'Treatment' AND tft.`name` = 'text'
LIMIT 1;

INSERT INTO `system_settings` (`name`, `isActive`, `description`)
VALUES ('WHO Reporting', 0, 'Enables WHO MDS daily reporting for managers');
