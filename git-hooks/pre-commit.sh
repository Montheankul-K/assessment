#!/bin/bash

cd ../posttest
./gradlew test
./gradlew spotlessCheck test
