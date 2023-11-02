import sys
from argostranslate import package, translate

def main():
    #package.install_from_path('en_es.argosmodel')
    installed_languages = translate.get_installed_languages()
    translation = installed_languages[0].get_translation(installed_languages[5])
    text = translation.translate(sys.argv[1])
    print(text)

if __name__ == "__main__":
   main()
