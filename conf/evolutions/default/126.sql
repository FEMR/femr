# --- !Ups

ALTER TABLE patients
  ADD COLUMN country VARCHAR(255);

# --- !Downs

ALTER TABLE patients
  DROP COLUMN country;
