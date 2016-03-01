#ALTER TABLE `patients`
#ADD CONSTRAINT `fk_users_patients_user_id`
#FOREIGN KEY (`deleted_by_user_id`)
#REFERENCES `users` (`id`)
 # ON DELETE NO ACTION
  #ON UPDATE NO ACTION;