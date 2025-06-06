# --- !Ups
# --- Defaults multiple chief complaints to on in admin settings
UPDATE `system_settings`
SET `isActive`=1
WHERE `id` = 1
