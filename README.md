# FEMR - Fast Electronic Medical Records

[![Build Status](https://concourse.teamfemr.org/api/v1/pipelines/femr/jobs/build-test-package/badge)](https://concourse.teamfemr.org/teams/main/pipelines/femr)

A simple and fast EMR system.

### Community
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)

### Description

The goal is to become an easy EMR solution for remote clinics who depend on speed and ease of use rather than complex features.

### Dependencies

* [Play Framework](http://www.playframework.com/)

### Installation and Deployment

For more information on contributing, please see the CONTRIBUTING.md file. For details regarding installation and deployment, continue reading.

Add play framework to your PATH environment variable if you haven't done so already. For more information, visit the official [PlayConsole](https://www.playframework.com/documentation/2.3.x/PlayConsole) page.

#### 1. Edit application.conf accordingly.
#### 2. Enter the console.
    sbt clean compile test dist
#### 3. This will generate a script used for executing FEMR.

### Warnings

* This application is not HIPAA compliant (... yet).
* This application is not meant to diagnose, treat, cure or prevent disease.

### Questions?

Email: kevin.zurek@teamfemr.org
