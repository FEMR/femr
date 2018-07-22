# FEMR - Fast Electronic Medical Records

[![Build Status](https://concourse.teamfemr.org/api/v1/pipelines/femr/jobs/build-test-package/badge)](https://concourse.teamfemr.org/teams/main/pipelines/femr)

### Description

fEMR is a fast EMR solution for remote clinics who depend on speed and ease of use rather than complex features. Check out [Team fEMR's website](https://teamfemr.org) for more information and a live demo.

### Community
1. [Slack](http://teamfemr.org/slack.html)
2. [JIRA](https://teamfemr.atlassian.net)
3. [Team FEMR](https://teamfemr.org)

### Dependencies

* [Play Framework](http://www.playframework.com/)

### Installation and Deployment

For more information on contributing, please see the CONTRIBUTING.md file. For details regarding installation and deployment, continue reading.


#### 1. Edit application.conf accordingly.
#### 2. Enter the console.
    sbt clean compile test dist
#### 3. This will generate a script used for executing FEMR.

### Warnings

* fEMR is not HIPAA compliant (... yet).
* fEMR is not meant to diagnose, treat, cure or prevent disease.
* fEMR may attempt to establish a secure remote connection when internet access becomes available. This behavior is configurable and turned off by default.

### Questions?

Email: kevin.zurek@teamfemr.org
