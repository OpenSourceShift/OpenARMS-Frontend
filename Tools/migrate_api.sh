#!/bin/sh
echo "Started the migration of the API classes ..."
DIR=`dirname $0`
CLASSDIR=`readlink -f $DIR/../Service/tmp/classes/api/`
JARTARGET=`readlink -f $DIR/../Frontend/lib/openarms-api.jar`
echo "Tool dir is located at $DIR"
echo "Class dir is at $CLASSDIR"
echo "Target .jar file at $JARTARGET"

cd $CLASSDIR
echo $JARTARGET
zip -r $JARTARGET .


