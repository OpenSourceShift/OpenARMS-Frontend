#!/bin/bash

if [ "$2" != "" ]; then
	DATA=`cat $2`
else
	DATA="";
fi;

if [ "$4" != "" ]; then
	AUTH=`cat $4`
else
	AUTH="";
fi;

if [ "$AUTH" != "" ]; then
	#BASE64AUTH=`echo $AUTH | base64`
	AUTHOPT="-u '$AUTH'"
else
	AUTHOPT=""
fi;

echo "$1 http://localhost:9000/$3"
curl -v -i $AUTHOPT -H "Accept: application/json" -X $1 -d "$DATA" http://localhost:9000/$3
echo


