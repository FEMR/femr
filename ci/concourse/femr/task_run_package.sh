#!/bin/sh

set -ex

mv sbt-cache/.ivy2 ~/

cd git-femr-app && sbt dist && mv target/universal/femr-*.zip ../femr-dist/femr.zip
