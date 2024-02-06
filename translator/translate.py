import sys
import argostranslate.package
import argostranslate.translate
import zipfile
import os

def main():
    from_code = sys.argv[2]
    to_code = sys.argv[3]

#     if (len(argostranslate.package.get_installed_packages()) == 0):
#         argostranslate.package.update_package_index()
#         available_packages = argostranslate.package.get_available_packages()
#         package_to_install = next(
#             filter(
#                 lambda x: x.from_code == from_code and x.to_code == to_code, available_packages
#             )
#         )
#         argostranslate.package.install_from_path(package_to_install.download())


    #Install all packages
    def install_packages():
        if(len(argostranslate.package.get_installed_packages()) == 0):
            package_dir = "C:/Users/micha/Projects/Capstone/femr/translator/all-argos-translate-models-2020-12-20"
            for filename in os.listdir(package_dir):
                file = os.path.join(package_dir, filename)
                argostranslate.package.install_from_path(file)
            print(argostranslate.package.get_installed_packages())

    #Uninstall all packages
    def uninstall_all_packages():
        installed = argostranslate.package.get_installed_packages()
        for package in installed:
            argostranslate.package.uninstall(package)
        print(argostranslate.package.get_installed_packages())
    install_packages()


if __name__ == "__main__":
   main()

