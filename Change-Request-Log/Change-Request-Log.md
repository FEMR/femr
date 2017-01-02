# Change Request Log

## Team: Group 1

txz150730 - Tianxiang Zhang  
zxq150130 - Zengtai Qi (Shane)

## Change Request: FEMR-137

Description:  

Researchers want fEMR to flag whether or not the patient's birthdate is real or if fEMR tried to guess what it was. So that data is accurately stored with integrity.

Notes:  
- Patients often do not know what day they were born and sometimes don't even know their age.
- fEMR allows you to identify how old a patient is in 3 different ways: enter the actual birthdate, enter an age integer, or select a category (child,adult,elder,etc).
- The database has 2 options of storing this data - a birthdate OR the category. If the user enters an age integer, the system will create a fake birthdate, but it won't flag the birthdate as fake for future reference.

## Concept Location

\# | Description | Rationale
---|---|---
1 | We understand the problem in the persistent storing layer, but not quite sure how and where exactly to reproduce the problem and test after changing. So we sent email to Kevin Zurek for some clarifications. According to Kevin's response, the problem is that when researchers export data to csv file, there is no flag to tell whether or not the patient's birthdate is real. | Clarify the problem.
2 | We think more than one location might be involved in this change. We list them as:<br> 1. CSV exporting logic. <br> 2. Database structure. <br> 3. Models. <br> 4. User Interface. | We were not sure if database supports this feature.
3 | We decided to do the concept location phase for the two locations separately. | The two locations seems far from each other.

### CSV Exporting

\# | Description | Rationale
---|---|---
1 | We ran the system. |
2 | We interacted with the system: after logging in we entered the 'research' view, and then we filled in the form and click `Export Data` button. We opened the CSV file we got from the website, there is only 'age' filed, no 'birthday' field nor flag of if birthday is fake.  | In order to locate the page of the problem and reproduce the problem.
3 | We navigated to the html template file of 'research' view (`./app/femr/ui/views/admin/research/index.scala.html`) via IDE project tree view window. | According to common sense of web framework, there should a template file of the edit page. And it turned out there does is a template html file.
4 | We searched keywords 'Export Data' in the template html file, and find the button dom. But no lucky, can't see what action the button triggers. |
5 | We made an assumption that the button fires an Javascript function. So we went to the browser, used the browser developing tool to find out the dom id of the 'Export Data' button, then search the id ('export-button') as keywords in all Javascript files of this webpage. We finally found out that the button does fire a Javascript function which just simply submits the form with id 'graph-options'. | Here we used some acknowledge of Javascript and jQuery which we didn't record much details.
6 | We went back to the html template file of 'research' view (`./app/femr/ui/views/admin/research/index.scala.html`) and searched the form with id 'graph-options', and we finally found out that CSV files comes from `ResearchController.exportPost()`. |
7 | In `ResearchController.exportPost()`, we found out that it only simply returns response along with the CSV file. CSV file actually comes from interface method: `IResearchService.retrieveCsvExportFile()`. We used IDE 'navigate to implementations' tool navigated to class `ResearchService` which implements method `retrieveCsvExportFile()`. |
8 | We looked into `ResearchService` a little bit, it has a private method `createResearchExportItem()` which is in charge of adding fields for every CSV row. We put class `ResearchService` into our initial impact set. | Successfully located CSV exporting concept location.

### Database Structure

\# | Description | Rationale
---|---|---
1 | We opened the database with MySQL command line tool and inspected all the tables (ordered by the possibility of containing age/birthday information). | In order to find out how the system stores age information.
2 | After inspection we found out table `patients` has a column `age` storing birthday date of patients, table `patient_age_classifications` stores all the categories of patients' age and table `patient_encounters` has a column `patient_age_classification_id` connect patient's encounters with age categories. |
3 | Since birthday date is absolute while patient_age_classifications is relative (relative to when the record was recorded into the system). We asserted that birthday date and patient_age_classifications should be two different concept. We decided to find a way giving the `age` column of table `patients` a flag. | Narrow down the concept location area.
4 | We inspected table `patients`, existed columns didn't support having a flag implicitly without altering data. We decided to add a new column storing the birthday\_is\_fake flag. |
5 | We searched keywords 'add column play framework' on Google, and learned that we should add `.sql` file in `./conf/evolutions.default/` directory. | Successfully located database structure changing concept location.

### Models

\# | Description | Rationale
---|---|---
1 | Since we add a column into patients table, we went the `./app/femr/data/models/` and found out the class `Patient`. We put it into initial impact set. | According to common sense of MCV pattern, models are possibly defined here. And in the `Patient` class we found, there are properties binded to column.

### User Interface

\# | Description | Rationale
---|---|---
1 | Since we added a flag for patients' birthday, user interface should also be change to support flagging birthday as fake or not fake. | Considering the consistent of the system.
2 | We figured the first file needs modify is html template file (`./app/femr/ui/views/admin/triage/index.scala.html`). But it's not a class. | We should stop until we reach the file class to change.
3 | We searched keywords 'Age' within the html template file and with IDE find tool. We found out that this form will be submitted into action `TriageController.indexPost()`, we put class `TriageController` into initial impact set. |

__Time Spent: 150 mins__  
__Recorder: Shane Qi__


## Impact Analysis

\# | Description | Rationale
---|---|---
1 | We have the initial impact set: (`ResearchService`, `Patient`, `TriageController`) |
2 | We searched usages of `ResearchService` and `IResearchService` in the project via IDE 'search usages tool', included `ResearchController` into impact set. | In order to find classes that depends on `ResearchService`.
3 | We did the dependencies analysis with IDE 'Analyze Dependencies' tool on class `ResearchService`, and put `ResearchExportItem` and `ResearchResultSetItem` into impact set | In order to find `ResearchService`'s dependencies.
4 | We searched usages of `Patient` and its interface `IPatient` put `ResearchEncounter`, `PatientEncounter`, `PatientProvider`, `PatientService`, `PhotoService`, `SearchService`, and `DataModelMapper` into impact set. |
5 | We did the dependencies analysis on `Patient`, and put `Photo` into impact set. |
6 | We searched class `TriageController`'s usages, but there is no solid class except `Routes.scala`. We didn't put any other class into impact set here. |
7 | We did dependencies analysis on `TriageController`, and put `PatientItem`, `ItemModelMapper`, `IndexViewModelPost` and `EncounterService` into impact set. |
8 | We finished estimated impact set as: <br>![](estimated-impact-set.png) |
9 | We marked `ResearchService` as 'CHANGED' and marked `ResearchExportItem`, `ResearchResultSetItem` and `ResearchController` as 'NEXT'. | We already decided to make changes on `ResearchService` in the concept location phase.
10 | We inspected `ResearchExportItem`, this class is kind of a model of every CSV row, since we planed to add a column into CSV file, we marked this class as 'Changed'. |
11 | We inspected `ResearchResultSetItem`, this class doesn't hold information about CSV file, we marked it as 'Unchanged'. |
12 | We inspected `ResearchController`, this class only dispatch different requests to different delegate class and return the responses with content, doesn't hold logic about response content, we marked it as 'Unchanged'. |
13 | We marked `Patient` as 'Changed'. And marked `ResearchEncounter`, `PatientEncounter`, `PatientProvider`, `PatientService`, `PhotoService`, `SearchService`, `DataModelMapper` and `Photo` as 'NEXT'. | This is a member of initial impact set, should be marked 'Changed' and mark neighbors as 'NEXT'.
14 | We inspected `ResearchEncounter`, it doesn't hold patients' birthday information, we marked it as 'Unchanged'  |
15 | We inspected `PatientEncounter`, it doesn't hold patients' birthday information, we marked it as 'Unchanged'  |
16 | We inspected `PatientProvider`, it's an generics interface in defined in Play framework, can't be changed, we marked it as 'Unchanged'.  |
17 | We inspected `PatientService`, this is kind of an middleware between database and MVC framework, since model `Patient` is gonna be changed, we marked it as 'Changed'.  |
18 | We inspected `PhotoService`, it only deal with fetching and saving a patient's photo, since we won't make change on patient photo, we marked this class as 'Unchanged'.  |
19 | We inspected `SearchService`, it contains a method which fetch a patient's whole information by id, since we were gonna make changes on model `Patient`, we marked this class as 'Changed'. |
20 | We inspected `DataModelMapper`, it's the map between model's properties and database column, it definitely need changed, we marked it as 'Changed'. |
21 | We inspected `Photo`, this is a model class, a photo belongs to a patient, it won't be impacted by change on `Patient`, we marked it as 'Unchanged'.  |
22 | We marked `TriageController` as 'Changed'. And marked `PatientItem`, `ItemModelMapper`, `IndexViewModelPost` and `EncounterService` with 'NEXT'. | This is a member of initial impact set, should be marked 'Changed' and mark neighbors as 'NEXT'.
23 | We inspected `PatientItem`, this is kind of a wrapper class making model `Patient` adaptive to other classes like `IndexViewModelPost`. It has all properties corresponding to `Patient`. Since `Patient` is gonna be changed, we marked this class with 'Changed'. |
24 | We inspected `ItemModelMapper`, it's the mapper class which contains `PatientItem`'s map method, we had to marked it as 'Changed' since `PatientItem` was marked 'Changed'. |
25 | We inspected `IndexViewModelPost`, this class is a viewModel class. Front end (html) form information will be bind to this class's properties. Since the we were gonna do a little change from front end, we marked this class with 'Changed'. |
26 | We inspected `EncounterService`, this class fetches and stores patient's encounters, won't be impacted. We marked it with 'Unchanged'. |
27 | No more classes are marked 'NEXT', we finished impact analysis with: <br>![](impact-analysis.png) |

__Time Spent: 120 mins__  
__Recorder: Shane Qi__

## Prefactoring

\# | Description | Rationale
---|---|---
1 | We went to `config/evolutions.default` and added a new evolution script called 99.sql which alters the `patients` table by adding a new boolean column `birthday_is_fake` which is null by default thus patients who have already registered are having the null value for the flag until their next visit. | According to Kevin’s email, adding a column to the `patients` table is an option to solve this issue, and we do think it is the safest way. Since database structure is the prerequisites for all the following changes, we put it in prefactoring phase.
2 | We went into interface `IPatient` and added abstract getter and setter of new column/property `birthdayIsFake`. | Implementation changes were left for actualization phase.
3 | We went into interface `IDataModelMapper` and added a parameter `birthdayIsFake` into the abstract method `createPatient`. | Implementation changes were left for actualization phase.

__Time Spent: 20 mins__  
__Recorder: Tianxiang Zhang__

## Actualization

\# | Description | Rationale
---|---|---
1 | Since database altering was done in prefactoring phase, we split the actualization into three parts:  <br> 1. Models.  <br> 2. User Interface. <br>3. CSV exporting logic. |

### Models

\# | Description | Rationale
---|---|---
1 | We looked into the java class `Patient` and added the corresponding data field, `birthdayIsFake` as well as the “set" and “get” methods, thus the `birthday_is_fake` could be accessed now. |
2 | We went into class `DataModelMapper`, and did the changes corresponding to interface's `createPatient` method change. |
3 | We went into class `PatientItem` class and added `birthday_is_fake` property and it's setter and getter. | `PatientItem` doesn't conform to any interface.
4 | We went into interface `IItemModelMapper`, and added a parameter `birthday_is_fake` into method `createPatientItem`. |
5 | We went into class `ItemModelMapper` and changed the method `createPatientItem` corresponding to its interface. |
6 | We went into class `PatientService` which creates new `Patient` and `PatientItem` object. We need to add a new argument `birthdayIsFake` so the number of parameters matches out constructor (`DataModelMapper.createPatient()` and `ItemModelMapper.createPatientItem()`). |
7 | We went into class `searchService` which creates new `PatientItem` object. We also need to add a new argument `birthdayIsFake` so the number of parameters matches constructor (`ItemModelMapper.createPatientItem()`). |

### User Interface

\# | Description | Rationale
---|---|---
1 | We added a new class in `triage.css` called `checkBoxLabel` and thus we could add a good-looking checkbox onto the `triage` page to ask the user explicitly if the birthday is fake or not. |
2 | We added the 'birthday is fake' check-box into `./app/femr/ui/views/triage/index.scala.html`. |
3 | Then we added the flag variable `birthdayIsFake` for the input boolean status of the mentioned checkbox in `IndexViewModelPost`, as well as the get and set methods. | `IndexViewModelPost` is the class that html form bind to.

### CSV Exporting

\# | Description | Rationale
---|---|---
1 | In the `TriageController` class we invoked `patient.setBirthdayIsFake()` with the input parameter `viewModelPost.getBirthdayIsFake()` which comes directly from the user.|  
2 | In `researchExportItem`, we added a new method that will create a new cloumn of `birthday` which would be used to store birthday and the fake birthday flag(if applicable).|
3 | We also modified `ResearchService` which is reposiponsible for exporting patient's data into CSV file. In this class, we have to also add some conditions to output the correct birthday information, which would be discussed soon. | We need to implement a condition that can correctly output 4 kinds of `birthday` column value, 1. accurate date. 2. date + "fake" indicating a fake birthday 3. empty string which indicates there is only classification instead of birthday 4. "unknown" string for those patients who have joined before this fix released.
4 | In `ResearchService`, we added a new outout column which will be filled into `birthday` mentioned in `step 12`. We then set a `if` statement to determine if the `age` column in table `patients` is null or not (the confusing part here is that `age` column actually stores birthday in `date` type). As long as it is not null we output the birthday (otherwise we mark it “unknown"), in addition, if the `birthdayIsFake` flag is `true`, we add another string “(fake)” after the birthday. | Current excel has only the age column, and we want to output the birthday from database as well as the flag to indicate if the date is fake or not

__Time Spent: 40 mins__  
__Recorder: Tianxiang Zhang__


## Postfactoring

\# | Description | Rationale
---|---|---
1 | We reviewed the change we made, not any anti-patterns were introduced. | The change we made is not a big one.
2 | We decided to skip postfactoring. |

__Time Spent: 20 mins__  
__Recorder: Tianxiang Zhang__

## Validation

\# | Description | Rationale
---|---|---
1 | Test case defined:<br>Inputs:<br>-input 02/20/1993 as the genuine birthday without checking the checkbox<br>Expected output:<br>Birthday column: 02/20/1993 | Regular expected behavior test.<br>Result: 2/20/93 passed
2 | Test case defined:<br>Inputs:<br>-input 02/20/1993 as a fake birthday with checkbox checked<br>Expected output:<br>Birthday column: 02/20/1993(fake) | Regular expected behavior test.<br>Result: 02/20/1993(fake) passed
3 | Test case defined:<br>Inputs:<br>-input teen(13-17) as the classification without checking the checkbox<br>Expected output:<br>Birthday column: `nothing` | Regular expected behavior test.<br>Result: returns nothing, passed
4 | Test case defined:<br>Inputs:<br>-input teen(13-17) as the classification with the checkbox checked saying it is a fake birthday<br>Expected output:<br>Birthday column: `nothing` | Exceptional behavior test. This case demonstrates the scenario when a patient claims his or her birthday as fake yet he or she does not even submit a birthday.<br>Result: returns nothing, passed
5 | Test case defined:<br>Inputs:<br>-input 16 years as the age with the checkbox checked saying it is a fake birthday<br>Expected output:<br>Birthday column: 10/03/2000(fake) | Regular expected behavior test.<br>Result: 10/03/2000(fake) as expected, passed
6 | Test case defined:<br>Inputs:<br>-this is a sad story<br>-going to the hospital on birthday<br>-input 16 years as the genuine age with the checkbox unchecked<br>Expected output:<br>Birthday column:10/03/2000 | Regular expected behavior test.<br>Result: 10/03/00 passed

__Time Spent: 40 mins__  
__Recorder: Tianxiang Zhang__

## Timing

Phase Name | Time (minutes)
---|---
Concept Location | 150
Impact Analysis | 120
Prefactoring | 20
Actualization | 40
Postfactoring | 20
Validation | 40
Total | 390

__Recording Time Included__

## Reverse engineering

### Class Diagram

![](class-diagram.png)

### Sequence Diagram

![](sequence-diagram.png)

## Conclusion

The whole change is not that tricky. We have come up with several solutions but adding a column seems like the safest one as well as the one that could be achieved after we analyzed the structure of the `triage` module. Adding a data field in `patient` object did not take too long, but it was really time-consuming to figure out where the constructor is used. Also, it also took a lot of time for us to make every mapping complete including the mapping between front-end objects and back-end objects, database table column and `patient` data field, CSV column and database column, etc.

In validation section we've tested 6 cases which were generated by the cartisian product of {checked box or not} and {classification, accurate birthday, year and month}, and all of them passed the tests as expected. The only thing that's missing (and also the thing that we cannot help) is about the flag for those people who have joined before our change. Since there was no flag, all the previous birthday data are marked as unknown since we could not find any evidence to prove if any of them are fake or not, but the good news is that, once the patients come again, they could renew their birthday information and overwrite the previous records, so sooner or later we would update all the previous birthday records by patients themselves' help and the researchers would be able to access those data without any convern to the fake birthday issue.

Files changed:

- `./app/femr/business/services/system/PatientService.java`
- `./app/femr/business/services/system/ResearchService.java`
- `./app/femr/business/services/system/SearchService.java`
- `./app/femr/common/IItemModelMapper.java`
- `./app/femr/common/ItemModelMapper.java`
- `./app/femr/common/models/PatientItem.java`
- `./app/femr/common/models/ResearchExportItem.java`
- `./app/femr/data/DataModelMapper.java`
- `./app/femr/data/IDataModelMapper.java`
- `./app/femr/data/models/core/IPatient.java`
- `./app/femr/data/models/mysql/Patient.java`
- `./app/femr/ui/controllers/TriageController.java`
- `./app/femr/ui/models/triage/IndexViewModelPost.java`
- `./app/femr/ui/views/triage/index.scala.html`
- `./conf/evolutions/default/99.sql`
- `./public/css/triage.css`
