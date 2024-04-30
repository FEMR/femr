import os
import sys
import json
import argostranslate.package
import argostranslate.translate
from functools import cached_property
from http.server import BaseHTTPRequestHandler
from urllib.parse import parse_qsl, urlparse
from http.server import HTTPServer
from pathlib import Path
from transformers import MarianMTModel, MarianTokenizer
from typing import Sequence
from libargos import install_packages
import socket

PORTS = [8001, 8002, 8003, 8004, 8005, 8006, 8007, 8008, 5000]
TIMEOUT = 60
PATH = os.getcwd()



class MarianModel:
    def __init__(self, source_lang: str, dest_lang: str) -> None:
        path = f"{PATH}/translator/marian_models/opus-mt-{source_lang}-{dest_lang}"
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
        text = self.query_data['text']
        from_code = self.query_data['from']
        to_code = self.query_data['to']

        # Use Argos if Language Package Exists
        if Path(f"{PATH}/translator/argos_models/translate-{from_code}_{to_code}.argosmodel").exists():
            translatedText = argostranslate.translate.translate(text, from_code, to_code)
            return translatedText
        # Use Marian if Language Package Exists in Marian but not Argos
        elif Path(f"{PATH}/translator/marian_models/opus-mt-{from_code}-{to_code}").exists():
            marian = MarianModel(from_code, to_code)
            translatedText = marian.translate([text])
            return translatedText[0]
        # Use Argos "English in the Middle" if not in Argos and Marian by Default
        elif (Path(f"{PATH}/translator/argos_models/translate-{from_code}_en.argosmodel").exists() and \
                Path(f"{PATH}/translator/argos_models/translate-{to_code}_en.argosmodel").exists()) or \
                (Path(f"{PATH}/translator/argos_models/translate-en_{from_code}.argosmodel").exists() and \
                 Path(f"{PATH}/translator/argos_models/translate-en_{to_code}.argosmodel").exists()):
            translatedText = argostranslate.translate.translate(text, from_code, to_code)
            return translatedText
        # If a package doesn't exist
        else:

            marian_language_path = Path(f"{PATH}/translator/marian_models/opus-mt-{from_code}-{to_code}")
            if marian_language_path.exists():
                # print("MARIAN")
                marian = MarianModel(from_code, to_code)
                translatedText = marian.translate([text])
                return translatedText[0]
            else:
                return from_code + to_code + "Translation Unavailable"



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

def port_open(port):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        return s.connect_ex(('localhost', port)) != 0

def start_server(port):
    try:
        server = HTTPServer(("127.0.0.1", port), WebRequestHandler)
        server.timeout = TIMEOUT
        server.handle_timeout = lambda: (_ for _ in ()).throw(TimeoutError())
        print(f"Serving at port: {port}", file=sys.stderr)
        while(True): server.handle_request()
    except TimeoutError:
        print("Translation server timed out")
        sys.exit()

if __name__ == "__main__":
    install_packages()
    for port in PORTS:
        try:
            start_server(port)
        except OSError:
            print(f"Port {port} unavailable")


