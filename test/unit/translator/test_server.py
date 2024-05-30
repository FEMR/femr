
import sys

# Checks for modules in this path first
sys.path.insert(1, './../../../translator')
sys.path.insert(1, 'translator')

from server import WebRequestHandler


""" 
    IMPORTANT NOTE: These tests must be ran from the main femr directory!
    
    - The argos_models and marian_models files are located within the femr/translator directory. The translator code 
      requires the cwd to be the femr directory to work. As a result, these tests are designed in the same manner to function.
      
    - If the argos_models and marian_models directories do not exist, you can create them by running the libargos.py and 
      libmarian.py python scripts.
    
    - If you need to use libmarian.py, please install the en-ti and ti-en packages for the tests to work.
"""

def test_small_english_to_french_argos_translation():
    handler = WebRequestHandler
    translated_text = handler.translate(handler, "Hello", "en", "fr")
    assert translated_text == "Bonjour."
    
def test_small_french_to_english_argos_translation():
    handler = WebRequestHandler
    translated_text = handler.translate(handler, "Bonjour", "fr", "en")
    assert translated_text == "Hello."
    
def test_sentence_english_to_french_argos_translation():
    handler = WebRequestHandler
    complaint = "I have a broken leg and a bruised elbow."
    translated_text = handler.translate(handler, complaint, "en", "fr")
    assert translated_text == "J'ai une jambe cassée et un coude meurtri."
    
def test_sentence_french_to_english_argos_translation():
    handler = WebRequestHandler
    complaint = "J'ai une jambe cassée et un coude meurtri."
    translated_text = handler.translate(handler, complaint, "fr", "en")
    assert translated_text == "I have a broken leg and a bruised elbow."
    
# def test_small_english_to_tigrinya_marian_translation():
#     handler = WebRequestHandler
#     translated_text = handler.translate(handler, "Hello there", "en", "ti")
#     assert translated_text == "ሰላም."

# def test_tigrinya_to_english_marian_translation():
#     handler = WebRequestHandler
#     translated_text = handler.translate(handler, "ሰላም.", "ti", "en")
#     assert translated_text == "Hello."
    
    
def test_english_in_the_middle_for_spanish_to_french_argos_translation():
    handler = WebRequestHandler
    complaint = "J'ai une jambe cassée et un coude meurtri."
    translated_text = handler.translate(handler, complaint, "fr", "es")
    assert translated_text == "Tengo una pierna rota y un codo magullado."    

def test_failed_translation():
    handler = WebRequestHandler
    translated_text = handler.translate(handler, "Bonjour", "hi", "there")
    assert translated_text == "Translation Unavailable"
