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
from libargos import install_packages, packages_downloaded
from libmarian import package_downloaded
import socket
import time

PORTS = [8000, 5000, 8001, 8002, 8003, 8004, 8005, 8006, 8007, 8008]

try:
    TIMEOUT = int(sys.argv[1])
except (ValueError, IndexError):
    TIMEOUT = 0

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
        #parse request
        length = int(self.headers['Content-Length'])
        input_list = json.loads(self.rfile.read(length).decode('utf-8'))
        from_code = self.query_data['from']
        to_code = self.query_data['to']

        input_len = len(input_list)
        output_list = input_list

        bool_array = [False] * input_len
        #for each tab in field_list, build delimiter string to translate

        translation_count = 0
        translate_string = ""
        for i in range(input_len):
            if(input_list[i]["text"] != ""):
                translation_count = translation_count + 1
                translate_string = translate_string + " @ " + input_list[i]["text"]
                bool_array[i] = True

        #remove first "@"
        translate_string = translate_string[3:]
        translated_string = self.translate(translate_string, from_code, to_code)
        translated_list = list(translated_string.split(" @ "))

        if(len(translated_list) != translation_count):
            output_list[0]["text"] = "Delimiter Error"
            return json.dumps(output_list)

        for i in range(input_len):
            if(bool_array[i]):
                output_list[i]["text"] = translated_list.pop(0)
                bool_array[i] = False


        return json.dumps(output_list)

    def translate(self, text, from_code, to_code):
            # Use Argos if Language Package Exists
            if packages_downloaded((from_code, to_code)):
                translatedText = argostranslate.translate.translate(text, from_code, to_code)
                return translatedText

            # Use Marian if Language Package Exists in Marian but not Argos
            elif package_downloaded(from_code, to_code):
                marian = MarianModel(from_code, to_code)
                translatedText = marian.translate([text])
                return translatedText[0]

            # Use Argos "English in the Middle" if not in Argos and Marian by Default
            elif packages_downloaded([(from_code ,"en"), (to_code, "en"), ("en", from_code), ("en", to_code)]):
                translatedText = argostranslate.translate.translate(text, from_code, to_code)
                return translatedText

            # If a package doesn't exist
            else:
                return "Translation Unavailable"

    def get_response(self):
        return json.dumps(
            {
                "translate_data" : self.translate_data if self.query_data else "",
            },
            ensure_ascii=False
        )

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(self.get_response().encode("utf-8"))

    def do_POST(self):
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(self.get_response().encode("utf-8"))

def port_open(port):
    #connect_ex returns 0 if it connects to a socket meaning port is closed
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        return s.connect_ex(('localhost', port)) != 0

def start_server(port):
    try:
        server = HTTPServer(("127.0.0.1", port), WebRequestHandler)
        server.timeout = TIMEOUT
        server.handle_timeout = lambda: (_ for _ in ()).throw(TimeoutError())
        print(f"Serving at port: {port}", file=sys.stderr)
        print(f"Server started at {time.strftime('%I:%M')} with timeout: {TIMEOUT} seconds", file=sys.stderr)
        while(True): server.handle_request()
    except TimeoutError:
        print("Translation server timed out")
        sys.exit()

if __name__ == "__main__":
    install_packages()
    for port in PORTS:
        if(port_open(port)):
            start_server(port)
            