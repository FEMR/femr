import ssl
import os
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
("home_index_div_ID", "ID, Name, or Phone #")
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

# Create output directory if it doesn't exist
os.makedirs("output", exist_ok=True)

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