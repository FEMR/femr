import os
import pytest

from selenium.webdriver.chrome import webdriver
from selenium import webdriver
from selenium.webdriver.common.by import By


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
def test_adding_new_patient(driver):
    femr_address = os.getenv("FEMR_ADDRESS")
    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"
    driver.get(f"{femr_address}/")
    driver.set_window_size(1362, 1157)


def test_search_up_on_medical(driver):
    femr_address = os.getenv("FEMR_ADDRESS")
    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"
    driver.get(f"{femr_address}/")
    driver.set_window_size(1362, 1157)

