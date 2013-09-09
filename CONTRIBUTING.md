Verbiage taken from PhoneGap's [Git Contributor Workflow](https://github.com/phonegap/phonegap/wiki/Git-Contributor-Workflow) with some modifications.

# Git Contribution Workflow

## Topic Branch

A good habit to get into is using topic branches for your work, while keeping the master branch untouched. You can then keep the master branch up-to-date with the main repository without worrying about merge conflicts.

### Reduce Merge Conflicts

By not working on the master branch, you ensure that the branch's history will not diverge from the main repository's master branch. This allows you to pull in updates from the main repository (delasteve/femr) without merge conflicts.

### Organize and Isolate Contributes

By creating a topic branch for each contribution, you effectively isolate your changes into a single branch of history. As long as the topic branch is up-to-date, your changes will merge cleanly into the main repository. If your contributions cannot be merged cleanly, the repository maintainer may have to reject your contribution until you update it.

### Easier for the Maintainer

Maintainers like topic branches. It is easier to review the pull request and merge the commit into the main repository.

## Git Workflow

Consider that you've decided to work on issue #11, which implements BMI calculation.

### Sync master branch with main repository

    git checkout master
    git pull upstream master
    git push origin master

### Create a topic branch

    git checkout master
    git checkout -b feature-11-calculate-BMI

However you choose to name your branch is up to you. The maintainer's preferred method is:

    [feature|bug]-[issue number if available]-[feature implementing]

### Make changes to code

    git add /path/to/file.java
    git status
    git commit -m "[#11] Added BMI Calculation"

Prepare to send pull request

    git checkout master
    git pull upstream master
    git checkout feature-11-calculate-BMI
    git rebase master

Before sending the pull request, you should ensure that your changes merge cleanly with the main repository delasteve/femr.

You can do this by pulling the latest changes from the main repository and rebasing the history of the master branch onto the topic branch feature-11-calculate-BMI. Essentially, this will fast-forward your topic branch to the latest commit of the master.

## Sending a Pull Request from GitHub

Open a web browser to your GitHub account's fork of the femr repository.

More soon...

### When your Pull Request is Accepted

    git checkout master
    git pull upstream master
    git log

You can now delete your topic branch, because it is now merged into the main repository and in master branch.

    git branch -d feature-11-calculate-BMI
    git push origin :feature-11-calculate-BMI

### When your Pull Request is Rejected

    git checkout master
    git pull upstream master
    git checkout feature-11-calculate-BMI
    git rebase master
    * Address issues in pull request discussion *
    git checkout master
    git pull upstream master
    git checkout feature-11-calculate-BMI
    git rebase master
    git push origin feature-11-calculate-BMI

Resend pull request from GitHub account.