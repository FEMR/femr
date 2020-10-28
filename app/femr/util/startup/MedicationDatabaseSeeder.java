/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.util.startup;

import io.ebean.Ebean;
import com.google.inject.Inject;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.concepts.ConceptMedicationForm;
import femr.data.models.mysql.concepts.ConceptMedicationUnit;
import femr.data.models.mysql.concepts.ConceptPrescriptionAdministration;
import femr.data.models.mysql.concepts.ConceptMedication;
import femr.data.models.mysql.concepts.ConceptMedicationGeneric;
import femr.data.models.mysql.concepts.ConceptMedicationGenericStrength;
import org.apache.commons.codec.binary.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MedicationDatabaseSeeder {

    private final IRepository<IMedication> conceptMedicationRepository;
    private final IRepository<IMedicationGeneric> conceptMedicationGenericRepository;
    private final IRepository<IMedicationGenericStrength> conceptMedicationGenericStrengthRepository;
    private final IRepository<IConceptMedicationUnit> conceptMedicationUnitRepository;
    private final IRepository<IConceptMedicationForm> conceptMedicationFormRepository;
    private final IRepository<IConceptPrescriptionAdministration> conceptPrescriptionAdministrationRepository;

    @Inject
    public MedicationDatabaseSeeder(IRepository<IMedication> conceptMedicationRepository,
                                    IRepository<IMedicationGeneric> conceptMedicationGenericRepository,
                                    IRepository<IMedicationGenericStrength> conceptMedicationGenericStrengthRepository,
                                    IRepository<IConceptMedicationUnit> conceptMedicationUnitRepository,
                                    IRepository<IConceptMedicationForm> conceptMedicationFormRepository,
                                    //this needs to be removed
                                    IRepository<IConceptPrescriptionAdministration> conceptPrescriptionAdministrationRepository) {

        this.conceptMedicationRepository = conceptMedicationRepository;
        this.conceptMedicationGenericRepository = conceptMedicationGenericRepository;
        this.conceptMedicationGenericStrengthRepository = conceptMedicationGenericStrengthRepository;
        this.conceptMedicationUnitRepository = conceptMedicationUnitRepository;
        this.conceptMedicationFormRepository = conceptMedicationFormRepository;
        this.conceptPrescriptionAdministrationRepository = conceptPrescriptionAdministrationRepository;

        this.seed();
    }

    private void seed() {

        //prescription concepts
        seedConceptPrescriptionAdministrations();
        //medication concepts
        seedConceptMedicationUnits();
        seedConceptMedicationForms();
        seedConceptMedicationGenerics();
        seedConceptMedicationGenericStrengths();
        seedConceptMedications();
    }

    private void seedConceptPrescriptionAdministrations() {
        List<? extends IConceptPrescriptionAdministration> administrations = conceptPrescriptionAdministrationRepository.findAll(ConceptPrescriptionAdministration.class);
        List<ConceptPrescriptionAdministration> conceptPrescriptionAdministrationsToAdd = new ArrayList<>();

        if (administrations != null) {
            ConceptPrescriptionAdministration conceptPrescriptionAdministration;

            /* Daily modifier is used as helper in the calculation when prescribing a medication.
             * It is how many times per day it should be taken. This modifier is then multiplied by
             * the amount of days the prescriber sets. The total can be adjusted */

            if (!containConceptPrescriptionAdministration(administrations, "alt")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("alt");
                conceptPrescriptionAdministration.setDailyModifier(0.5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "BID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("BID");
                conceptPrescriptionAdministration.setDailyModifier(2f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "BIW")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("BIW");
                conceptPrescriptionAdministration.setDailyModifier(0.2857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "CID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("CID");
                conceptPrescriptionAdministration.setDailyModifier(5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "HS")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("HS");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q12h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q12h");
                conceptPrescriptionAdministration.setDailyModifier(2f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q24h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q24h");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q4-6h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q4-6h");
                conceptPrescriptionAdministration.setDailyModifier(5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q4h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q4h");
                conceptPrescriptionAdministration.setDailyModifier(6f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q6h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q6h");
                conceptPrescriptionAdministration.setDailyModifier(4f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q8h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q8h");
                conceptPrescriptionAdministration.setDailyModifier(3f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qAM")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qAM");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qd")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qd");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qHS")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qHS");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "QID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("QID");
                conceptPrescriptionAdministration.setDailyModifier(4f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q5min")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q5min");
                conceptPrescriptionAdministration.setDailyModifier(288f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qOd")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qOd");
                conceptPrescriptionAdministration.setDailyModifier(0.5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qPM")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qPM");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q week")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q week");
                conceptPrescriptionAdministration.setDailyModifier(0.142857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "TID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("TID");
                conceptPrescriptionAdministration.setDailyModifier(3f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "TIW")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("TIW");
                conceptPrescriptionAdministration.setDailyModifier(0.42857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }


            //a whole bunch of if statements to fix the problem with the daily modifier turning to 0 when navigating through evolutions
            for (IConceptPrescriptionAdministration existingConceptPrescriptionAdministrations : administrations) {

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "alt") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "BID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(2f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "BIW") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.2857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "CID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "HS") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q12h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(2f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q24h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q4-6h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q4h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(6f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q6h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(4f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q8h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(3f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qAM") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qd") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qHS") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "QID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(4f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q5min") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(288f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qOd") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qPM") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q week") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.142857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "TID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(3f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "TIW") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.42857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

            }

            conceptPrescriptionAdministrationRepository.createAll(conceptPrescriptionAdministrationsToAdd);
        }
    }

    private void seedConceptMedicationUnits() {
        List<? extends IConceptMedicationUnit> medicationUnits = conceptMedicationUnitRepository.findAll(ConceptMedicationUnit.class);

        List<ConceptMedicationUnit> newconceptMedicationUnits = new ArrayList<>();
        ConceptMedicationUnit conceptMedicationUnit;
        if (medicationUnits != null && !containUnit(medicationUnits, "%")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("%");
            conceptMedicationUnit.setDescription("g/dL");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "g")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("g");
            conceptMedicationUnit.setDescription("gram");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "gr")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("gr");
            conceptMedicationUnit.setDescription("grain");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "IU")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("IU");
            conceptMedicationUnit.setDescription("international units");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "mg")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("mg");
            conceptMedicationUnit.setDescription("milligram");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "U")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("U");
            conceptMedicationUnit.setDescription("unit");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "oz")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("oz");
            conceptMedicationUnit.setDescription("ounces");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "mL")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("mL");
            conceptMedicationUnit.setDescription("milliliter");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "mcg")) {
            conceptMedicationUnit = new ConceptMedicationUnit();
            conceptMedicationUnit.setName("mcg");
            conceptMedicationUnit.setDescription("microgram");
            conceptMedicationUnit.setIsDeleted(false);
            newconceptMedicationUnits.add(conceptMedicationUnit);
        }

        conceptMedicationUnitRepository.createAll(newconceptMedicationUnits);
    }

    private void seedConceptMedicationForms() {

        List<? extends IConceptMedicationForm> conceptMedicationForms = conceptMedicationFormRepository.findAll(ConceptMedicationForm.class);

        List<ConceptMedicationForm> newMedicationForms = new ArrayList<>();
        ConceptMedicationForm conceptMedicationForm;
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "B/S")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("B/S");
            conceptMedicationForm.setDescription("bite and swallow");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "caps")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("caps");
            conceptMedicationForm.setDescription("capsules");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "crm")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("crm");
            conceptMedicationForm.setDescription("cream");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "elix")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("elix");
            conceptMedicationForm.setDescription("elixir");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "gtts")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("gtts");
            conceptMedicationForm.setDescription("drops");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "MDI")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("MDI");
            conceptMedicationForm.setDescription("metered dose inhaler");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "nebs")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("nebs");
            conceptMedicationForm.setDescription("solution for nebulization");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "NPO")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("NPO");
            conceptMedicationForm.setDescription("nothing by mouth");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "PO")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("PO");
            conceptMedicationForm.setDescription("by mouth, orally , or swallowed");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "PR")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("PR");
            conceptMedicationForm.setDescription("suppository");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "SL")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("SL");
            conceptMedicationForm.setDescription("sublingual form");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "soln")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("soln");
            conceptMedicationForm.setDescription("solution");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "supp")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("supp");
            conceptMedicationForm.setDescription("suppository");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "susp")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("susp");
            conceptMedicationForm.setDescription("suspension");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "syr")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("syr");
            conceptMedicationForm.setDescription("syrup");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "tabs")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("tabs");
            conceptMedicationForm.setDescription("tablets");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "tab chew")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("tab chew");
            conceptMedicationForm.setDescription("Chewable Tablets");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "ung")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("ung");
            conceptMedicationForm.setDescription("ointment");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "lotion")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("lotion");
            conceptMedicationForm.setDescription("lotion");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "inj")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("inj");
            conceptMedicationForm.setDescription("injection");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }

        conceptMedicationFormRepository.createAll(newMedicationForms);
    }

    private void seedConceptMedicationGenerics(){
        List<? extends IMedicationGeneric> conceptMedicationGenerics = conceptMedicationGenericRepository.findAll(ConceptMedicationGeneric.class);

        List<ConceptMedicationGeneric> newConceptMedicationGenerics = new ArrayList<>();

        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "acetaminophen"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "acetic acid"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "albendazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "albuterol"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "amlodipine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "amoxicillin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ampicillin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "aspirin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "atenolol"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "azithromycin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "bacitracin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "bacitracin/neomycin/polymyxin b"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "bisacodyl"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "bismuth subsalicylate"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "budesonide"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "calamine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "calcium cargonate"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "cefdinir"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "cefprozil"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ceftriaxone"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "cephalexin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "clarithromycin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "clavulanic acid"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "clotrimazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "diltiazem"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "diphenhydramine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "docusate"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "doxycycline"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "econazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "electrolytes"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "famotidine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "fluconazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "folic acid"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "glipizide"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "glycerin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "guaifenesin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "hydrocortisone"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "hydroxychloroquine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ibuprofen"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ivermectin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "levofloxacin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "loperamide"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "loratadine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "menthol"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "metformin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "methylsalicylate"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "metoclopramide"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "metoprolol"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "metronidazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "montelukast"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "naproxen"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "nitrofurantoin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "omeprazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ondansetron"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "permethrin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "prednisolone"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "prednisone"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "promethazine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "ranitidine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "sennosides"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "sulfamethoxazole"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "terazosin"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "terbinafine"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "trimethoprim"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "valacyclovir"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "a liquid"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "zafirlukast"));
        newConceptMedicationGenerics.add(addConceptMedicationGeneric(conceptMedicationGenerics, "zinc"));
        newConceptMedicationGenerics.removeIf(Objects::isNull);//remove the null stragglers cause eBean don't like em

        if (newConceptMedicationGenerics.size() > 0)
            conceptMedicationGenericRepository.createAll(newConceptMedicationGenerics);
    }

    /**
     * Seed the concept dictionary for ConceptMedicationGenericStrengths. A Generic Name must already exist from seedConceptMedicationGenerics()
     * or you will get an error.
     *
     * Contains:
     * medication unit (mg, %, mcg, etc)
     * medication generic (acetaminophen, amoxicillin, etc)
     * isDenominator (mL = true)
     * value (500.0. 325.0, 0.83 etc)
     */
    private void seedConceptMedicationGenericStrengths(){

        List<? extends IMedicationGenericStrength> conceptMedicationGenericStrengths = conceptMedicationGenericStrengthRepository.findAll(ConceptMedicationGenericStrength.class);
        Map<String, Integer> conceptMedicationUnitMap = getAvailableConceptMedicationUnits();
        Map<String, Integer> conceptMedicationGenericMap = getAvailableConceptMedicationGenerics();

        List<ConceptMedicationGenericStrength> newConceptMedicationGenericStrengths = new ArrayList<>();

        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "acetaminophen", "mg", 160.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "acetaminophen", "mg", 325.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "acetaminophen", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "acetic acid", "%", 2.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "albendazole", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "albendazole", "mg", 400.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "albuterol", "%", 0.083));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "albuterol", "mcg", 90.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "amlodipine", "mg", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "amoxicillin", "mg", 125.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "amoxicillin", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "amoxicillin", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "amoxicillin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ampicillin", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "aspirin", "mg", 325.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "aspirin", "mg", 81.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "atenolol", "mg", 50.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "azithromycin", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "azithromycin", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "azithromycin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "bacitracin", "g", 0.9));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "bacitracin", "g", 28.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "bacitracin/neomycin/polymyxin b", "mg", 9.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "bisacodyl", "mg", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "bismuth subsalicylate", "mg", 262.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "budesonide", "mcg", 180.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "calamine", "%", 8.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "calcium cargonate", "mg", 1000.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "calcium cargonate", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "calcium cargonate", "mg", 750.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "cefdinir", "mg", 125.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "cefprozil", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ceftriaxone", "g", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ceftriaxone", "g", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "cephalexin", "mg", 125.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "cephalexin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "clarithromycin", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "clarithromycin", "mg", 300.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "clarithromycin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "clavulanic acid", "mg", 28.5));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "clotrimazole", "%", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "diltiazem", "mg", 180.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "diltiazem", "mg", 240.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "diphenhydramine", "mg", 12.5));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "diphenhydramine", "mg", 25.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "docusate", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "docusate", "mg", 50.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "doxycycline", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "doxycycline", "mg", 150.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "doxycycline", "mg", 75.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "econazole", "mg", 85.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "electrolytes", "oz", 16.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "famotidine", "mg", 20.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "fluconazole", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "folic acid", "mg", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "glipizide", "mg", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "glycerin", "%", 50.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "guaifenesin", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "guaifenesin", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "guaifenesin", "mg", 400.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "hydrocortisone", "%", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "hydrocortisone", "%", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "hydroxychloroquine", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ibuprofen", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ibuprofen", "mg", 200.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ivermectin", "mg", 6.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "levofloxacin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "loperamide", "mg", 2.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "loratadine", "mg", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "loratadine", "mg", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "menthol", "%", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "metformin", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "methylsalicylate", "%", 15.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "metoclopramide", "mg", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "metoprolol", "mg", 50.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "metronidazole", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "metronidazole", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "montelukast", "mg", 4.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "montelukast", "mg", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "naproxen", "mg", 220.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "naproxen", "mg", 500.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "nitrofurantoin", "mg", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "omeprazole", "mg", 20.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ondansetron", "mg", 4.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ondansetron", "mg", 8.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "permethrin", "%", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "permethrin", "%", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "prednisolone", "mg", 15.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "prednisone", "mg", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "promethazine", "mg", 50.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "ranitidine", "mg", 150.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "sennosides", "mg", 8.6));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "sulfamethoxazole", "mg", 160.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "sulfamethoxazole", "mg", 80.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "terazosin", "mg", 2.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "terbinafine", "mg", 250.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "trimethoprim", "mg", 400.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "trimethoprim", "mg", 800.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "valacyclovir", "g", 1.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "a liquid", "mL", 10.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "a liquid", "mL", 100.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "a liquid", "mL", 5.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "zafirlukast", "mg", 20.0));
        newConceptMedicationGenericStrengths.add(addMedicationGenericStrength(conceptMedicationGenericStrengths, conceptMedicationUnitMap, conceptMedicationGenericMap, "zinc", "mg", 220.0));
        newConceptMedicationGenericStrengths.removeIf(Objects::isNull);//remove the null stragglers cause eBean don't like em



        if (newConceptMedicationGenericStrengths.size() > 0)
            conceptMedicationGenericStrengthRepository.createAll(newConceptMedicationGenericStrengths);
    }

    /**
     * Adds a generic strength to the list of generic strengths to save in the database.
     *
     * @param conceptMedicationGenericStrengths a list of the existing generic strengths, not null
     * @param conceptMedicationUnitMap a map of the existing medication units, not null
     * @param conceptMedicationGenericMap a map of the existing medication generics, not null
     * @param genericName the name of the generic for which you want to add a strength to, not null
     * @param genericUnit the unit of the new generic strength, not null
     * @param value value of the medication (strength of the ingredient), not null
     * @return a new ConceptMedicationGenericStrength if it is not already in the database, or null if it is/errors occur
     */
    private ConceptMedicationGenericStrength addMedicationGenericStrength(List<? extends IMedicationGenericStrength> conceptMedicationGenericStrengths,
                                                                          Map<String, Integer> conceptMedicationUnitMap,
                                                                          Map<String, Integer> conceptMedicationGenericMap,
                                                                          String genericName,
                                                                          String genericUnit,
                                                                          Double value){
        if (conceptMedicationGenericStrengths == null || conceptMedicationUnitMap == null || conceptMedicationGenericMap == null || genericName == null || genericUnit == null || value == null){
            return null;
        }

        ConceptMedicationGenericStrength conceptMedicationGenericStrength = null;

        if (conceptMedicationGenericStrengths != null &&
                !containConceptGenericStrength(conceptMedicationGenericStrengths, conceptMedicationGenericMap.get(genericName), conceptMedicationUnitMap.get(genericUnit), value)){

            conceptMedicationGenericStrength = new ConceptMedicationGenericStrength();
            conceptMedicationGenericStrength.setMedicationGeneric(Ebean.getReference(ConceptMedicationGeneric.class, conceptMedicationGenericMap.get(genericName)));
            conceptMedicationGenericStrength.setConceptMedicationUnit(Ebean.getReference(ConceptMedicationUnit.class, conceptMedicationUnitMap.get(genericUnit)));
            if (Objects.equals(genericUnit, "mL"))
                conceptMedicationGenericStrength.setDenominator(true);
            else
                conceptMedicationGenericStrength.setDenominator(false);
            conceptMedicationGenericStrength.setValue(value);
        }

        return conceptMedicationGenericStrength;
    }

    /**
     * Checks to see if the generic strength already exists in the database.
     *
     * @param conceptMedicationGenericStrengths a list of all generic strengths
     * @param genericId id of the generic medication name
     * @param unitId id of the generic medication unit
     * @param value value of the medication (strength of the ingredient)
     * @return true if an error or if it exists, false otherwise
     */
    private static boolean containConceptGenericStrength(List<? extends IMedicationGenericStrength> conceptMedicationGenericStrengths, int genericId, int unitId, Double value){
        for (IMedicationGenericStrength mgs : conceptMedicationGenericStrengths){
            if (mgs.getConceptMedicationUnit() == null || mgs.getMedicationGeneric() == null)
                return true;//error
            if (Objects.equals(mgs.getValue(), value) && mgs.getConceptMedicationUnit().getId() == unitId && mgs.getMedicationGeneric().getId() == genericId){
                return true;
            }
        }
        return false;
    }

    /**
     * Used to verify a generic name does not already exist in the database.
     *
     * @param conceptMedicationGenerics list of all of the generic medications in the database, not null
     * @param name name of the generic medication to maybe add, not null
     * @return a new ConceptMedicationGeneric if the name doesn't already exist or null if errors/it already exists
     */
    private ConceptMedicationGeneric addConceptMedicationGeneric(List<? extends IMedicationGeneric> conceptMedicationGenerics, String name){
        if (name == null || conceptMedicationGenerics == null)
            return null;

        for (IMedicationGeneric mg : conceptMedicationGenerics){
            if (mg.getName().equals(name)){
                return null;
            }
        }
        ConceptMedicationGeneric conceptMedicationGeneric = new ConceptMedicationGeneric();
        conceptMedicationGeneric.setName(name);

        return conceptMedicationGeneric;
    }

    private void seedConceptMedications(){

        List<? extends IMedication> conceptMedications = conceptMedicationRepository.findAll(ConceptMedication.class);
        Map<String, Integer> conceptMedicationFormMap = getAvailableConceptMedicationForms();
        List<? extends IMedicationGenericStrength> conceptMedicationGenericStrengths = conceptMedicationGenericStrengthRepository.findAll(ConceptMedicationGenericStrength.class);
        List<IMedicationGenericStrength> conceptMedicationGenericStrengthsToAdd;
        List<ConceptMedication> newConceptMedications = new ArrayList<>();


        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "acetaminophen", 325.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tylenol", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "acetaminophen", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tylenol", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "albendazole", 200.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Albenza", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "albendazole", 400.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Albenza", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "albuterol", 0.083, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Proventil", "nebs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "albuterol", 90.0, "mcg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Proventil", "MDI"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amlodipine", 5.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Norvasc", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amoxicillin", 125.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Amoxil", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amoxicillin", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Amoxil", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amoxicillin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Amoxil", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ampicillin", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Principen", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "aspirin", 325.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bayer", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "aspirin", 81.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bayer", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "atenolol", 50.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tenormin", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "azithromycin", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zithromax", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "azithromycin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zithromax", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "bacitracin", 0.9, "g"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bacitracin", "ung"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "bacitracin", 28.0, "g"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bacitracin", "ung"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "bacitracin/neomycin/polymyxin b", 9.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Triple Antibiotic", "ung"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "bisacodyl", 5.0, "g"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Dulcolax", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "bismuth subsalicylate", 262.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Pepto Bismol", "tab chew"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "budesonide", 180.0, "mcg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Pulmicort", "MDI"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "calamine", 8.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Calamine", "lotion"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "calcium cargonate", 1000.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tums", "tab chew"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "calcium cargonate", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tums", "tab chew"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "calcium cargonate", 750.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tums", "tab chew"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "cefprozil", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Cefzil", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ceftriaxone", 1.0, "g"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Rocephin", "inj"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "cephalexin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Keflex", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "clarithromycin", 300.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Biaxin", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "clarithromycin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Biaxin", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "clotrimazole", 1.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Lotrimin", "crm"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "diltiazem", 180.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tiazac", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "diltiazem", 240.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tiazac", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "diphenhydramine", 25.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Benadryl", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "docusate", 100.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Colace", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "doxycycline", 100.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Vibramycin", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "doxycycline", 150.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Adoxa", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "doxycycline", 75.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Vibramycin", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "econazole", 85.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Spectazole", "crm"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "electrolytes", 16.0, "oz"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Pedialyte", "soln"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "famotidine", 20.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Pepcid", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "fluconazole", 100.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Diflucan", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "folic acid", 1.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Folvite", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "glipizide", 10.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Glucotrol", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "glycerin", 50.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Sani-Supp", "PR"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "guaifenesin", 200.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Mucinex", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "guaifenesin", 400.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Mucinex", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "hydrocortisone", 1.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Cortaid", "crm"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "hydroxychloroquine", 200.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Plaquenil", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ibuprofen", 200.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Advil", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ivermectin", 6.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Stromectol", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "levofloxacin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Levaquin", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "loperamide", 2.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Imodium", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "loratadine", 10.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Claritin", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "metformin", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Glucophage", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "metoclopramide", 5.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Metozolv ODT", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "metoprolol", 50.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Lopressor", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "metronidazole", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Flagyl", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "metronidazole", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Flagyl", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "montelukast", 4.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Singulair", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "montelukast", 5.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Singulair", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "naproxen", 220.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Aleve", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "naproxen", 500.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Aleve", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "nitrofurantoin", 100.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Furadantin", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "omeprazole", 20.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zegerid", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ondansetron", 4.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zofran", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ondansetron", 8.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zofran", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "permethrin", 1.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Elimite", "crm"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "permethrin", 5.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Acticin", "crm"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "prednisone", 10.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Rayos", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "promethazine", 50.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Phenergan", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ranitidine", 150.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zantac", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "terazosin", 2.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Hytrin", "caps"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "terbinafine", 250.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Lamisil", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "valacyclovir", 1.0, "g"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Valtrex", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "zafirlukast", 20.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Accolate", "tabs"));
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "zinc", 220.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zincfant", "tabs"));

        //start concept medications with multiple generics
        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "diphenhydramine", 12.5, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Benadryl", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "loratadine", 5.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Claritin", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "acetaminophen", 160.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Tylenol", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ibuprofen", 100.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Advil", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amoxicillin", 125.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Amoxil", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "amoxicillin", 200.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "clavulanic acid", 28.5, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Augmentin", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ampicillin", 250.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Principen", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "azithromycin", 200.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Zithromax", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "cefdinir", 125.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Omnicef", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "clarithromycin", 250.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Biaxin", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "trimethoprim", 400.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "sulfamethoxazole", 80.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bactrim", "tabs"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "trimethoprim", 800.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "sulfamethoxazole", 160.0, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bactrim", "tabs"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "docusate", 50.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "sennosides", 8.6, "mg"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Senokot", "tabs"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "menthol", 10.0, "%"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "methylsalicylate", 15.0, "%"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Bengay", "crm"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "ceftriaxone", 10.0, "g"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 100.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Rocephin", "inj"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "guaifenesin", 100.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Mucinex", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "hydrocortisone", 1.0, "%"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "acetic acid", 2.0, "%"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 10.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Vosol HC",	"susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "cephalexin", 125.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Keflex", "susp"));

        conceptMedicationGenericStrengthsToAdd = new ArrayList<>();
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "prednisolone", 15.0, "mg"));
        conceptMedicationGenericStrengthsToAdd.add(getConceptMedicationGenericStrength(conceptMedicationGenericStrengths, "a liquid", 5.0, "mL"));
        newConceptMedications.add(addConceptMedication(conceptMedications, conceptMedicationFormMap, conceptMedicationGenericStrengthsToAdd, "Prednisolone", "inj"));

        newConceptMedications.removeIf(Objects::isNull);
        if (newConceptMedications.size() > 0)
          conceptMedicationRepository.createAll(newConceptMedications);
    }

    /**
     * Puts a concept medication together for adding.
     *
     * @param conceptMedications a list of all currently available concept medications to compare against exisiting ones, not null
     * @param conceptMedicationFormMap a map of all medication forms available, not null
     * @param conceptMedicationGenericStrengths a list of the generic medications for the new concept, not null
     * @param brandName brand name of the new medication, may be null
     * @param form form of the new medication, may be null
     * @return a new ConceptMedication or null if errors or null if the concept medication already exists
     */
    private ConceptMedication addConceptMedication(List<? extends IMedication> conceptMedications,
                                                   Map<String, Integer> conceptMedicationFormMap,
                                                   List<IMedicationGenericStrength> conceptMedicationGenericStrengths,
                                                   String brandName,
                                                   String form){

        if (conceptMedications == null || conceptMedicationFormMap == null || conceptMedicationGenericStrengths == null){

            return null;
        }

        //sort by primary key to get an order that works for comparing
        Collections.sort(conceptMedicationGenericStrengths, (o1, o2) -> ((Integer)o1.getId()).compareTo(o2.getId()));

        //compare all of the medications
        for (IMedication medication : conceptMedications){
            List<IMedicationGenericStrength> medicationGenericStrengths = medication.getMedicationGenericStrengths();
            Collections.sort(medicationGenericStrengths, (o1, o2) -> ((Integer)o1.getId()).compareTo(o2.getId()));

            if (medicationGenericStrengths.equals(conceptMedicationGenericStrengths) &&
                    medication.getName().equals(brandName) &&
                    medication.getConceptMedicationForm() != null &&
                    medication.getConceptMedicationForm().getName().equals(form)){
                return null;
            } else if (femr.util.stringhelpers.StringUtils.isNullOrWhiteSpace(medication.getName())&& femr.util.stringhelpers.StringUtils.isNotNullOrWhiteSpace(brandName)) {
                // Everything is the same except the name, just update the name of the concept medication in the dictionary
                medication.setName(brandName);
                conceptMedicationRepository.update(medication);
                return null;
            }
        }


        ConceptMedication conceptMedication = new ConceptMedication();
        conceptMedication.setName(brandName);
        conceptMedication.setConceptMedicationForm(Ebean.getReference(ConceptMedicationForm.class, conceptMedicationFormMap.get(form)));
        conceptMedicationGenericStrengths.removeIf(Objects::isNull);
        conceptMedication.setMedicationGenericStrengths(conceptMedicationGenericStrengths);
        conceptMedication.setIsDeleted(false);
        if (conceptMedication.getMedicationGenericStrengths().size() > 0)
            conceptMedication.setMedicationGenericStrengths(conceptMedicationGenericStrengths);
        else
            conceptMedication = null;

        return conceptMedication;
    }

    /**
     * Gets a ConceptMedicationGenericStrength
     *
     * @param conceptMedicationGenericStrengths a list of all of the ConceptMedicationGenericStrength, not null
     * @param genericName generic name (diphenydramine, acetaminophen, etc), not null
     * @param value value of the generic medication (25.0, 325.0, etc), not null
     * @param unit unit of the generic medication (mg, mcg, %, etc), not null
     * @return a new ConceptMedicationGenericStrength or null if an error occurs. If more than one are found, the first is
     * returned. This shouldn't happen.
     */
    private IMedicationGenericStrength getConceptMedicationGenericStrength(List<? extends IMedicationGenericStrength> conceptMedicationGenericStrengths,
                                                   String genericName,
                                                   Double value,
                                                   String unit){

        if (conceptMedicationGenericStrengths == null || genericName == null || value == null || unit == null){

            return null;
        }

        IMedicationGenericStrength conceptMedicationGenericStrength;
        List<IMedicationGenericStrength> medicationGenericStrengths = conceptMedicationGenericStrengths.stream()
                .filter(cmgs -> cmgs.getValue().equals(value) &&
                        cmgs.getConceptMedicationUnit().getName().equals(unit) &&
                        cmgs.getMedicationGeneric().getName().equals(genericName))
                .collect(Collectors.toList());
        if (medicationGenericStrengths == null || medicationGenericStrengths.size() == 0) {

            conceptMedicationGenericStrength = null;
        } else {

            conceptMedicationGenericStrength = medicationGenericStrengths.get(0);
        }



        return conceptMedicationGenericStrength;
    }

    /**
     * Maps all of the unique medication form names to their primary key so that they can
     * be referenced by eBean as needed while building the initial concept dictionary.
     */
    private Map<String, Integer> getAvailableConceptMedicationForms(){

        List<? extends IConceptMedicationForm> medicationForms = conceptMedicationFormRepository.findAll(ConceptMedicationForm.class);
        Map<String, Integer> conceptMedicationFormMap = new HashMap<>();
        for (IConceptMedicationForm conceptMedicationForm : medicationForms){
            conceptMedicationFormMap.put(conceptMedicationForm.getName(), conceptMedicationForm.getId());
        }
        return conceptMedicationFormMap;
    }

    /**
     * Maps all of the unique generic medications to their primary key so that they can
     * be referenced by eBean as needed while building the initial concept dictionary.
     */
    private Map<String, Integer> getAvailableConceptMedicationGenerics(){

        List<? extends IMedicationGeneric> medicationGenerics = conceptMedicationGenericRepository.findAll(ConceptMedicationGeneric.class);
        Map<String, Integer> conceptMedicationGenericMap = new HashMap<>();
        for (IMedicationGeneric conceptMedicationGeneric : medicationGenerics){
            conceptMedicationGenericMap.put(conceptMedicationGeneric.getName(), conceptMedicationGeneric.getId());
        }
        return conceptMedicationGenericMap;
    }

    /**
     * Maps all of the unique medication units to their primary key so that they can
     * be referenced by eBean as needed while building the initial concept dictionary.
     */
    private Map<String, Integer> getAvailableConceptMedicationUnits(){

        List<? extends IConceptMedicationUnit> medicationUnits = conceptMedicationUnitRepository.findAll(ConceptMedicationUnit.class);
        Map<String, Integer> conceptMedicationUnitMap = new HashMap<>();
        for (IConceptMedicationUnit conceptMedicationUnit : medicationUnits){
            conceptMedicationUnitMap.put(conceptMedicationUnit.getName(), conceptMedicationUnit.getId());
        }
        return conceptMedicationUnitMap;
    }



    private static boolean containConceptPrescriptionAdministration(List<? extends IConceptPrescriptionAdministration> administrations, String administration) {
        for(IConceptPrescriptionAdministration a : administrations) {
            if (a.getName().equals(administration)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containForm(List<? extends IConceptMedicationForm> conceptMedicationForms, String form) {
        for (IConceptMedicationForm mf : conceptMedicationForms) {
            if (mf.getName().equals(form)) {
                return true;
            }
        }
        return false;
    }





    private static boolean containUnit(List<? extends IConceptMedicationUnit> conceptMedicationUnits, String unit) {
        for (IConceptMedicationUnit mmu : conceptMedicationUnits) {
            if (mmu.getName().equals(unit)) {
                return true;
            }
        }
        return false;
    }
}
