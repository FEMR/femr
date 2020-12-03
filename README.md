# FEMR - Fast Electronic Medical Records

![Build Status](https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiMVBXNWNSMnZsYkgxb05IYS9rclF4eE9QcVdZT1JBNWI1V3RucFd1cXd4ZVEzTzZ5ZWREaEJJRXRDbExyY243eG05VVV4cWVkQXlMelN1bnkxY2dHUUlZPSIsIml2UGFyYW1ldGVyU3BlYyI6IjlCTnI2U0hvU00yNjROQnQiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master)

### Description

fEMR is a fast EMR solution for remote clinics who depend on speed and ease of use rather than complex features. Check out [Team fEMR's website](https://teamfemr.org) for more information and a live demo.

### Community
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)
3. [Team FEMR](https://teamfemr.org)

### Dependencies

* [Play Framework](http://www.playframework.com/)

# Community:

### Communication
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)

# Installation and Configuration using IntelliJ IDEA:

### Required downloads
1. [MySQL 5.7](http://www.mysql.com/)
2. [Scala Build Tool](http://www.scala-sbt.org/)
3. [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
4. [IntelliJ IDEA](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### Installation and Configuration
1. Make sure your Java and sbt environment variables are set.

2. Fork the repository to your GitHub account.

3. Clone the repository.
    ```bash
    git clone https://github.com/yourusername/femr.git
    ```
4. See the wiki page Git Configuration for further information regarding how to contribute to fEMR.

### Setting up MySQL 
1. After downloading MySQL, run the installer to install MySQL. 
2. Proceed through the installer and make a note of the username and password you set. 
3. It is recommended to install [MySQL Workbench](https://www.mysql.com/products/workbench/), the official GUI for your MySQL database.
4. Run MySQL Workbench and you should see your MySQL database running as localhost. Select your database and create a new schema by clicking one of the icons on the top toolbar. Name it whatever you want, but make note of it for later.
5. Navigate to the Session tab in the lower left corner. Take note of your Host and Port. The default should be localhost and 3306.

### Setting up IntelliJ IDEA
1. Open IntelliJ and close any active projects.
2. A project selector window will pop up, click Configure and then Plugins.
3. Search for Scala plugin and install. 
2. Back on the project selector window, click Import Project.
3. Navigate to where you cloned fEMR, and click Open.
4. Select 'Import project from external model'
5. Select 'SBT'
6. Click Next
8. Ensure the Project SDK is java version 1.8, click Finish.
10. Create a file named application.dev.conf in the conf folder, copy and paste the information from application.example.conf in it. Change the db.default fields to match your database address, username, and password that you noted before. Change the same fields in application.conf as well.

Example with database named femr, database located at localhost with port 3306, username and password both root: 
```bash
db.default.url="jdbc:mysql://127.0.0.1:3306/femr?characterEncoding=UTF-8"
db.default.username="root"
db.default.password=“root”
```
11. Quit IntelliJ, navigate to where you cloned fEMR, and run the following command to clean and compile the project. It is normal to have a few errors. 
```bash
    sbt clean compile test
```
12. Open up the fEMR project in IntelliJ again. Under Run, select Edit configurations. Create a new sbt Task and name it whatever you want. Under the “Tasks” field, enter the word “run”. Then, under Environment variables, add the following environment variables using your absolute filepath to the file:
     [config.file // /absolute/location/to/conf/application.dev.conf]
     [user.dir // /absolute/location/to/femr]
13. Run the program from IntelliJ. Open a browser and navigate to localhost:9000. If everything was configured correctly, then you will be greeted with a login screen. Enter admin as the username and password, and you can then create new user credentials to login with.
14. Contact info@teamfemr.org with any issues configuring IntelliJ.

### Warnings

* fEMR is not HIPAA compliant (... yet).
* fEMR is not meant to diagnose, treat, cure or prevent disease.
* fEMR may attempt to establish a secure remote connection when internet access becomes available. This behavior is configurable and turned off by default.

### Questions?

Email: kevin.zurek@teamfemr.org
