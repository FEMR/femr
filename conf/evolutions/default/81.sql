# --- !Ups

INSERT INTO `patient_prescription_replacements`
	(
    `patient_prescription_id_original`,
    `patient_prescription_id_replacement`,
    `patient_prescription_replacement_reason_id`
    )
SELECT `pp`.`id`, `pp`.`replacement_id`, `pprr`.`id`
FROM `patient_prescriptions` pp, `patient_prescription_replacement_reasons` pprr
WHERE `replacement_id` IS NOT null
AND `pprr`.`name` = 'pharmacist replacement';

# --- !Downs