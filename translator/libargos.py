import argostranslate.package
import argostranslate.settings
import os
from pathlib import Path

PATH = os.getcwd()
ARGOS_PACKAGES_DIR=Path(f"{PATH}/translator/argos_models")

#Download all available packages
def download_packages():
    if not ARGOS_PACKAGES_DIR.exists(): os.mkdir(ARGOS_PACKAGES_DIR)
    argostranslate.settings.downloads_dir = ARGOS_PACKAGES_DIR
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    for package in available_packages:
          package_name = package.code + ".argosmodel"
          if package_name not in os.listdir(ARGOS_PACKAGES_DIR):
             package.download()


#returns list of installed_packages with names in format matching the filenames
def get_installed_package_names():
    models = []
    installed_packages = argostranslate.package.get_installed_packages()
    for package in installed_packages:
        model_name = f"translate-{package.from_code}_{package.to_code}.argosmodel"
        models.append(model_name)
    return models

#Installs all packages from local directory
def install_packages():
    installed_packages = get_installed_package_names()
    for filename in os.listdir(ARGOS_PACKAGES_DIR):
        if filename not in installed_packages:
            file = os.path.join(ARGOS_PACKAGES_DIR, filename)
            argostranslate.package.install_from_path(file)
    return argostranslate.package.get_installed_packages()

#Uninstall all packages
def uninstall_all_packages():
    installed = argostranslate.package.get_installed_packages()
    for package in installed:
        argostranslate.package.uninstall(package)

#update all installed packages
def update_packages():
    installed = install_packages()
    for package in installed:
        package.update()

#displays all installed packages
def display_installed_packages():
    installed = install_packages()
    list = {}
    for package in installed:
        list.update({package.to_code:package.to_name})
        list.update({package.from_code:package.from_name})
    for code in list:
        print(f"{code}, {list[code]}")

if __name__ == "__main__":
    download_packages()
    display_installed_packages()

