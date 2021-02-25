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

### CI
This repo uses Github Actions workflows for continuous integration, which can be found under the Actions tab in Github (https://github.com/CPSECapstone/zzs-femr/actions?query=workflow%3A%22Scala+CI%22). The Scala CI workflow runs 'sbt test' whenever code is pushed or a pull request is made to the main branch.

### Installation and Deployment

For detailed instructions regarding installation and deployment, please follow the directions in the following link.   
* [Installation Instructions](https://docs.google.com/document/d/1CLDNAvnc_doWw2OGKpXw11MguEUsex2o14ifo-qf5jA/edit?usp=sharing)

Prerequisites: Have MySQL, MySQLWorkbench, and IntelliJ IDEA Ultimate installed on a local machine.  
#### 1. Download Scala and Play Framework plugins.
#### 2. Make a new file in the conf folder named application.dev.conf containing database information (see link for more details).
#### 3. Create a new schema "femr_db" in SQL Workbench.
#### 4. Add a Play2 App Configuration on IntelliJ with 2 environment variables: user.dir and config.file.
#### 5. Click the sbt refresh button.
#### 6. Run the configuration on IntelliJ.

### Warnings

* fEMR is not HIPAA compliant (... yet).
* fEMR is not meant to diagnose, treat, cure or prevent disease.
* fEMR may attempt to establish a secure remote connection when internet access becomes available. This behavior is configurable and turned off by default.

### Questions?

Email: kevin.zurek@teamfemr.org
