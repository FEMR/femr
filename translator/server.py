import json
import argostranslate.package
import argostranslate.translate
import sys
import os
from functools import cached_property
from http.server import BaseHTTPRequestHandler
from urllib.parse import parse_qsl, urlparse
from http.server import HTTPServer
from pathlib import Path
from transformers import MarianMTModel, MarianTokenizer
from typing import Sequence


PORT = 8000
TIMEOUT = 60
PATH = os.getcwd()

#Install all packages
def install_packages():
    if(len(argostranslate.package.get_installed_packages()) == 0):
        package_dir = f"{PATH}/all-argos-translate-models-2020-12-20"
        for filename in os.listdir(package_dir):
            file = os.path.join(package_dir, filename)
            argostranslate.package.install_from_path(file)

class MarianModel:
    def __init__(self, source_lang: str, dest_lang: str) -> None:
        path = f"{PATH}/marian_models/opus-mt-{source_lang}-{dest_lang}"
        self.model = MarianMTModel.from_pretrained(path, local_files_only = True)
        self.tokenizer = MarianTokenizer.from_pretrained(path, local_files_only = True)

    def translate(self, texts: Sequence[str]) -> Sequence[str]:
        tokens = self.tokenizer(list(texts), return_tensors="pt", padding=True)
        translate_tokens = self.model.generate(**tokens)
        return [self.tokenizer.decode(t, skip_special_tokens=True) for t in translate_tokens]

class WebRequestHandler(BaseHTTPRequestHandler):
    @cached_property
    def url(self):
        return urlparse(self.path)

    @cached_property
    def query_data(self):
        return dict(parse_qsl(self.url.query))

    @cached_property
    def translate_data(self):
        print(os.getcwd())
        text = self.query_data['text']
        from_code = self.query_data['from']
        to_code = self.query_data['to']

        #ARGOS
        # return argostranslate.translate.translate(text, from_code, to_code)

        #MARIAN
        # marian = MarianModel(from_code, to_code)
        # return marian.translate([text])[0]

        #BOTH
        argos_language_path = Path(f"{PATH}/all-argos-translate-models-2020-12-20/{from_code}_{to_code}.argosmodel")
        if argos_language_path.exists():
            print("ARGOS")
            translatedText = argostranslate.translate.translate(text, from_code, to_code)
            return translatedText
        else:
            marian_language_path = Path(f"{PATH}/marian_models/opus-mt-{from_code}-{to_code}")
            if marian_language_path.exists():
                print("MARIAN")
                marian = MarianModel(from_code, to_code)
                translatedText = marian.translate([text])
                return translatedText[0]
            else:
                return "Translation Unavailable"
        #     except Exception:
        #         print("ALL TRANSLATIONS FAILED")
        #

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(self.get_response().encode("utf-8"))


    def get_response(self):
        return json.dumps(
            {
                "translate_data" : self.translate_data if self.query_data else "",
            },
            ensure_ascii=False
        )

if __name__ == "__main__":
    try:
        install_packages()
        server = HTTPServer(("127.0.0.1", PORT), WebRequestHandler)
        server.timeout = TIMEOUT
        server.handle_timeout = lambda: (_ for _ in ()).throw(TimeoutError())
        print("Serving at port", PORT)
        while(True): server.handle_request()
    except TimeoutError:
        print("Translation server timed out")
        sys.exit()