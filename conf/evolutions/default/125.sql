# --- !Ups
# --- Updated the name to make it more clear for the end user in admin settings
UPDATE `kit_status`
SET `name`= "Backend Connection"
WHERE `id` = 1
