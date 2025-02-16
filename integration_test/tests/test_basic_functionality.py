import pytest
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import os
import requests

@pytest.fixture
def driver():
    driver_address = os.getenv("SELENIUM_ADDRESS")
    assert driver_address is not None, "SELENIUM_ADDRESS environment variable not set"
    options = webdriver.ChromeOptions()
    drvr = webdriver.Remote(command_executor=driver_address, options=options)
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

    # Test searching
    driver.find_element(By.ID, "nameOrIdSearchForm").send_keys("Search")
    driver.find_element(By.ID, "searchBtn").click()
    assert driver.find_element(By.ID, "nameOrIdSearchForm").get_attribute("placeholder") != "Invalid Patient"

    # Test viewing patient in Medical:
    driver.find_element(By.ID, "history_patient_Medical").click()
    assert "Patient Overview" in driver.find_element(By.ID, "partials_medical_Overview_Patient").text

    # log out afterwards:
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"