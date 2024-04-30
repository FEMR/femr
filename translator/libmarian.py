from transformers import MarianMTModel, MarianTokenizer
from typing import Sequence
import os
PATH = os.getcwd()

def download_package(src, dst):
    print(f"Downloading {src}-{dst}...")
    model_name = f"Helsinki-NLP/opus-mt-{src}-{dst}"
    try:
        tokenizer = MarianTokenizer.from_pretrained(model_name)
        tokenizer.save_pretrained(f"{PATH}/translator/marian_models/opus-mt-{src}-{dst}")
        model = MarianMTModel.from_pretrained(model_name)
        model.save_pretrained(f"{PATH}/translator/marian_models/opus-mt-{src}-{dst}")
    except OSError:
        print("Package not found")

def package_downloaded(src, dst):
    package_name = f"opus-mt-{src}-{dst}"
    os.makedirs(f"{PATH}/translator/marian_models", exist_ok=True)
    if package_name in os.listdir(f"{PATH}/translator/marian_models"):
        return True
    return False