#!/bin/sh

# Use this script to bump the version accross all POMs.

PROGNAME=`basename "$0"`

if [ "$#" -ne 1 ]; then
    echo "Illegal number of arguments. Use '$PROGNAME <version>'"
else
    mvn versions:set -DnewVersion=$1
    sed -i.versionsBackup "s/version: '.*',$/version: '$2',/" frontend/Gruntfile.js
    sed -i.versionsBackup "s/\"version\": \".*\",$/\"version\": \"$2\",/" frontend/package.json
    find . -name pom.xml.versionsBackup -exec rm {} \;
fi
