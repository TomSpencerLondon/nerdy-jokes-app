FROM azul/zulu-openjdk:11.0.7

COPY build/libs/*.jar /app/app.jar

WORKDIR /app

CMD ["java", "-jar", "app.jar"]
