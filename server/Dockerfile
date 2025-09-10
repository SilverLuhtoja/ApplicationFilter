FROM openjdk:17-alpine

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build  -x test

CMD ["java", "-jar", "build/libs/filter-api.jar"]
