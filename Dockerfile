FROM openjdk:11
EXPOSE 9184
ADD target/*.jar Franzoo_Auth.jar
ENTRYPOINT ["java","-jar","Franzoo_Auth.jar"]