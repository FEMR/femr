# Community:

### Communication
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)

# Installation and Configuration using IntelliJ IDEA:

### Required downloads
1. [MySQL 5.7](http://www.mysql.com/)
2. [Scala Build Tool](http://www.scala-sbt.org/)
3. [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
4. [IntelliJ IDEA Ultimate](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### Installation and Configuration
1. make sure java and sbt environment variables are set

2. fork the repository to your GitHub account

3. clone the repository
    ```bash
    git clone https://github.com/yourusername/femr.git
    ```

4. clean, compile, and test the project
    ```bash
    sbt clean compile test
    ```

### Setting up IntelliJ IDEA
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
12. Under Run, select Edit configurations. Create a new Play 2.0 application and add the following environment variables using your absolute filepath to the file:
     [config.file // /absolute/location/to/conf/application.dev.conf]
     [user.dir // /absolute/location/to/femr]
13. Run
14. Contact info@teamfemr.org for an IntelliJ IDEA liscense key or with any issues configuring IntelliJ.


# General workflow information (Git, Pull Requests, branching, etc..):

Unless you are working on a bug in a specific release, always create a branch from master. Each JIRA issue should have its own branch. [JIRA](https://teamfemr.atlassian.net) issues have 4 categories. To create a working branch before working on an issue:

1. features:
```bash
    git checkout master
    git checkout -b feature-[JIRA_ID]-[briefDescriptionOfFeature]
```

2. bugs:
```bash
    git checkout master
    git checkout -b bug-[JIRA_ID]-[briefDescriptionOfBug]
```

3. improvements:
```bash
    git checkout master
    git checkout -b improvement-[JIRA_ID]-[briefDescriptionOfImprovement]
```

4. tasks:
```bash
    git checkout master
    git checkout -b task-[JIRA_ID]-[briefDescriptionOfTask]
```

Example:
```bash
git checkout -b feature-FEMR832-fixingEverythingEverywhere
```

## Staying in sync with the main repository
Always sync your fork's (username/femr) master branch with the project's (femr/femr) master branch. If your working branch begins to deviate too far from master, merging will becoming increasingly difficult. This ensures that your work remains in sync with everyone else's work:

1. List your current remotes to see if you have one pointing upstream to the main project repository (femr/femr):
```bash
    git remote -v
```

2. If you do not, add one:
```bash
    git remote add upstream https://github.com/femr/femr.git
```

3. Sync your updated local master branch with your fork's master branch:
```bash
    git checkout master
    git pull upstream master
    git push origin master
```

  > If you have committed your work to master, you will run into issues here. Move your work to a separate working branch and get a fresh copy of the master branch.

4. This step requires [rebasing](https://git-scm.com/docs/git-rebase). Sync your working branch with your fork and rebase new code into your working branch:
	git checkout [issueBranchName]
    git push origin [issueBranchName]
	git rebase master

5. After confirming your code was properly merged and that the rebase was successful, sync the new branch with your fork:
    git checkout [issueBranchName]
    git push -f origin [issueBranchName]

    > Note the '-f' option for push in Step 5. This forces a push because the rebase has altered your commit history. Anyone else that was using your branch will need to delete it and pull down a fresh copy.

## Submit your code for review to be accepted into the main project repository (femr/femr) by sending a Pull Request from your fork on GitHub:

1. Update your branch with the newest code by syncing master and then rebasing your working branch. See Step 4 and Step 5 in the previous section to complete this.

2. Initiating a pull request:

  Initiate a pull request from your fork's (username/femr) working branch into the main repository's (femr/femr) master branch.

3. If your Pull Request is Accepted
	git checkout master
	git pull upstream master   

4. If your Pull Request requires additional commits you can add them to your branch and push to your fork. They will automatically be updated in the Pull Request.
```bash
    git checkout [issueBranchName]
    ~~~make changes, commit them~~~
    git push origin [issueBranchName]
```

5. If your Pull Request is Rejected
```bash
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
```

6. Delete your branch locally:
```bash
    git branch -d [issueBranchName]
```

7. Delete your branch from your fork:
```bash
	git push origin :[issueBranchName]
```
