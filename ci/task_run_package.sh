#!/bin/sh

set -ex

cd resource-femr-app && sbt dist && mv target/universal/femr-*.zip ../femr-dist/femr.zip
ls ../femr-dist
