# --- !Ups

UPDATE `tab_fields`
SET name="procedure/counseling"
WHERE name="treatment"


# --- !Downs

UPDATE `tab_fields`
SET name="treatment"
WHERE name="procedure/counseling"
