# Build phase
FROM maven:3.9.16-eclipse-temurin-21@sha256:2b4496088e7b80ae10a8c9f74e574ea21380325a006ec684532ad6bad5bc7273 AS build
WORKDIR /app
# org.etsi.osl.model.tmf and com.itav.slicemanager:api-models aren't published to Central;
# vendor them into the local repo so Maven can resolve them offline instead of fetching remotely.
COPY docker/m2-repo /root/.m2/repository
COPY . .
RUN mvn clean package -DskipTests

# Runtime phase
FROM eclipse-temurin:21.0.11_10-jre-jammy@sha256:d63bd8d9b171999cbed8576f2c76e874dd4856791a358536e5c4d407e77edc13
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Docker Image migas77/slicing-gcontroller:0.0.4
# sudo docker run -d --name gcontroller -p 18080:8080 migas77/slicing-gcontroller:0.0.4