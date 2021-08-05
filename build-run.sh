#!/bin/bash

mvn clean install && docker-compose down --rmi all && docker-compose up 
#mvn clean install && docker-compose up --force-recreate
