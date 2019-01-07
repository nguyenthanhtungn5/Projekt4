FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . ./
CMD ./gradlew --no-daemon --stacktrace clean bootJar

FROM openjdk:8-jre-alpine
RUN apk add --no-cache bash
WORKDIR /app

COPY wait-for-it.sh .
COPY --from=build /app/build/libs/*.jar person-scs.jar

CMD java -jar person-scs.jar
