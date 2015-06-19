-- After upgrading to fEMR 2.1.4, run these script to fix the null chief complaint foreign keys in patient encounter tab fields
-- Note: this has not been tested on custom fields and custom tabs (shouldn't make a difference though)

UPDATE patient_encounter_tab_fields tf
  INNER JOIN (
               SELECT
                 cc.id   AS cc_id,
                 petf.id AS petf_id
               FROM patient_encounter_tab_fields AS petf
                 INNER JOIN tab_fields AS tf
                   ON tf.id = petf.tab_field_id
                 INNER JOIN chief_complaints AS cc
                   ON cc.patient_encounter_id = petf.patient_encounter_id
                 INNER JOIN tabs t
                   ON tf.tab_id = t.id
               WHERE EXISTS
                     (
                         SELECT id
                         FROM chief_complaints AS cc
                         WHERE cc.patient_encounter_id = petf.patient_encounter_id
                     )
                     AND petf.chief_complaint_id IS NULL
                     AND t.name = "HPI"
             ) AS null_cc
    ON null_cc.petf_id = tf.id
SET tf.chief_complaint_id = null_cc.cc_id;
