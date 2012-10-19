#!/bin/bash
BASE="/home/creen/Projects/OpenARMS"
play build-module $BASE/OpenARMS/Service
play build-module $BASE/OpenARMS/Frontend
play build-module $BASE/OpenARMS-Authentication-CAS
# Extract a special API-only version of the OpenARMS/Service
SERVICE="$BASE/OpenARMS/Service/dist/OpenARMS-Service-0.3.zip"
API="$BASE/OpenARMS/Service/dist/OpenARMS-Service-API-0.3.zip"
cp $SERVICE $API
zip -d $API test/\* public/\* modules/\* conf/\* app/views/\* app/notifiers/\* app/models/\* app/controllers/\*
zip -d $BASE/OpenARMS-Authentication-CAS/dist/OpenARMS-Authentication-CAS-0.1.zip modules/OpenARMS-Service-0.3/\*
play dependencies $BASE/OpenARMS/Service --sync
play dependencies $BASE/OpenARMS/Frontend --sync
play dependencies $BASE/OpenARMS-Authentication-CAS --sync
play eclipsify $BASE/OpenARMS/Service
play eclipsify $BASE/OpenARMS/Frontend
play eclipsify $BASE/OpenARMS-Authentication-CAS
