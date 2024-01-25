from functools import cached_property
from http.server import BaseHTTPRequestHandler
from urllib.parse import parse_qsl, urlparse
from http.server import HTTPServer
import json
import argostranslate.package
import argostranslate.translate
import sys
from transformers import MarianMTModel, MarianTokenizer
from typing import Sequence

class MarianModel:
    def __init__(self, source_lang: str, dest_lang: str) -> None:
        self.model_name = f'Helsinki-NLP/opus-mt-{source_lang}-{dest_lang}'
        self.model = MarianMTModel.from_pretrained(self.model_name)
        self.tokenizer = MarianTokenizer.from_pretrained(self.model_name)

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

        #ARGOS
        #return argostranslate.translate.translate(text, from_code, to_code)
        
        #MARIAN
        marian = MarianModel(from_code, to_code)
        return marian.translate([text])[0]

    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.end_headers()
        self.wfile.write(self.get_response().encode("utf-8"))

    def do_POST(self):
        self.do_GET()

    def get_response(self):
        return json.dumps(
            {
                "translate_data" : self.translate_data if self.query_data else "",
            },
            ensure_ascii=False
        )

if __name__ == "__main__":
    server = HTTPServer(("127.0.0.1", 8000), WebRequestHandler)
    server.serve_forever()