#!/bin/bash

if [ "$2" != "" ]; then
	DATA=`cat $2`
else
	DATA="";
fi;

echo "$1 http://localhost:9000/$3"
curl -i -H "Accept: application/json" -X $1 -d "$DATA" http://localhost:9000/$3
echo


