#!/bin/bash

echo "Building Search Service"
cd search-service
./mvnw clean package -DskipTests
cd ..

echo "Building Ranking Service"
cd ranking-service
./mvnw clean package -DskipTests
cd ..

echo "Building Trip Service"
cd trip-service
./mvnw clean package -DskipTests
cd ..
