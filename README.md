# FEMR - Fast Electronic Medical Records

![Build Status](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiMVBXNWNSMnZsYkgxb05IYS9rclF4eE9QcVdZT1JBNWI1V3RucFd1cXd4ZVEzTzZ5ZWREaEJJRXRDbExyY243eG05VVV4cWVkQXlMelN1bnkxY2dHUUlZPSIsIml2UGFyYW1ldGVyU3BlYyI6IjlCTnI2U0hvU00yNjROQnQiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master)

![Scala CI](https://github.com/FEMR/femr/actions/workflows/scala.yml/badge.svg)

### Description

fEMR is a fast EMR solution for remote clinics who depend on speed and ease of use rather than complex features. Check out [Team fEMR's website](https://teamfemr.org) for more information and a live demo.

### Community
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)
3. [Team FEMR](https://teamfemr.org)

### Dependencies

* [Play Framework](http://www.playframework.com/)

### Contributing
For more information on contributing, please see the CONTRIBUTING.md file. For details regarding installation and deployment, continue reading.

### CI
This repo uses Github Actions workflows for continuous integration, which can be found under the Actions tab in Github (https://github.com/CPSECapstone/zzs-femr/actions?query=workflow%3A%22Scala+CI%22). The Scala CI workflow runs 'sbt test' whenever code is pushed or a pull request is made to the main branch.

### Setting up weekly backup reminder

* It is very important for the administrator to backup local data to the remote database.
* A cronjob can be set up before a kit is deployed to automatically display a message once a week.

1. In the command line, type crontab -e
2. A text editor should appear.
3. Add the following line to this file: 0 11 * * 5 <path to fEMR project home directory>/util/WeeklyReminder.sh
4. This will display a reminder pop up at 11:00AM every Friday.

### Warnings

* fEMR is not HIPAA compliant (... yet).
* fEMR is not meant to diagnose, treat, cure or prevent disease.
* fEMR may attempt to establish a secure remote connection when internet access becomes available. This behavior is configurable and turned off by default.

### Questions?

Email: kevin.zurek@teamfemr.org
    
    
## Running the application (tremr-branch Trauma sheet) using Docker 
1. Make sure you have [Docker](https://docs.docker.com/get-docker/) installed and running on your machine.
2. Clone the [FEMR/femr](https://github.com/FEMR/femr) repo: `git clone https://github.com/FEMR/femr.git`
3. Checkout the [tremr-branch](https://github.com/FEMR/femr/tree/tremr-branch) branch: `git checkout tremr-branch`.
4. Cd into the femr directory: `cd femr`    
5. Run `docker build -t tremr_branch .` (This step will take a while).
6. Then run `docker-compose up`. (This will also take a while).
7. If step 6 successfully finishes, then the app will be available at http://localhost:9000/    

## Running the application (super-femr branch) using Docker
1. Make sure you have [Docker](https://docs.docker.com/get-docker/) installed and running on your machine.
2. Clone the [FEMR/femr](https://github.com/FEMR/femr) repo: `git clone https://github.com/FEMR/femr.git`
3. Checkout the [super-femr](https://github.com/FEMR/femr/tree/super-femr) branch: `git checkout super-femr`.
4. Cd into the femr directory: `cd femr`    
5. Run `docker-compose up` to start the app.
6. If step 5 successfully finishes, then the app will be available at http://localhost:9000/
    
# Setting up the project with IntelliJ on macOS

### Step 1: Download and Install the following Software and Dependencies 
- [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Git](http://git-scm.com/)

### Step 2: Clone the repo and checkout super-femr branch
- `git clone https://github.com/FEMR/femr.git`
- `git checkout super-femr`

### Step 3: Setting up the DB 
1. Open the MySQL Workbench.
2. Select the db icon to create a new schema and call it `femr_db`.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/mysqlworkbench1.png?raw=true)

</details>

3. Under the Administration tab, select `User and Priviliges`. Then `Add account` and add `Login name` and `Password` of your preference. Save the login and password because you will need it in the later steps. 
     - For this example, the username is `testing` and password is `password`.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/mysqlworkbench2.png?raw=true)

</details>

4. Then go to the `Schema Privileges` tab, select `Add Entry...` for the user you created in the previous step, and select the `femr_db` schema.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/mysqlworkbench3.png?raw=true)

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/mysqlworkbench4.png?raw=true)

</details>

5. Give all of the rights, except the `GRANT OPTION`. 

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/mysqlworkbench5.png?raw=true)

</details>

### Step 4: Configuring IntelliJ

1. Open IntelliJ IDEA Ultimate. Then open the `femr` project with `super-femr` branch checked out.

2. In IntelliJ IDEA Ultimate and go to `Preferences` -> `Plugins` -> click `Marketplace` -> Then download `Scala` and `Play Framework`. Then restart the IDE.

3. Inside `femr/conf` folder, create a new file named  `application.dev.conf`. 
4. Copy the following settings inside and save it. Note that `db.default.username` and `db.default.password` values must match the account and password from the Step 3.3. For this example, my username is `testing` and password is `password`.

```
include "application.conf"
settings.researchOnly=0
db.default.url="jdbc:mysql://127.0.0.1:3306/femr_db?characterEncoding=UTF-8"
db.default.username="testing"
db.default.password="password"
photos.defaultProfilePhoto="./public/img/defaultProfile.png"
csv.path="./Upload/CSV"
```

5. Go to `Run` -> `Edit Configurations` -> click on the `+` sign -> `Play 2 App`.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij5.png?raw=true)

</details>

6. Then, click on `Edit Environment Variables` -> add the following two environment variables: `user.dir` and `config.file` (make sure to change the value based on where the two are stored on your local machine). Then click apply and ok.
    - `config.file` is the path for application.dev.conf.
    - `user.dir` is the path for the project.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij6.png?raw=true)

</details>


7. Go to `File` -> `Project structure` -> Under the `Project` tab -> Select `Project SDK` and set it to 1.8. 
    - You can download 1.8 directly from IntelliJ. If so, choose 1.8 Amazon Correto.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij7.png?raw=true)

</details>

8. Change the language level to 8.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij8.png?raw=true)

</details>

9. On the rightmost side of IntelliJ, if there is a vertical line containing sbt. Click on the sbt tab and then click the refresh symbol.
      - If there isn’t sbt on the right, remove the .idea folder from the root directory of the project. In the command line, traverse to the root directory and do: rm -r .idea. Then redo step 2 and then continue. If still does not work try removing the project and recloning it again. 

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij9.png?raw=true)

</details>

10. After that runs, click on the Play button to run the configuration. If everything was set up correctly, the website should open up on another window. Select “Apply this script now”.

<details> <summary> screenshot </summary>

![Image](https://github.com/kylene-phillips/femr-installation/blob/gh-pages/images/intellij10.png?raw=true)

</details>

### Troubleshooting
1. Try deleting the .idea folder and rerunning sbt 
2. Confirm you have the absolute path in your environment variables
3. Try to re-apply the plugins Scala and Play Framework
4. Otherwise, try cloning the femr github and going through the steps once more.
5. Try invalidating Intellij IDEA cache.

### [EULA and Privacy Policy](https://github.com/FEMR/femr/blob/master/LICENSE)

