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
4. [IntelliJ IDEA Ultimate 15](http://www.jetbrains.com/idea/)
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
8. Ensure the Project SDK is java version 1.8, click finish
9. If asked to select modules/data, select fEMR(root module) and fEMR-build.
10. Create a file named application.dev.conf in the conf folder, copy and paste the information from application.example.conf in it. Change the information to match your database, username, and password.
11. Create a database in mySQL with the same name as the database in the connection string inside of application.dev.conf
12. Under Run, select Edit configurations. Create a new Play 2.0 application and add the following environment variables:
     [config.file // /absolute/location/to/conf/application.dev.conf]
     [user.dir // /absolute/location/to/femr]
13. Run
14. Contact kevin.zurek@teamfemr.org for an IntelliJ IDEA liscense key or with any issues configuring IntelliJ.


# General workflow information (Git, Pull Requests, branching, etc..):

### -Do not commit to the master branch or any of the release branches. Each JIRA issue should have its own branch. [JIRA](https://teamfemr.atlassian.net) issues have 4 categories. To create a working branch before starting on a [JIRA](https://teamfemr.atlassian.net) issue:

#####1. features:
    git checkout master
    git checkout -b feature-[JIRA_ID]-[briefDescriptionOfFeature]

#####2. bugs:
    git checkout master
    git checkout -b bug-[JIRA_ID]-[briefDescriptionOfBug]

#####3. improvements:
    git checkout master
    git checkout -b improvement-[JIRA_ID]-[briefDescriptionOfImprovement]

#####4. tasks:
    git checkout master
    git checkout -b task-[JIRA_ID]-[briefDescriptionOfTask]

######Example: git checkout -b feature-FEMR832-fixingEverythingEverywhere

### -Always sync your fork's (username/femr) master branch with the project's (femr/femr) master branch. If your working branch deviates too far from master, merging will be hard. This ensures your code is always up to date with everyone else:

#####1. make sure you have a remote pointing upstream to the main project repository (femr/femr):
    git remote -v

#####2. if you don't, add one:
    git remote add upstream https://github.com/femr/femr.git

#####3. sync updated master with your fork:
    git checkout master
    git pull upstream master
    git push origin master

#####4. This step requires rebasing. Rebasing can be daunting at first. Find us in [Slack](http://teamfemr.org/slack.html) if you have questions. Sync your working branch with your fork and rebase new code into your issue branch. :
	git checkout [issueBranchName]
	git rebase master


### -Submit your code for review to be accepted into the main project repository (femr/femr) by sending a pull request:

#####1. update your branch with the newest code by syncing master and then rebasing your issue branch
    git checkout master
    git pull upstream master
    git checkout [issueBranchName]
    git rebase master

#####2. initiate a pull request:

Initiate a pull request from your fork's (username/femr) issue branch into the main repository's (femr/femr) master branch. Information on initating pull requests can be found [here](https://help.github.com/articles/using-pull-requests).

#####3. if your Pull Request is Accepted
	git checkout master
	git pull upstream master

#####4. if your Pull Request is Rejected
    git checkout master
    git pull upstream master
    git checkout [issueBranchName]
    git rebase master
    ~~~fix issues~~~
    git checkout master
    git pull upstream master
    git checkout [issueBranchName]
    git rebase master
    git push -f origin [issueBranchName]

#####5. delete your branch locally:
    git branch -d [issueBranchName]

#####6. delete your branch from your fork:
	git push origin :[issueBranchName]
