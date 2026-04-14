# --- !Ups

CREATE TABLE `who_report_config` (
    `mission_trip_id`    INT(11)      NOT NULL,
    `org_name`           VARCHAR(255) NULL DEFAULT NULL,
    `team_type_1mobile`  TINYINT(1)   NOT NULL DEFAULT 0,
    `team_type_1fixed`   TINYINT(1)   NOT NULL DEFAULT 0,
    `team_type_2`        TINYINT(1)   NOT NULL DEFAULT 0,
    `team_type_3`        TINYINT(1)   NOT NULL DEFAULT 0,
    `team_specialized`   TINYINT(1)   NOT NULL DEFAULT 0,
    `contact_persons`    VARCHAR(255) NULL DEFAULT NULL,
    `phone_no`           VARCHAR(100) NULL DEFAULT NULL,
    `email`              VARCHAR(255) NULL DEFAULT NULL,
    `state_admin1`       VARCHAR(255) NULL DEFAULT NULL,
    `village_admin3`     VARCHAR(255) NULL DEFAULT NULL,
    `facility_name`      VARCHAR(255) NULL DEFAULT NULL,
    `geo_lat`            VARCHAR(50)  NULL DEFAULT NULL,
    `geo_long`           VARCHAR(50)  NULL DEFAULT NULL,
    PRIMARY KEY (`mission_trip_id`),
    CONSTRAINT `fk_who_report_config_mission_trip_id`
        FOREIGN KEY (`mission_trip_id`) REFERENCES `mission_trips` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE IF EXISTS `who_report_config`;
