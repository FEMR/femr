#!/bin/sh

set -ex


cd git-femr-app && sbt clean compile test



mv ~/.ivy2 ../sbt-cache
ls .
ls ..
ls ../sbt-cache
