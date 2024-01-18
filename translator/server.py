from functools import cached_property
from http.server import BaseHTTPRequestHandler
from urllib.parse import parse_qsl, urlparse
from http.server import HTTPServer
import json
import argostranslate.package
import argostranslate.translate
import sys

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
        if(text == "temporaryexitcode"):
            sys.exit()
        #language hard coded right now, need to pass language codes in request
        return argostranslate.translate.translate(text, "en", "es")

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
    print("Test")