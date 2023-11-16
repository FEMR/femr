import sys
import argostranslate.package
import argostranslate.translate

def main():
    from_code = "en"
    to_code = "es"

    if (len(argostranslate.package.get_installed_packages()) == 0):
        argostranslate.package.update_package_index()
        available_packages = argostranslate.package.get_available_packages()
        package_to_install = next(
            filter(
                lambda x: x.from_code == from_code and x.to_code == to_code, available_packages
            )
        )
        argostranslate.package.install_from_path(package_to_install.download())

    translatedText = argostranslate.translate.translate(sys.argv[1], from_code, to_code)
    print(list(translatedText.encode('utf-8')))

if __name__ == "__main__":
   main()
