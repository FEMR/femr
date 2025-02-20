# 🌍 fEMR Internationalization Guide

## 📖 Table of Contents

1. [Language List](#language-list)
2. [Using the Script](#using-the-script)
3. [Adding a New Language](#adding-a-new-language)
4. [Adding Text to the Main fEMR Repo](#adding-text-to-the-main-femr-repo)
5. [Splitting by Language](#splitting-by-language)

---

## Language List

| Language    | Code | Language           | Code  |
| ----------- | ---- | ------------------ | ----- |
| English     | `en` | Italian            | `it`  |
| Spanish     | `es` | Japanese           | `ja`  |
| French      | `fr` | Korean             | `ko`  |
| Georgian    | `ka` | Polish             | `pl`  |
| Arabic      | `ar` | Portuguese         | `pt`  |
| Azerbaijani | `az` | Russian            | `ru`  |
| Bulgarian   | `bg` | Slovak             | `sk`  |
| Bengali     | `bn` | Swedish            | `sv`  |
| Bosnian     | `bs` | Turkish            | `tr`  |
| Czech       | `cs` | Vietnamese         | `vi`  |
| Danish      | `da` | Chinese            | `zh`  |
| German      | `de` | Tagalog (Filipino) | `fil` |
| Greek       | `el` | Hebrew             | `he`  |
| Estonian    | `et` | Hindi              | `hi`  |
| Persian     | `fa` | Croatian           | `hr`  |
| Finnish     | `fi` | Hungarian          | `hu`  |
| Indonesian  | `id` | -                  | -     |

---

## Using the Scripts

```bash
# 1️⃣ Copy the language file(s) into the directory

# 2️⃣ Add your label and text data to `translator.py` by editing the `input_values` array inside `translator.py`

# 3️⃣ Create and activate a virtual environment (recommended)

python -m venv venv
source venv/bin/activate  # On macOS/Linux
venv\Scripts\activate     # On Windows

# 4️⃣ Install dependencies

pip install -r translator-requirements.txt

# 5️⃣ Run the translation script

python translator.py

# Translations will populate the language files folder

# 6️⃣ Replace the main repo’s language file(s)
```

---

## Adding a New Language

1. Add the new language’s code to the **Language List** in **translator.py**.
2. Run the script to generate the translations.
3. Verify the translations before adding them to the main repo.

---

## Adding Text to the Main fEMR Repo

Within the file you added text to, you will need to call an update function within the script block to load the translations.
Here is an example of that:

```javascript
function updateTextContent(elementId, value) {
  const element = document.getElementById(elementId);
  if (element) {
    element.textContent = value;
  }
}
```

You can also find a good example of how it's done in fEMR by looking at **index.scala.html** for triage

---

### Splitting by Language

By default, the script runs in single mode, which saves all translations in a single languages.json file. However, you can enable multiple mode to save translations into separate files, one per language.

To use multiple mode, the script requires input files that are already split by language, or else it will create all new files for each language. In order to add on to existing translations, you can either:

1. Provide your own pre-separated JSON files if the languages are already separated..
2. Use json-splitter.py to split an existing languages.json file into multiple language-specific files.

All output files will be saved in the output folder by default.

Here is how to run the script:

```bash
# 1️⃣ Run the script to split languages.json

python json-splitter.py

# The split language files will be saved in the "output" folder
```
