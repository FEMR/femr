# --- !Ups

CREATE TABLE language_codes (
    code VARCHAR(5) NOT NULL,
    language_name VARCHAR(64) NOT NULL,
    PRIMARY KEY (code)
);

# --- !Downs

DROP TABLE IF EXISTS language_codes;