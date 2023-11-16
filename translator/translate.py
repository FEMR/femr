import sys
import argostranslate.package
import argostranslate.translate

def main():
    if ( len(argostranslate.package.get_installed_packages()) == 0 ):
        argostranslate.package.install_from_path('en_es.argosmodel')
    text = argostranslate.translate.translate(sys.argv[1], 'en', 'es')
    print(list(text.encode('utf-8')))

if __name__ == "__main__":
   main()
