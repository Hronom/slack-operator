FROM maven:3.9-eclipse-temurin-25-alpine AS build

WORKDIR /code

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline --batch-mode

COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip=true --also-make --batch-mode

FROM build AS tests

RUN mvn test --batch-mode

FROM eclipse-temurin:25-jre-alpine AS application

WORKDIR /app

COPY --from=build /code/target/*.jar app.jar

RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --ingroup appuser appuser

RUN chown -R appuser:appuser /app

USER appuser

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
