
## `dist` Folder
- manually put in S3 for now, maybe track in a repo later
- mirror femr defaults, but configurable via ENV vars
- Folder Structure
    - `public/img/defaultProfile.png`
    - `Uploads/CSV`
    - `Procfile` - tells EB how to run the app
        - `web: ./bin/femr -Dhttp.port=5000 -Dconfig.file=conf/demo.conf`
 

## Code Pipeline Setup
- Code Build
    - buildspec.yml: what does it do
        - Setup environment
        - run tests
        - run build
        - package build artifacts 
        - start EB deployment
- How to setup initially
    - manually add buildsepc: `ci/aws/buildspec.yml`
    - Secondary Sources: `DistFolder` name in setup matters?
- Approval Step

## Elastic Beanstalk Setup
- How the deployment works, what tasks are performed
- How to setup in EB initially


## TODO
- run this whole thing with code files and remove manual setups