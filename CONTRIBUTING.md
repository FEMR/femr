#Installation and Configuration using IntelliJ IDEA

### Required downloads
1. [MySQL](http://www.mysql.com/)
2. [Play Framework](http://www.playframework.com/)
3. [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
4. [IntelliJ IDEA](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### Configuration
1. make sure java and play enviroment variables are set

2. clone the repository
>git clone https://github.com/kevinzurek/femr.git

3. convert the project to an IDEA module
>play idea

4. clean and compile the project
>play clean compile

### Setting up IntelliJ IDEA
1. Install Plugins: Play 2.0 Support/Scala
2. Open FEMR
3. Create a file named application.dev.conf in the conf folder, copy and paste the information from application.example.conf in it.
4. Under Run, select Edit configurations. Create a new Play 2.0 application and add config.file // conf/application.dev.conf to the enviroment variables.
5. Run!



# Git

### Common commands during development:

#### Create a branch to work on:
    
    1. features:
    git checkout master
    git checkout -b feature-issue#-featureName

    2. bugs:
    git checkout master
    git checkout -b bug-issue#-bugName


#### Sync master branch (fork) with master branch (main repository):

    1. make sure you have a remote pointing upstream:
    git remote

    2. if you don't, add one:
    git remote add upstream https://github.com/kevinzurek/femr.git

    3. sync:
    git checkout master
    git pull upstream master
    git push origin master


#### Prepare to send a pull request:

    git checkout master
    git pull upstream master
    git checkout branchName
    git rebase master

Rebasing will update your branch with the master branch.
You can find more info on rebasing [here](http://git-scm.com/book/ch3-6.html).

#### Sending a Pull Request from GitHub

Information on doing pull requests can be found [here](https://help.github.com/articles/using-pull-requests).

#### If your Pull Request is Rejected

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

#### Deleting your branch:

    git branch -d branchName
    git push origin :branchName