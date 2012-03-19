#!/bin/bash

echo "http://localhost:9000/$3"
curl -i -H "Accept: application/json" -X $1 -d "$2" http://localhost:9000/$3



