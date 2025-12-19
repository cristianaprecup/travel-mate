@echo off
echo Building Search Service
cd search-service
call mvnw clean package -DskipTests
cd ..

echo Building Ranking Service
cd ranking-service
call mvnw clean package -DskipTests
cd ..

echo Building Trip Service
cd trip-service
call mvnw clean package -DskipTests
cd ..
