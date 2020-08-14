FROM azul/zulu-openjdk:11.0.7

WORKDIR /app

COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
