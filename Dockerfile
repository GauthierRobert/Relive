FROM openjdk:11 as build

WORKDIR /workspace/app

COPY ./pom.xml ./pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY src src

RUN ["chmod", "+x", "mvnw"]
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM openjdk:11-jre-slim

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","be.relive.Global.GlobalApplication"]