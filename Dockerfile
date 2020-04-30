
FROM openjdk:11
EXPOSE 8080
ADD target/spring-manage-user.jar spring-manage-user.jar 
ENTRYPOINT ["java","-jar","/spring-manage-user.jar"]
