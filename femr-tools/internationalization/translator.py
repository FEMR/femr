import ssl
import argostranslate.package
import argostranslate.translate
from googletrans import Translator
import json

ssl._create_default_https_context = ssl._create_unverified_context

# Define input values as a list of tuples (label, input_text)
input_values = [
("home_index_h2_Welcome", "Welcome to fEMR 3.0.0,"),
("home_index_p_Please", "Please select a tab at the top to get started!"),
("home_index_p_Search", "Search Below:"),
("home_index_div_ID", "ID, Name, or Phone #"),
("langCode_triage", "Triage"),
("langCode_medical", "Medical"),
("langCode_pharmacy", "Pharmacy"),
("langCode_research", "Research"),
("langCode_manager", "Manager"),
("langCode_admin", "Admin"),
("langCode_superUser", "SuperUser"),
("langCode_reference", "Reference"),
("langCode_settings", "Settings"),
("langCode_feedbackBtn", "Leave Feedback"),
("langCode_googleChrome", "Designed for Google Chrome"),
("triage_index_p_That", "That patient could not be found."),
("triage_index_p_Patient", "Patient has an open encounter"),
("triage_index_a_Go", "Go To Medical"),
("triage_index_h2_Check", "Check In - Triage"),
("triage_index_h2_General", "General Info"),
("triage_index_b_patientDemographics", "Complete form with patient demographics as instructed. Any box with an asterix (*) is required. Shared contact information would be if two patients have a household phone or email that they share, for example."),
("triage_index_firstName", "First Name"),
("triage_index_lastName", "Last Name"),
("triage_index_phoneNumber", "Phone Number"),
("triage_index_address", "Address"),
("triage_index_button_reset_fields", "Reset Fields"),
("triage_index_button_submit_patient", "Submit Patient"),
("triage_index_That", "That patient could not be found."),
("triage_index_Patient", "Patient has an open encounter"),
("triage_index_Go", "Go To Medical"),
("triage_index_Check", "Check In - Triage"),
("triage_index_General", "General Info"),
("triage_index_City", "City"),
("triage_index_Age", "Age"),
("triage_index_Years", "Years"),
("triage_index_Month", "Months"),
("triage_index_Birth", "Birth Date"),
("triage_index_Gender", "Gender"),
("triage_index_Male", "Male"),
("triage_index_Female", "Female"),
("triage_index_Birth1", "Birth Date and Age group are conflicting"),
("triage_index_OR", "OR"),
("triage_index_Patient1", "Patient Photo"),
("triage_file_input_button_label", "Patient Photo"),
("triage_file_input_no_file_chosen_text", "Patient Photo"),
("triage_warning_min", "Should be greater than:"),
("triage_warning_max", "and should be less than:"),
("triage_index_Vitals", "Vitals"),
("triage_index_Temperature", "Temperature"),
("triage_index_Blood", "Blood Pressure"),
("triage_index_Systolic", "Systolic"),
("triage_index_Diastolic", "Diastolic"),
("triage_index_Heart", "Heart Rate"),
("triage_index_Respirations", "Respirations"),
("triage_index_Weeks", "Weeks Pregnant"),
("triage_index_Weeks1", "Weeks"),
("triage_index_History", "History of:"),
("triage_index_Diabetes", "Diabetes"),
("triage_index_Hypertension", "Hypertension"),
("triage_index_High", "High Cholesterol"),
("triage_index_Tobacco", "Tobacco Use Disorder"),
("triage_index_Substance_Alcohol", "Substance/Alcohol Abuse"),
("triage_index_Oxygen", "Oxygen Saturation"),
("triage_index_Height", "Height"),
("triage_index_Weight", "Weight"),
("triage_index_BMI", "BMI"),
("triage_index_Glucose", "Glucose"),
("triage_index_Did", "Did you screen this patient for Diabetes?"),
("triage_index_Patient2", "Patient History"),
("triage_index_Chief", "Chief Complaint"),
("triage_index_No", "No file chosen"),
("triage_index_choose", "Choose File"),
("triage_index_Meters", "Meters"),
("triage_index_Centimeters", "Centimeters"),
("trips_cities_Add", "Add City"),
("trips_cities_City", "City:"),
("trips_cities_Country", "Country:"),
("trips_cities_Submit", "Submit"),
("trips_cities_tCity", "City"),
("trips_cities_tCountry", "Country"),
("trips_edit_Id", "Id:"),
("trips_edit_Team", "Team name:"),
("trips_edit_City", "City:"),
("trips_edit_Country", "Country:"),
("trips_edit_Start", "Start Date:"),
("trips_edit_End", "End Date:"),
("trips_edit_AddUsers", "Add users to this trip:"),
("trips_edit_Id_Add", "Add"),
("trips_edit_Id_RemoveUsers", "Remove users from this trip"),
("trips_edit_Id_Remove", "Remove"),
("trips_edit_Id_First", "First Name"),
("trips_edit_Id_Last", "Last Name"),
("trips_edit_Id_Email", "Email"),
("trips_edit_Id_About", "About"),
("trips_manage_Add", "Add Trip:"),
("trips_manage_Team", "Trip Team:"),
("trips_manage_TripCity", "Trip City:"),
("trips_manage_TripCountry", "Trip Country:"),
("trips_manage_Start1", "Start Date:"),
("trips_manage_End1", "End Date:"),
("trips_manage_Submit", "Submit"),
("trips_manage_Edit", "Edit"),
("trips_manage_Name", "Team Name"),
("trips_manage_City", "City"),
("trips_manage_Country", "Country"),
("trips_manage_Start2", "Start Date"),
("trips_manage_End2", "End Date"),
("trips_teams_Add", "Add Team"),
("trips_teams_TeamName1", "Team Name:"),
("trips_teams_Location", "Team Origin:"),
("trips_teams_Description1", "Team Description:"),
("trips_teams_Submit", "Submit"),
("trips_teams_TeamName2", "Team Name"),
("trips_teams_Description2", "Description"),
("trips_teams_Origin", "Origin"),
("feedback_feedback_Thanks", "Thanks for choosing to give feedback to fEMR. Please be honest and as thorough as possible to make sure we are able to understand and implement your feedback appropriately."),
("feedback_feedback_Submit", "Submit"),
("feedback_feedback_Confidentiality", "Your feedback is completely anonymous and will only be used to produce a better fEMR product."),
("inventory_custom_Add", "Add Custom Medicine to Formulary:"),
("inventory_custom_Exist", "Add Existing Medicine to Formulary:"),
("inventory_custom_Brand", "Brand"),
("inventory_custom_Form", "Form"),
("inventory_custom_Quantity", "Quantity"),
("inventory_custom_Generic", "Generic"),
("inventory_custom_Strength", "Strength"),
("inventory_custom_Unit", "Unit"),
("inventory_custom_Submit", "Submit"),
("inventory_existing_Add", "Add medications from fEMR's concept dictionary below"),
("inventory_existing_Dict", "If a medication does not exist in the concept directory, add it below"),
("inventory_existing_Adjust", "Adjust current/initial quality in the Formulary after adding"),
("inventory_existing_Submit", "Submit"),
("inventory_manage_Trip", "Select a different trip inventory to view:"),
("inventory_manage_Select", "Select"),
("inventory_manage_CurForm", "Current Formulary:"),
("inventory_manage_Add", "Add medications using the buttons above"),
("inventory_manage_Formulary", "Formulary only applies to Users on the trip mentioned above"),
("inventory_manage_Manually", "Manually edit Current/Initial quantity below"),
("inventory_manage_Quantity", "Quantity will be subtracted from the Current Quantity column after being dispensed in Pharmacy"),
("inventory_manage_Export", "Export your inventory data using the 'Export as CSV' button to the right"),
("inventory_manage_CSV1", "Export as CSV"),
("inventory_manage_CSV2", "Export as CSV"),
("inventory_manage_Medication", "Medication"),
("inventory_manage_CurQuan", "Current Quantity"),
("inventory_manage_InitQuan", "Initial Quantity"),
("inventory_manage_Date", "Date Added"),
("inventory_manage_Added", "Added By"),
("inventory_manage_Remove", "Remove"),
("inventory_manage_RemoveButton", "Remove"),
("updates_manage_Database", "Database Status"),
("updates_manage_Create", "Create Backup"),
("updates_manage_Network", "Network Status"),
("updates_manage_Available", "Available Updates"),
("updates_manage_Kit1", "Kit Update: Available"),
("updates_manage_Kit2", "Kit Update: Already Up to Date"),
("updates_manage_fEMR", "fEMR Kit Status"),
("admin_index_Welcome", "Welcome, Administrator. Choose an option to get started."),
("pharmacies_edit_List1", "List of Diagnoses"),
("pharmacies_edit_Pharmacy", "Pharmacy"),
("pharmacies_edit_Note", "Note"),
("pharmacies_edit_Prescriber", "Prescriber"),
("pharmacies_edit_List2", "List of Medications"),
("pharmacies_edit_Replace", "R"),
("pharmacies_edit_Current", "Current Quantity:"),
("pharmacies_edit_Prescription", "Prescription"),
("pharmacies_edit_Administration", "Administration"),
("pharmacies_edit_Days", "Days"),
("pharmacies_edit_Amount", "Amount"),
("pharmacies_edit_The", "The patient was counseled on the risks and side effects of the medications dispensed."),
("pharmacies_edit_Submit", "Submit"),
("pharmacies_edit_Patient", "Patient History"),
("pharmacies_edit_View", "View in Medical"),
("configure_manage_Feature", "Feature"),
("configure_manage_Description", "Description"),
("configure_manage_Toggle", "Toggle"),
("configure_manage_Save", "Save"),
("ageClassification_infant", "Infant 0-1"),
("ageClassification_child", "Child 2-12"),
("ageClassification_teen", "Teen 13-17"),
("ageClassification_adult", "Adult 18-64"),
("ageClassification_elder", "Elder 65+"),
("admin_welcome", "Welcome, Administrator. Choose an option to get started."),
("admin_configure", " Configure"),
("admin_inventory", " Inventory"),
("admin_trips", " Trips"),
("admin_users", " Users"),
("admin_updates", " Updates"),
("admin_title", "Admin Panel"),
("manager_trip_message", "User is not currently assigned to a trip. To manage trips please have an Admin visit the"),
("manager_trip_link", " Trip Page"),
("manager_patients_overview", "Overview of patients checked in today: "),
("patient_id", "Patient ID"),
("search_btn", "Search"),
("title_medical_search", "Medical Search"),
("title_pharmacy_search", "Pharmacy Search"),
("feedback_header", "Give Feedback"),
("feedback_welcome_message", "Thanks for choosing to give feedback to fEMR. Please be honest and as thorough as possible to make sure we are able to understand and implement your feedback appropriately."),
("feedback_disclaimer", "Your feedback is completely anonymous and will only be used to produce a better fEMR product."),
("feedback_submit", "Submit"),
("trips_manage_trips_title", "Trips - Manage Trips"),
("add_trip_header", "Add Trip"),
("trip_team_label", "Trip Team"),
("trip_city_label", "Trip City"),
("trip_country_label", "Trip Country"),
("start_date_label", "Start Date"),
("end_date_label", "End Date"),
("submit_button", "Submit"),
("table_header_edit", "Edit"),
("table_header_team", "Team Name"),
("table_header_country", "Country"),
("table_header_city", "City"),
("table_header_start_date", "Start Date"),
("table_header_end_date", "End Date"),
("graph_instructions", "To view a graph, from the sidebar on the left:"),
("choose_dataset", "Choose Dataset(s)"),
("choose_graph_type", "Choose a Graph Type"),
("choose_filters", "Choose Filters"),
("click_apply", "Click Apply"),
("export_data_instructions", "To export data, click the 'Export Data' button. The exported data will be de-identified and it will have all selected Datasets and Filters applied to it."),
("research_header", "Research"),
("primary_dataset", "Primary Dataset"),
("demographics_title", "Demographics"),
("age", "Age"),
("gender", "Gender"),
("height", "Height"),
("weight", "Weight"),
("pregnancy_status", "Pregnancy Status"),
("weeks_pregnant", "Weeks Pregnant"),
("medication_title", "Medication"),
("prescribed_meds", "Prescribed Medications"),
("dispensed_meds", "Dispensed Medications"),
("vitals_title", "Vitals"),
("temperature", "Temperature"),
("bp_systolic", "Blood Pressure Systolic"),
("bp_diastolic", "Blood Pressure Diastolic"),
("heart_rate", "Heart Rate"),
("respiratory_rate", "Respirations"),
("oxygen_saturation", "Oxygen Saturation"),
("glucose", "Glucose"),
("secondary_dataset", "Secondary Dataset"),
("none", "None"),
("graph_type", "Graph Type"),
("bar_graph", "Bar Graph"),
("pie_graph", "Pie Graph"),
("line_graph", "Line Graph"),
("scatterplot", "Scatterplot"),
("stacked_bar_graph", "Stacked Bar Graph"),
("grouped_bar_graph", "Grouped Bar Graph"),
("table_graph", "Table"),
("filter_date", "Filter Date (DD/MM/YYYY)"),
("start_date", "Start Date (DD/MM/YYYY)"),
("end_date", "End Date (DD/MM/YYYY)"),
("filter_trip", "Filter Trip"),
("select_trip", "-- Select Trip --"),
("filter_primary_dataset", "Filter Primary Dataset"),
("group_primary", "Group Primary"),
("start", "Start"),
("end", "End"),
("clear", "Clear"),
("apply", "Apply"),
("manage_trips", "Manage Trips"),
("manage_teams", "Manage Teams"),
("manage_cities", "Manage Cities"),
("no_trip_assigned_title", "Your account is not assigned to a trip"),
("no_trip_assigned_text", "In order to manage your formulary, you must be assigned to a trip. Please assign yourself to a trip before using the inventory feature."),
("manage_trip_users", "Manage Trip Users »"),
("formulary_view_info", "You are viewing the formulary for"),
("settings_title", "Settings"),
("settings_language", "Language Preference"),
("partials_medical_tabs_hpi_onset", "Onset"),
("partials_medical_tabs_hpi_Radiation", "Radiation"),
("partials_medical_tabs_hpi_Quality", "Quality"),
("partials_medical_tabs_hpi_Provokes", "Provokes"),
("partials_medical_tabs_hpi_Palliates", "Palliates"),
("partials_medical_tabs_hpi_Time", "Time Of Day"),
("partials_medical_tabs_hpi_Narrative", "Narrative"),
("partials_medical_tabs_hpi_Physical", "Physical Examination"),
("partials_medical_tabs_hpi_severity", "Severity"),
("medical_edit_Complaint", "Complaint"),
("medical_edit_Submit", "Submit Patient"),
("medical_edit_History", "Patient History"),
("medical_edit_View", "View in Pharmacy"),
("medical_edit_Record", "Record New Vitals"),
("medical_edit_HPI", "HPI"),
("medical_edit_Treatment", "Treatment"),
("medical_edit_PMH", "PMH"),
("medical_edit_Photos", "Photos"),
("partials_medical_tabs_treatment_Assessment", "Assessment"),
("partials_medical_tabs_treatment_Diagnosis", "Diagnosis"),
("partials_medical_tabs_treatment_Prescriptions", "Prescriptions"),
("partials_medical_tabs_treatment_Administration", "Administration"),
("partials_medical_tabs_treatment_Days", "Days"),
("partials_medical_tabs_treatment_Amount", "Amount"),
("partials_medical_tabs_treatment_looking", "Don't see what you're looking for? Only medications in stock will appear."),
("partials_medical_tabs_treatment_Procedure", "Procedure/Counseling"),
("partials_medical_tabs_treatment_Pharmacy", "Pharmacy Note"),
("partials_medical_tabs_pmh_Medical", "Medical/Surgical History"),
("partials_medical_tabs_pmh_Social", "Social History"),
("partials_medical_tabs_pmh_Medications", "Current Medications"),
("partials_medical_tabs_pmh_Family", "Family History"),
("partials_medical_tabs_photo_Add", "Add Photo:"),
("partials_medical_tabs_photo_Edit", "Edit Description"),
("partials_medical_tabs_photo_Delete", "Delete"),
("medical_vitals_Smoking", "Smoking"),
("medical_vitals_Diabetes", "Diabetes"),
("medical_vitals_Alcohol", "Alcohol"),
("medical_vitals_Cholesterol", "Cholesterol"),
("medical_vitals_Hypertension", "Hypertension"),
("medical_new_vitals_Smoking", "Smoking"),
("medical_new_vitals_systolic", "systolic"),
("medical_new_vitals_diastolic", "diastolic"),
("medical_new_vitals_Diabetes", "Diabetes"),
("medical_new_vitals_Alcohol", "Alcohol"),
("medical_new_vitals_Cholesterol", "Cholesterol"),
("medical_new_vitals_Hypertension", "Hypertension"),
("medical_new_vitals_Save", "Save"),
("medical_new_vitals_Cancel", "Cancel"),
("medical_new_vitals_weeks", "weeks"),
("partials_medical_Overview_Patient", "Patient Overview "),
("partials_medical_Overview_Name", "Name:"),
("partials_medical_Overview_Age", "Age:"),
("partials_medical_Overview_Sex", "Sex:"),
("partials_medical_Overview_City", "City:"),
("partials_medical_Overview_Pregnant", "Weeks Pregnant:"),
("history_patient_First", "First Name:"),
("history_patient_Last", "Last Name:"),
("history_patient_Phone", "Phone Number:"),
("history_patient_Address", "Address:"),
("history_patient_City", "City"),
("history_patient_Age", "Age"),
("history_patient_Sex", "Sex"),
("history_patient_Previous", "Previous Encounters"),
("history_patient_General", "General Info"),
("history_patient_Chief", "Chief complaint:"),
("history_patient_Click", "Click an encounter to view past history!"),
("history_patient_Delete", "Delete this Patient:"),
("history_patient_Medical", "View in Medical:"),
("history_patient_Pharmacy", "View in Pharmacy:"),
("history_patient_Triage", "See This Patient In Triage:"),
("history_patient_Patient", "Patient Id:"),
("export_data_button", "Export Data"),
("research_select_lang", "Select Language"),
("research_select_lang_sub", "-- Select Language --")
]

from_code = "en"
languages = ["en", "es", "fr", "ka", "ar", "az", "bg", "bn", "bs", "cs", "da", "de", "el", "et", "fa", "fi", "tl", "he", "hi", "hr", "hu", "id", "it", "ja", "ko", "pl", "pt", "ru", "sk", "sv", "tr", "vi", "zh"]

mode = "single" # single or multiple for single json file or multiple json files by language
# Initialize Google Translator
translator = Translator()

# Install the packages for argostranslate
argostranslate.package.update_package_index()
available_packages = argostranslate.package.get_available_packages()

# Store installed languages
installed_languages = []

for to_code in languages:
    # Find the package to install for argostranslate
    package_to_install = next(
        (pkg for pkg in available_packages if pkg.from_code == from_code and pkg.to_code == to_code), 
        None
    )
    
    if package_to_install is not None:
        argostranslate.package.install_from_path(package_to_install.download())
        installed_languages.append(to_code)  # Track installed languages
    else:
        # If no translation model is found for argostranslate, use google translate
        print(f"No translation model found for {from_code} to {to_code}. Will use google translate instead.")

with open("input/languages.json", "r", encoding="utf-8") as file:
        existing_translations = json.load(file)

# Initialize translations dictionary
translations = {}
for language in languages:
    # Use "fil" for Tagalog language code because in fEMR it's labeled as fil for Filipino
    lang_code = "fil" if language == "tl" else language

    # Initialize the language code in the translations dictionary
    translations[lang_code] = {}

    # Translate each input_text for the current language
    for label, input_text in input_values:
        if existing_translations[lang_code].get(label) == None:
            if language in installed_languages:
                # Argos Translate
                translated_text = argostranslate.translate.translate(input_text, from_code, language)
                print(f"{from_code} - > {language}, Argos translate: {input_text} -> {translated_text}")
            else:
                # Google Translate fallback
                translated_text = translator.translate(input_text, src=from_code, dest=language).text
                print(f"{from_code} - > {language}, Google translate: {input_text} -> {translated_text}")

            # Store the translated text in the translations dictionary
            translations[lang_code][label] = translated_text
        else:
            print(f"Translation for {label} already exists in {lang_code}. Skipping...")

    # If mode is multiple, save the translations to separate json files by language
    if mode == "multiple":
        lang_file = f'input/{lang_code}.json'
        try:
            with open(lang_file, 'r+', encoding='utf-8') as file:
                lang_data = json.load(file)
        except FileNotFoundError:
            lang_data = {}
            
        lang_data[lang_code].update(translations[lang_code])

        output_file = f'output/{lang_code}.json'
        with open(output_file, 'w', encoding='utf-8') as file:
            json.dump(lang_data, output_file, ensure_ascii=False, indent=4)
            print(f"Translation for {lang_code} completed.")

# If mode is single, merge translations to a single json file
if mode == "single":
    with open("input/languages.json", "r", encoding="utf-8") as file:
        existing_translations = json.load(file)

    # Merge translations
    for lang_code, translations in translations.items():
        if lang_code in existing_translations:
            existing_translations[lang_code].update(translations)
        else:
            existing_translations[lang_code] = translations

    # Save the updated translations back to language.json
    with open("output/languages.json", "w", encoding="utf-8") as file:
        json.dump(existing_translations, file, ensure_ascii=False, indent=4)
        
print("Translation completed. Check femr-tools/internationalization/output directory for the translated files.")