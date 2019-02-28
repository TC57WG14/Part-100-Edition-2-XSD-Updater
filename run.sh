#!/bin/bash -f
if [ "$#" -eq "0" ]; then
   echo "Usage: $0 - [file1] [file2] ..."
   exit 1
fi
set -o noclobber
CLASSPATH=.:target/XSDUpdater.jar:$CLASSPATH
for INFILE in $@; do 
    java -ea org.tc57wg14.part100.ed2.XSDUpdater <$INFILE >${INFILE%%.xsd}-2.xsd
done
