import os
import requests

def test_hello():
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    response = requests.get(femr_address)
    assert response.status_code == 200

def test_hello2():
    femr_address = os.getenv("FEMR_ADDRESS")

    assert femr_address is not None, "FEMR_ADDRESS environment variable not set"

    response = requests.get(f"{femr_address}/thispath-doesnotexist")
    assert response.status_code == 200