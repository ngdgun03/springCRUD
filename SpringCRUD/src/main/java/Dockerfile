# Sử dụng image môi trường Java 11
FROM openjdk:11

WORKDIR /app

COPY ../../../target/spring-boot-redis.jar ./

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "spring-boot-redis.jar"]