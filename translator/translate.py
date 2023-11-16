import sys
from argostranslate import package, translate

def main():
    if ( len(package.get_installed_packages()) == 0 ):
        package.install_from_path('en_es.argosmodel')
    installed_languages = translate.get_installed_languages()
    translation = installed_languages[0].get_translation(installed_languages[1])
    text = translation.translate(sys.argv[1])
    print(list(text.encode('utf-8')))

if __name__ == "__main__":
   main()
