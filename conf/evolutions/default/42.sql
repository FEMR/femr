# --- !Ups

ALTER TABLE `patients` DROP COLUMN `photo_view_x`;
ALTER TABLE `patients` DROP COLUMN `photo_view_y`;
ALTER TABLE `patients` DROP COLUMN `photo_view_width`;
ALTER TABLE `patients` DROP COLUMN `photo_view_height`;

# --- !Downs