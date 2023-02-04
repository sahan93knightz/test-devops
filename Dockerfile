FROM eclipse-temurin:17 AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY .gradle .gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./gradlew build -x test
RUN mkdir -p build/dependency && (cd build/dependency; find ../libs -name "*.jar" -exec jar -xf {} \;)

FROM eclipse-temurin:17-jre
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.example.demodevops.DemoDevopsApplication"]