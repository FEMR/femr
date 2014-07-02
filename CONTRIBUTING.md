#Installation and Configuration using IntelliJ IDEA

### Required downloads
1. [MySQL](http://www.mysql.com/)
2. [Play Framework](http://www.playframework.com/)
3. [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
4. [IntelliJ IDEA](http://www.jetbrains.com/idea/)
5. [Git](http://git-scm.com/)

### Configuration
####1. make sure java and play enviroment variables are set

####2. fork the repository to your github account

####3. clone the repository
    git clone https://github.com/yourusername/femr.git

####4. convert the project to an IDEA module
    play idea

####5. clean and compile the project
    play clean compile

### Setting up IntelliJ IDEA
1. Install Plugins: Play 2.0 Support/Scala
2. Open FEMR
3. Create a file named application.dev.conf in the conf folder, copy and paste the information from application.example.conf in it. Change the information to match your username and password.
4. Create a database in mySQL with the same name as the database in the connection string inside of application.conf
5. Under Run, select Edit configurations. Create a new Play 2.0 application and add config.file // /absolute/location/to/conf/application.dev.conf to the enviroment variables.
6. Run!



# Git

### Common commands during development:

#### Create a branch to work on:

#####1. features:
    git checkout development
    git checkout -b feature-issue#-featureName

#####2. bugs:
    git checkout development
    git checkout -b bug-issue#-bugName


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