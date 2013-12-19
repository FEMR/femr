# Git Workflow

## The Forking Workflow

Learn the forking workflow in detail [here](https://www.atlassian.com/git/workflows#!workflow-forking).

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