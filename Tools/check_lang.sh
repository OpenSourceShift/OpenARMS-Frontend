#!/bin/bash

function parsemsgs() {
	# skip comments
	cat $1 |grep -v "\#" > /tmp/msg
	# get parameters as space separated list
	cat /tmp/msg |cut -d '=' -f 1 |tr '\n' ' '
	rm /tmp/msg
}


echo "Checking language translations"
DIR=`dirname $0`
LANGDIR=`readlink -f $DIR/../Frontend/conf`
PARAMS=$(parsemsgs $LANGDIR/messages.en)

for f in `ls $LANGDIR/messages.*`; do
	for p in $PARAMS; do
		TMPPARAMS=$(parsemsgs $f)
		MATCH=0
		for np in $TMPPARAMS; do
			if [ "$np" = "$p" ]; then
				MATCH=1
				break;
			fi;
		done;
		if [ "$MATCH" -eq 0 ]; then
			echo $p is not in $f;
		fi;
	done;
	echo "";
	echo "=====================================";
	echo "";
done;


