FROM gradle:8.1-jdk17-alpine AS build
LABEL maintainer="isadiape.test@gmail.com"

COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM bellsoft/liberica-runtime-container:jre-17-cds-slim-musl

EXPOSE 9000
RUN mkdir /app

RUN addgroup appgroup; adduser  --ingroup appgroup --disabled-password spring
USER spring

ENV TZ="UTC"

COPY --from=build /home/gradle/src/build/libs/application-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "/app/app.jar"]