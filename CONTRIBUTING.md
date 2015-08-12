#Community
1. [JIRA](https://teamfemr.atlassian.net)
2. We collaborate on Slack - contact ken.dunlap@teamfemr.org for an invite!

#Installation and Configuration using IntelliJ IDEA 14*
*use development branch for most up-to-date information

### Required downloads
1. [MySQL 5.6](http://www.mysql.com/)
2. [Play Framework 2.3.7](http://downloads.typesafe.com/typesafe-activator/1.2.10/typesafe-activator-1.2.10.zip)
3. [Java JDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
4. [IntelliJ IDEA Ultimate 14](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### Configuration
####1. make sure java and play enviroment variables are set

####2. fork the repository to your github account

####3. clone the repository
    git clone https://github.com/yourusername/femr.git

####4. clean and compile the project
    activator clean compile

### Setting up IntelliJ IDEA 14
1. Install Plugin: Scala
2. File -> Import
3. Select the femr folder, click next
4. Select 'Import project from external model'
5. Select 'SBT'
6. Click Next
7. Select 'Use auto-import'
8. Ensure the Project SDK is java version 1.7, click finish
9. Create a file named application.dev.conf in the conf folder, copy and paste the information from application.example.conf in it. Change the information to match your database, username, and password.
10. Create a database in mySQL with the same name as the database in the connection string inside of application.dev.conf
11. Under Run, select Edit configurations. Create a new Play 2.0 application and add the following environment variables:
     [config.file // /absolute/location/to/conf/application.dev.conf]
     [user.dir // /absolute/location/to/femr]
12. Run
13. Contact kevin.zurek@teamfemr.org for an IntelliJ IDEA liscense key or with any issues configuring IntelliJ.


# Git

### Common commands during development:

#### Create a branch to work on:

#####1. features:
    git checkout development
    git checkout -b feature-[JIRA_Name]-Description

#####2. bugs:
    git checkout development
    git checkout -b bug-[JIRA_Name]-Description


#### Sync development branch (fork) with development branch (main repository):

#####1. make sure you have a remote pointing upstream:
    git remote

#####2. if you don't, add one:
    git remote add upstream https://github.com/femr/femr.git

#####3. sync:
    git checkout development
    git pull upstream development
    git push origin development


#### Prepare to send a pull request:

    git checkout development
    git pull upstream development
    git checkout branchName
    git rebase development

Rebasing will update your branch with the development branch.
You can find more info on rebasing [here](http://git-scm.com/book/ch3-6.html).

#### Sending a Pull Request from GitHub

Information on doing pull requests can be found [here](https://help.github.com/articles/using-pull-requests).

#### If your Pull Request is Rejected

    git checkout development
    git pull upstream development
    git checkout branchName
    git rebase development
    ~~~fix issues~~~
    git checkout development
    git pull upstream development
    git checkout branchName
    git rebase development
    git push origin branchName

#### Deleting your branch:

    git branch -d branchName
    git push origin :branchName
