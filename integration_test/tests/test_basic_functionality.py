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


def test_femr_is_alive():
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    response = requests.get(femr_address)
    assert response.status_code == 200

def test_can_login_and_logout_to_admin():
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    driver_address = os.getenv("SELENIUM_ADDRESS")

    assert driver_address is not None, "SELENIUM_ADDRESS environment variable not set"

    driver = webdriver.Remote(command_executor=driver_address, options=webdriver.ChromeOptions())

    driver.get(f"{femr_address}/")

    # Test Login
    driver.set_window_size(1361, 1157)
    driver.find_element(By.NAME, "email").click()
    driver.find_element(By.NAME, "email").send_keys("admin")
    driver.find_element(By.NAME, "password").send_keys("admin")
    driver.find_element(By.CSS_SELECTOR, "input:nth-child(4)").click()
    assert "Welcome to fEMR" in driver.find_element(By.ID, "home_index_h2_Welcome").text

    # Test Logout
    driver.find_element(By.CSS_SELECTOR, ".glyphicon-log-out").click()
    assert driver.find_element(By.CSS_SELECTOR, "h1").text == "Please sign in"
  