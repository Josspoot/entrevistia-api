# Imagen del backend para Render (o cualquier servicio con Docker).
# Etapa 1: compilar el .jar con Gradle y Java 26
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN ./gradlew --no-daemon dependencies > /dev/null
COPY src ./src
RUN ./gradlew --no-daemon bootJar -x test

# Etapa 2: solo lo necesario para correrlo (imagen más ligera)
FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
# Plan gratuito de Render: 512 MB de RAM
ENV JAVA_OPTS="-Xmx320m -XX:+UseSerialGC"
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
