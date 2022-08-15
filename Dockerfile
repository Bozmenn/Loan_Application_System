FROM openjdk:17-oracle
MAINTAINER Berk Ozmen <berkozmen94@hotmail.com>
# The EXPOSE instruction exposes the specified port
# and makes it available only for inter-container communication.
EXPOSE 8080
ADD target/Loan_Application_System-0.0.1-SNAPSHOT.jar loan_application_system.jar
ENTRYPOINT ["java","-jar","loan_application_system.jar"]

## Dockerizing the app
#
# Create a Spring Boot Application
# Create Dockerfile
# Build executable jar file - mvn clean package
# Build Docker image - docker build -t airport-reservation-app:v1 .
# Run Docker container using the image built - docker run --name airport-reservation-system -p 8080:8080 airport-reservation-applicaiton:v1
# Test