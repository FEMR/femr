import pytest
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.webdriver.support import expected_conditions as EC
import os
import requests

@pytest.fixture
def driver():
    if os.getenv("USE_REMOTE"):
        driver_address = os.getenv("SELENIUM_ADDRESS")
        assert driver_address is not None, "SELENIUM_ADDRESS environment variable not set"
        options = webdriver.ChromeOptions()
        drvr = webdriver.Remote(command_executor=driver_address, options=options)
    else:
        drvr = webdriver.Chrome()
    yield drvr
    drvr.quit()


def test_femr_is_alive():
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    response = requests.get(femr_address)
    assert response.status_code == 200


def test_can_login_and_logout_to_admin(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    # Test Login
    driver.set_window_size(1362, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("admin")
    driver.find_element(By.NAME, "password").send_keys("admin")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    assert "Welcome to fEMR" in driver.find_element(By.ID, "home_index_h2_Welcome").text

    # Test Logout
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"

def test_triage_no_photo(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    # Test logging in as testnurse:
    driver.set_window_size(1362, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("testnurse")
    driver.find_element(By.NAME, "password").send_keys("testnurse")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    assert "Welcome to fEMR" in driver.find_element(By.ID, "home_index_h2_Welcome").text

    # Test going to Triage page:
    driver.find_element(By.CSS_SELECTOR, "#langCode_triage").click()
    assert "Triage" in driver.find_element(By.ID, "triage_index_h2_Check").text

    # Enter test patient info
    driver.find_element(By.ID, "firstName").send_keys("NoPhotoTriageTester")
    driver.find_element(By.ID, "lastName").send_keys("Tester")
    #driver.find_element(By.ID, "").send_keys("")
    driver.find_element(By.ID, "city").send_keys("Port-au-Prince")
    driver.find_element(By.ID, "yearsInput").send_keys("22")
    driver.find_element(By.ID, "monthsInput").send_keys("3")
    driver.find_element(By.CSS_SELECTOR, "label.btn.btn-default.width-50").click()
    # No patient photo for this test.
    driver.find_element(By.ID, "temperature").send_keys("36")
    driver.find_element(By.ID, "bloodPressureSystolic").send_keys("90")
    driver.find_element(By.ID, "bloodPressureDiastolic").send_keys("90")
    driver.find_element(By.ID, "heartRate").send_keys("80")
    driver.find_element(By.ID, "respiratoryRate").send_keys("8")
    driver.find_element(By.ID, "oxygenSaturation").send_keys("90")
    # It's named "heightFeet" but is in reality meters...
    driver.find_element(By.ID, "heightFeet").send_keys("1")
    driver.find_element(By.ID, "heightInches").send_keys("77")
    driver.find_element(By.ID, "weight").send_keys("68")
    driver.find_element(By.ID, "glucose").send_keys("70")

    # Testing checkboxes on triage:
    driver.find_element(By.ID, "diabetic").click()
    driver.find_element(By.ID, "hypertension").click()
    driver.find_element(By.ID, "cholesterol").click()
    driver.find_element(By.ID, "smoker").click()
    driver.find_element(By.ID, "alcohol").click()

    driver.find_element(By.ID, "triageSubmitBtn").click()

    assert "Patient Id" in driver.find_element(By.ID, "history_patient_Patient").text

    # log out afterwards:
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"


def test_search(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    driver.set_window_size(1362, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("testnurse")
    driver.find_element(By.NAME, "password").send_keys("testnurse")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    driver.find_element(By.CSS_SELECTOR, "#langCode_triage").click()

    # Enter test patient info
    driver.find_element(By.ID, "firstName").send_keys("Search")
    driver.find_element(By.ID, "lastName").send_keys("Tester")
    #driver.find_element(By.ID, "").send_keys("")
    driver.find_element(By.ID, "city").send_keys("Port-au-Prince")
    driver.find_element(By.ID, "yearsInput").send_keys("22")
    driver.find_element(By.ID, "monthsInput").send_keys("3")
    driver.find_element(By.CSS_SELECTOR, "label.btn.btn-default.width-50").click()
    driver.find_element(By.ID, "temperature").send_keys("36")
    driver.find_element(By.ID, "bloodPressureSystolic").send_keys("90")
    driver.find_element(By.ID, "bloodPressureDiastolic").send_keys("90")
    driver.find_element(By.ID, "heartRate").send_keys("80")
    driver.find_element(By.ID, "respiratoryRate").send_keys("8")
    driver.find_element(By.ID, "oxygenSaturation").send_keys("90")
    driver.find_element(By.ID, "heightFeet").send_keys("1")
    driver.find_element(By.ID, "heightInches").send_keys("77")
    driver.find_element(By.ID, "weight").send_keys("68")
    driver.find_element(By.ID, "glucose").send_keys("70")
    driver.find_element(By.ID, "diabetic").click()
    driver.find_element(By.ID, "hypertension").click()
    driver.find_element(By.ID, "cholesterol").click()
    driver.find_element(By.ID, "smoker").click()
    driver.find_element(By.ID, "alcohol").click()

    driver.find_element(By.ID, "triageSubmitBtn").click()

    # Test searching. We use a wait here to avoid race conditions/element not loaded yet
    wait = WebDriverWait(driver, 10)
    search_form = wait.until(
        EC.visibility_of_element_located((By.ID, "nameOrIdSearchForm"))
    )
    search_form.send_keys("Search")
    driver.find_element(By.ID, "searchBtn").click()
    assert driver.find_element(By.ID, "nameOrIdSearchForm").get_attribute("placeholder") != "Invalid Patient"

    # Test viewing patient in Medical:
    driver.find_element(By.ID, "history_patient_Medical").click()
    assert "Patient Overview" in driver.find_element(By.ID, "partials_medical_Overview_Patient").text

    # log out afterwards:
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"

def test_medical(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")
    
    # Test logging in as testnurse:
    driver.set_window_size(1362, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("testnurse")
    driver.find_element(By.NAME, "password").send_keys("testnurse")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    
    assert "Welcome to fEMR" in driver.find_element(By.ID, "home_index_h2_Welcome").text

    #Submitting a patient before we look it up on medical
    driver.find_element(By.CSS_SELECTOR, "#langCode_triage").click()
    assert "Triage" in driver.find_element(By.ID, "triage_index_h2_Check").text

    driver.find_element(By.ID, "firstName").send_keys("Medical")
    driver.find_element(By.ID, "lastName").send_keys("Tester")
    driver.find_element(By.ID, "city").send_keys("Port-au-Prince")
    driver.find_element(By.ID, "yearsInput").send_keys("22")

    driver.find_element(By.CSS_SELECTOR, "#langCode_triage").click()

    # Enter test patient info
    driver.find_element(By.ID, "firstName").send_keys("Pharmacy")
    driver.find_element(By.ID, "lastName").send_keys("Testing")
    #driver.find_element(By.ID, "").send_keys("")
    driver.find_element(By.ID, "city").send_keys("Port-au-Prince")
    driver.find_element(By.ID, "yearsInput").send_keys("25")
    driver.find_element(By.ID, "monthsInput").send_keys("3")
    driver.find_element(By.CSS_SELECTOR, "label.btn.btn-default.width-50").click()
    driver.find_element(By.ID, "temperature").send_keys("36")
    driver.find_element(By.ID, "bloodPressureSystolic").send_keys("90")
    driver.find_element(By.ID, "bloodPressureDiastolic").send_keys("90")
    driver.find_element(By.ID, "heartRate").send_keys("80")
    driver.find_element(By.ID, "respiratoryRate").send_keys("8")
    driver.find_element(By.ID, "oxygenSaturation").send_keys("90")
    driver.find_element(By.ID, "heightFeet").send_keys("1")
    driver.find_element(By.ID, "heightInches").send_keys("77")
    driver.find_element(By.ID, "weight").send_keys("68")
    driver.find_element(By.ID, "glucose").send_keys("70")
    driver.find_element(By.ID, "diabetic").click()
    driver.find_element(By.ID, "hypertension").click()
    driver.find_element(By.ID, "cholesterol").click()
    driver.find_element(By.ID, "smoker").click()
    driver.find_element(By.ID, "alcohol").click()

    driver.find_element(By.ID, "triageSubmitBtn").click()
    
    assert "Patient Id" in driver.find_element(By.ID, "history_patient_Patient").text

    patientString = str(driver.find_element(By.ID, "history_patient_Patient").text)
    patientId = patientString.split()[len(patientString.split()) - 1]

    # Go to Medical Page and search for patient
    driver.find_element(By.ID, "langCode_medical").click()
    assert "Medical Search" in driver.find_element(By.ID, "search_box_title").text

    driver.find_element(By.CLASS_NAME, "fButtonSearch").send_keys(patientId)
    driver.find_element(By.CLASS_NAME, "idSearch").click()

    assert f"Patient Overview - Medical - Patient ID: {patientId}" in driver.find_element(By.ID, "partials_medical_Overview_Patient").text

    #Input some info and submit it
    driver.find_element(By.ID, "onsetTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "qualityTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "radiationTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "provokesTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "palliatesTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "narrativeTab").send_keys("urgent medical stuff")
    driver.find_element(By.ID, "physicalTab").send_keys("urgent, very important, medical stuff")

    driver.find_element(By.ID, "medicalSubmitBtn").click()

    # Search patient and go back to make sure it saved info
    driver.find_element(By.CLASS_NAME, "fButtonSearch").send_keys(patientId)
    driver.find_element(By.CLASS_NAME, "idSearch").click()
    driver.find_element(By.CLASS_NAME, "fYesButton").click()

    assert "urgent medical stuff" in driver.find_element(By.ID, "onsetTab").get_attribute("value")
    assert "urgent medical stuff" in driver.find_element(By.ID, "qualityTab").get_attribute("value")
    assert "urgent medical stuff" in driver.find_element(By.ID, "radiationTab").get_attribute("value")
    assert "urgent medical stuff" in driver.find_element(By.ID, "provokesTab").get_attribute("value")
    assert "urgent medical stuff" in driver.find_element(By.ID, "narrativeTab").get_attribute("value")
    assert "urgent, very important, medical stuff" in driver.find_element(By.ID, "physicalTab").get_attribute("value")
    assert "urgent medical stuff" in driver.find_element(By.ID, "palliatesTab").get_attribute("value")


# Tests purely for account creation/requesting approval.
def test_account_creation(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    driver.set_window_size(1362, 1157)
    driver.find_element(By.CSS_SELECTOR, "#login > form.form-signin.bottomButton > input[type=submit]").click()
    driver.find_element(By.ID, "email").send_keys("testguy123@gmail.com")
    driver.find_element(By.ID, "password").send_keys("Helloworld222")
    driver.find_element(By.ID, "passwordVerify").send_keys("Helloworld222")
    driver.find_element(By.ID, "firstName").send_keys("TestGuy")
    driver.find_element(By.ID, "lastName").send_keys("OrWoman")

    # Standard HTML <select> used to select language
    select_element = driver.find_element(By.ID, "language")
    select_object = Select(select_element)
    select_object.select_by_value("en")

    # Test account is a nurse
    driver.find_element(By.CSS_SELECTOR, "#roleWrap > label:nth-child(8) > input[type=checkbox]").click()

    # Submit info
    driver.find_element(By.ID, "addUserSubmitBtn").click()

    # Make sure we're back at home page
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"


# Tests account creation/requesting approval + admin approval
def test_account_creation_and_admin_approval(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    driver.set_window_size(1362, 1157)
    driver.find_element(By.CSS_SELECTOR, "#login > form.form-signin.bottomButton > input[type=submit]").click()
    driver.find_element(By.ID, "email").send_keys("testgal123@gmail.com")
    driver.find_element(By.ID, "password").send_keys("Helloworld222")
    driver.find_element(By.ID, "passwordVerify").send_keys("Helloworld222")
    driver.find_element(By.ID, "firstName").send_keys("TestWoman")
    driver.find_element(By.ID, "lastName").send_keys("OrGuy")

    # Standard HTML <select> used to select language
    select_element = driver.find_element(By.ID, "language")
    select_object = Select(select_element)
    select_object.select_by_value("en")

    # Test account is a nurse
    driver.find_element(By.CSS_SELECTOR, "#roleWrap > label:nth-child(8) > input[type=checkbox]").click()

    # Submit info
    driver.find_element(By.ID, "addUserSubmitBtn").click()

    # Make sure we're back at home page
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"

    # Log in as admin and approve request
    email_input = driver.find_element(By.NAME, "email")
    email_input.clear()
    email_input.send_keys("admin")
    driver.find_element(By.NAME, "password").send_keys("admin")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()

    # Wait to ensure everything loads
    welcome_header = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "home_index_h2_Welcome"))
    )
    assert "Welcome to fEMR" in welcome_header.text

    driver.find_element(By.ID, "langCode_admin").click()

    # Verify we travelled to admin panel page
    assert "Admin Panel" in driver.find_element(By.ID, "admin_title").text
    driver.find_element(By.ID, "admin_users").click()

    # Check for "TestWoman OrGuy" in our users list:
    table = driver.find_element(By.ID, "userTable")
    rows = table.find_elements(By.CSS_SELECTOR, "tbody tr")
    found = False

    for row in rows:
        cells = row.find_elements(By.TAG_NAME, "td")

        if not cells:
            continue

        name_text = cells[1].text.strip()

        if name_text == "TestWoman":
            found = True
            # Actually activate
            activate_btn = cells[-1].find_element(By.CSS_SELECTOR, "button.btn-success.toggleBtn")
            activate_btn.click()

            # Make sure activation was successful
            WebDriverWait(driver, 10).until(
                EC.text_to_be_present_in_element((By.CSS_SELECTOR, "button.btn-danger.toggleBtn"), "Deactivate")
            )
            deactivate_btn = cells[-1].find_element(By.CSS_SELECTOR, "button.btn-danger.toggleBtn")
            assert deactivate_btn.text.strip() == "Deactivate"
            break

    assert found

    # log out afterwards:
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"


# Tests pharmacy flow by creating a triage for a patient, searching for them in medical, assigning them a medication, and filling them in pharmacy
def test_pharmacy(driver):
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver.get(f"{femr_address}/")

    driver.set_window_size(1362, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("testnurse")
    driver.find_element(By.NAME, "password").send_keys("testnurse")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    driver.find_element(By.CSS_SELECTOR, "#langCode_triage").click()

    # Enter test patient info
    driver.find_element(By.ID, "firstName").send_keys("Pharmacy")
    driver.find_element(By.ID, "lastName").send_keys("Testing")
    #driver.find_element(By.ID, "").send_keys("")
    driver.find_element(By.ID, "city").send_keys("Port-au-Prince")
    driver.find_element(By.ID, "yearsInput").send_keys("25")
    driver.find_element(By.ID, "monthsInput").send_keys("3")
    driver.find_element(By.CSS_SELECTOR, "label.btn.btn-default.width-50").click()
    driver.find_element(By.ID, "temperature").send_keys("36")
    driver.find_element(By.ID, "bloodPressureSystolic").send_keys("90")
    driver.find_element(By.ID, "bloodPressureDiastolic").send_keys("90")
    driver.find_element(By.ID, "heartRate").send_keys("80")
    driver.find_element(By.ID, "respiratoryRate").send_keys("8")
    driver.find_element(By.ID, "oxygenSaturation").send_keys("90")
    driver.find_element(By.ID, "heightFeet").send_keys("1")
    driver.find_element(By.ID, "heightInches").send_keys("77")
    driver.find_element(By.ID, "weight").send_keys("68")
    driver.find_element(By.ID, "glucose").send_keys("70")

    driver.find_element(By.ID, "triageSubmitBtn").click()

    # Wait to ensure loading
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.ID, "langCode_medical")))

    # Save the patient's ID:
    patient_id = driver.find_element(By.ID, "history_patient_Patient").text[11:]

    driver.find_element(By.ID, "langCode_medical").click()

    driver.find_element(By.ID, "id").send_keys(patient_id)

    driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > button").click()

    assert "Patient Overview" in driver.find_element(By.ID, "partials_medical_Overview_Patient").text

    driver.find_element(By.CSS_SELECTOR, "#treatment").click()

    driver.find_element(By.NAME, "prescriptions[0].medicationName").send_keys("Test Medication")

    select_element = driver.find_element(By.XPATH, '//*[@name="prescriptions[0].administrationID"]')
    select_object = Select(select_element)
    select_object.select_by_value("1")

    driver.find_element(By.ID, "medicalSubmitBtn").click()

    # Wait to ensure loading
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.ID, "langCode_pharmacy")))

    assert "successfully" in driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > p").text

    driver.find_element(By.ID, "langCode_pharmacy").click()

    # Wait to ensure loading
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.ID, "id")))
    driver.find_element(By.ID, "id").send_keys(patient_id)
    driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > button").click()

    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, "#disclaimerWrap > input[type=checkbox]:nth-child(2)")))
    driver.find_element(By.CSS_SELECTOR, "#disclaimerWrap > input[type=checkbox]:nth-child(2)").click()
    driver.find_element(By.ID, "pharmacySubmitBtn").click()

    assert "successfully" in driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > p").text

    # Wait to ensure loading
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.ID, "langCode_pharmacy")))

    assert "successfully" in driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > p").text

    driver.find_element(By.ID, "langCode_pharmacy").click()

    # Wait to ensure loading
    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.ID, "id")))
    driver.find_element(By.ID, "id").send_keys(patient_id)
    driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > button").click()

    wait = WebDriverWait(driver, 10)
    wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, "#disclaimerWrap > input[type=checkbox]:nth-child(2)")))
    driver.find_element(By.CSS_SELECTOR, "#disclaimerWrap > input[type=checkbox]:nth-child(2)").click()
    driver.find_element(By.ID, "pharmacySubmitBtn").click()

    assert "successfully" in driver.find_element(By.CSS_SELECTOR, "body > div.container > div > form > div > p").text

















