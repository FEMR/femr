# --- !Ups

ALTER TABLE patients ADD COLUMN phone_number VARCHAR(20);

# --- !Downs

ALTER TABLE patients DROP COLUMN phone_number;
