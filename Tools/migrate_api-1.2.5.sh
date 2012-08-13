#!/bin/sh
echo "Started the migration of the API classes ..."
PLAYPATH="$1"
DIR=`dirname $0`
CLASSDIR="/tmp/classes/"
JAVASRCDIR=`readlink -f $DIR/../Service/app/api` 
JAVADSTDIR="/tmp/javasrc"
JARTARGET=`readlink -f $DIR/../Frontend/lib/openarms-api.jar`
echo "Tool dir is located at $DIR"
echo "Class dir is at $CLASSDIR"
echo "Java source dir at $JAVASRCDIR"
echo "Java dest dir at $JAVADSTDIR" 
echo "Target .jar file at $JARTARGET"

if [ ! -f "$PLAYPATH/framework/play-1.2.5.jar" ]; then
	echo "Sanity check! Did not find the play framework!"
	exit;
fi;

rm -rf $CLASSDIR $JAVADSTDIR

if [ ! -d "$CLASSDIR" ]; then
	mkdir $CLASSDIR
fi;

if [ ! -d "$JAVADSTDIR" ]; then
	mkdir $JAVADSTDIR;
fi;

cp -R $JAVASRCDIR $JAVADSTDIR/
rm -rf $JAVADSTDIR/api/deprecated/
cd $CLASSDIR
CLASSPATH=$PLAYPATH/framework/play-1.2.5.jar:$PLAYPATH/framework/lib/gson-2.2.jar javac `find $JAVADSTDIR/ -name "*.java"` -d .
if [ $? -ne 0 ]; then
	echo "Class compilation failed!";
	exit;
fi;
jar cf $JARTARGET api/
#echo $JARTARGET
#jar cf $JARTARGET api/
#zip -r $JARTARGET api/
#rm -rf $CLASSDIR
echo "Done."
