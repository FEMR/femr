import sys
import libargos as argos
import libmarian as marian

def optimize_path(src, dest):
    #download all available argos packages
    argos.download_packages()

    #check if installed as argos
    argos_packages = argos.install_packages()
    for package in argos_packages:
        if(package.from_code == src and package.to_code == dest):
            return

    #check if present as marian
    if(marian.package_downloaded(src, dest)):
        return

    #download marian package
    marian.download_package(src, dest)

def main():
    lang_one = sys.argv[1]
    lang_two = sys.argv[2]
    optimize_path(lang_one, lang_two)
    optimize_path(lang_two, lang_one)
    print(f"{lang_one} and {lang_two} Optimized")

if __name__ == "__main__":
    main()



