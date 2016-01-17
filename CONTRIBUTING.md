#Community:

### -Channels of communication
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)
3. [Mailing List](https://groups.google.com/forum/#!forum/team-femr)

#Installation and Configuration using IntelliJ IDEA:

### -Required downloads
1. [MySQL 5.6](http://www.mysql.com/)
2. [Play Framework 2.3.7](http://downloads.typesafe.com/typesafe-activator/1.2.10/typesafe-activator-1.2.10.zip)
3. [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
4. [IntelliJ IDEA Ultimate 14](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### -Installation and Configuration
#####1. make sure java and play environment variables are set

#####2. fork the repository to your GitHub account

#####3. clone the repository
    git clone https://github.com/yourusername/femr.git

#####4. clean and compile the project
    activator clean compile

### -Setting up IntelliJ IDEA
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


# Common Git commands during development:

### -Create a branch to work on

After assigning yourself an issue from [JIRA](https://teamfemr.atlassian.net):

#####1. features:
    git checkout master
    git checkout -b feature-[JIRA_ID]-Description

#####2. bugs:
    git checkout master
    git checkout -b bug-[JIRA_ID]-Description

#####3. improvements:
    git checkout master
    git checkout -b improvement-[JIRA_ID]-Description

#####4. tasks:
    git checkout master
    git checkout -b task-[JIRA_ID]-Description

### -Sync your master branch (username/femr) with the project's master branch (femr/femr)

#####1. make sure you have a remote pointing upstream:
    git remote -v

#####2. if you don't, add one:
    git remote add upstream https://github.com/femr/femr.git

#####3. sync:
    git checkout master
    git pull upstream master
    git push origin master


### -Sending a pull request

#####1. update your branch with the newest code by rebasing
    git checkout master
    git pull upstream master
    git checkout branchName
    git rebase master

#####2. initiate a pull request:

Initiate a pull request from your fork's feature branch into the main repository's master branch. Information on initating pull requests can be found [here](https://help.github.com/articles/using-pull-requests).

#####3. if your Pull Request is Accepted
	git checkout master
	git pull upstream master

#####4. if your Pull Request is Rejected

    git checkout master
    git pull upstream master
    git checkout branchName
    git rebase master
    ~~~fix issues~~~
    git checkout master
    git pull upstream master
    git checkout branchName
    git rebase master
    git push origin branchName

#####5. deleting your branch:

    git branch -d branchName
    git push origin :branchName
